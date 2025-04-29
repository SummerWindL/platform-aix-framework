package com.platform.aix.config;

import com.cluster.platform.redis.core.PipeReadService;
import com.cluster.platform.redis.core.PipeWriteService;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.platform.aix.common.agent.RegisterBeanFactory;
import com.platform.aix.controller.pikai.common.request.RequestLoggingFilter;
import com.platform.common.util.JsonAdaptor;
import com.platform.comservice.config.StarterService;
import com.platform.comservice.config.StarterServiceProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author: Advance
 * @date: 2018/4/18
 * @description:
 */
@Configuration
public class BeanConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private StarterServiceProperties properties;

    @Autowired
    private ApisPorperties apisPorperties;
    @Bean
    public StarterService starterService(){
        return new StarterService(properties.getUserStr());
    }

    @Bean
    @ConditionalOnMissingBean(JsonAdaptor.class)
    public JsonAdaptor jsonAdaptor() {
        return new JsonAdaptor();
    }

    /************************ehcache 缓存配置开始********************************/
    @Primary
    @Bean("aixCacheManager")
    public CacheManager cacheManager(EhCacheManagerFactoryBean cacheFactory) {
        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
        cacheManager.setCacheManager(cacheFactory.getObject());
        return cacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean cacheFactory() {
        EhCacheManagerFactoryBean cacheFactory = new EhCacheManagerFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        cacheFactory.setConfigLocation(resolver.getResource("classpath:aix-ehcache.xml"));
        return cacheFactory;
    }
    /************************ehcache 缓存配置结束********************************/

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PipeWriteService pipeWriteService(){
        return new PipeWriteService();
    }

    @Bean
    public PipeReadService pipeReadService(){
        return new PipeReadService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(apisPorperties.getMail().getHost());
        mailSender.setPort(apisPorperties.getMail().getPort());
        mailSender.setUsername(apisPorperties.getMail().getUserName());
        mailSender.setPassword(apisPorperties.getMail().getPassWord());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

    /**
     * 验证码
     * @author fuyanliang
     * @date 2025/4/22 14:22
     * @return com.google.code.kaptcha.Producer
     */
    @Bean
    public Producer kaptchaProducer() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "110");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.session.key", "captchaCode");
        properties.setProperty("kaptcha.textproducer.char.length", "4");

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilterRegistration(RequestLoggingFilter filter) {
        FilterRegistrationBean<RequestLoggingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(1);  // 设置过滤器顺序，越小越先执行
        registration.addUrlPatterns("/*");  // 应用到所有URL
        return registration;
    }
}

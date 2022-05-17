package com.platform.aix.config;

import com.cluster.platform.redis.core.PipeReadService;
import com.cluster.platform.redis.core.PipeWriteService;
import com.platform.aix.common.agent.RegisterBeanFactory;
import com.platform.common.util.JsonAdaptor;
import com.platform.comservice.config.StarterService;
import com.platform.comservice.config.StarterServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

/**
 * @author: Advance
 * @date: 2018/4/18
 * @description:
 */
@Configuration
public class BeanConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private StarterServiceProperties properties;

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
}

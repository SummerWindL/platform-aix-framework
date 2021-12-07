package com.platform.aix.common.listener;


import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 监听启动
 * @author Advance
 * @date 2021年11月19日 15:14
 * @since V1.0.0
 */
@Configuration
public interface AppStartupListener extends ApplicationListener<ContextRefreshedEvent> {

    /**
     * {@inheritDoc}
     */
    @Override
    default void onApplicationEvent(ContextRefreshedEvent event) {
        Object source = event.getSource();
        if (source instanceof AnnotationConfigServletWebServerApplicationContext) {
            onApplicationStartup(event);
        }
    }

    void onApplicationStartup(ContextRefreshedEvent event);

    /**
     * 项目启动成功后调用的方法
     * 交给子类去实现
     * @param applicationContext {@link ApplicationContext}
     */
    void afterStartup(ApplicationContext applicationContext);

}
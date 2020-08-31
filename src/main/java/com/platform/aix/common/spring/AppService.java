package com.platform.aix.common.spring;

import com.platform.aix.config.ServiceBeanConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppService implements ApplicationContextAware {

    private final static Log log = LogFactory.getLog(AppService.class);

    private static ApplicationContext context;

    private AppService() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String beanName) {
        if (context == null) {
            return null;
        }
        Object obj = null;
        try {
//			 log.info("load spring bean [" + service + "]");
            obj = context.getBean(beanName);
        } catch (Exception e) {
            log.error("load spring bean [" + beanName + "] failed");
            return null;
        }
        return (T) obj;
    }

    public static String getBeanName(Class<?> clz) {
        if (context == null) {
            return null;
        }
        String beanName = null;
        try {
            beanName = context.getBeanNamesForType(clz)[0];
        } catch (Exception e) {
            return null;
        }
        return beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppService.context = applicationContext;
        //启动时就获取 直接获取可能存在问题
        ServiceBeanConfig.dataSource = AppService.getBean("dataSource");
    }

}

/**
 * 2015年9月7日 上午11:19:52
 */
package com.platform.aix.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Jesse Zheng
 */
public class ApplicationContextLoadCompleted implements ApplicationContextAware {

    private ApplicationContext applicationContext_local = null;

    public ApplicationContext getApplicationContext() {
        return applicationContext_local;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == applicationContext_local) {
            this.applicationContext_local = applicationContext;
            afterLoadCompleted();
        }
    }

    public Object getBean(String bean) {
        return getApplicationContext().getBean(bean);
    }

    public void afterLoadCompleted() {
        // 默认不做事情，留给子类
    }
}

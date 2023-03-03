package com.platform.aix.config;

import com.platform.aix.module.rmi.RMIParseDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * @author Advance
 * @date 2022年09月12日 18:21
 * @since V1.0.0
 */
//@Configuration
//public class RMIClientConfig {
//    @Bean(name = "rmiService")
//    public RmiProxyFactoryBean initRmiProxyFactoryBean() {
//        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
//        factoryBean.setServiceUrl("rmi://127.0.0.1:1099/rmiService");
//        factoryBean.setServiceInterface(RMIParseDataService.class);
//        return factoryBean;
//    }
//}

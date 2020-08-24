package com.platform.aix.config;

import com.platform.aix.common.util.JsonAdaptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Alfred
 * @date: 2018/4/18
 * @description:
 */
@Configuration
public class BeanConfig {

    @Bean
    public JsonAdaptor jsonAdaptor() {
        return new JsonAdaptor();
    }

}

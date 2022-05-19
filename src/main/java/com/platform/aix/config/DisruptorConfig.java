package com.platform.aix.config;

import com.platform.aix.service.processor.disruptor.SeriesDataEventHandler;
import com.platform.aix.service.processor.disruptor.info.RegisterUserInformationHandler;
import com.platform.aix.service.processor.disruptor.pay.PayUserHandler;
import com.platform.aix.service.processor.disruptor.record.RecordUserSignInformationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Advance
 * @date 2022年05月17日 16:10
 * @since V1.0.0
 */
@Configuration
@ComponentScan(value = {"com.platform.aix.service.processor.disruptor"})
public class DisruptorConfig {
    /**
     * smsParamEventHandler1
     *
     * @return SeriesDataEventHandler
     */
    @Bean
    public SeriesDataEventHandler smsParamEventHandler1() {
        return new SeriesDataEventHandler();
    }

    /**
     * smsParamEventHandler2
     *
     * @return SeriesDataEventHandler
     */
    @Bean
    public SeriesDataEventHandler smsParamEventHandler2() {
        return new SeriesDataEventHandler();
    }

    /**
     * smsParamEventHandler3
     *
     * @return SeriesDataEventHandler
     */
    @Bean
    public SeriesDataEventHandler smsParamEventHandler3() {
        return new SeriesDataEventHandler();
    }


    /**
     * smsParamEventHandler4
     *
     * @return SeriesDataEventHandler
     */
    @Bean
    public SeriesDataEventHandler smsParamEventHandler4() {
        return new SeriesDataEventHandler();
    }

    /**
     * smsParamEventHandler5
     *
     * @return SeriesDataEventHandler
     */
    @Bean
    public SeriesDataEventHandler smsParamEventHandler5() {
        return new SeriesDataEventHandler();
    }


    /**
     * smsParamEventHandler5
     *
     * @return SeriesDataEventHandler
     */
    @Bean
    public SeriesDataEventHandler smsParamEventHandler6() {
        return new SeriesDataEventHandler();
    }

    @Bean
    public RegisterUserInformationHandler registerUserInformationHandler(){
        return new RegisterUserInformationHandler();
    }

    @Bean
    public RecordUserSignInformationHandler recordUserSignInformationHandler(){
        return new RecordUserSignInformationHandler();
    }

    @Bean
    public PayUserHandler payUserHandler(){
        return new PayUserHandler();
    }
}

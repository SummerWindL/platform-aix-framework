package com.platform.aix.service.processor.disruptor.info;

import com.lmax.disruptor.WorkHandler;
import com.platform.aix.service.processor.disruptor.SeriesDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 使用disruptor场景
 * 第一步 登记用户信息
 * @author Advance
 * @date 2022年05月18日 0:24
 * @since V1.0.0
 */
@Component
public class RegisterUserInformationHandler implements WorkHandler<SeriesDataEvent> {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onEvent(SeriesDataEvent event) throws Exception {
        log.info("RegisterUserInformationHandler consumer start consumption ---");
        //模拟登记用户信息
        log.info("登记用户信息。。。。成功，{}",event.getValue().getDeviceInfoStr());
    }
}

package com.platform.aix.service.processor.disruptor.pay;

import com.lmax.disruptor.WorkHandler;
import com.platform.aix.service.processor.disruptor.SeriesDataEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 第三步，付费
 * @author Advance
 * @date 2022年05月18日 0:30
 * @since V1.0.0
 */
@Component
@Slf4j
public class PayUserHandler implements WorkHandler<SeriesDataEvent> {
    @Override
    public void onEvent(SeriesDataEvent event) throws Exception {
        log.info("PayUserHandler consumer start consumption ---");
        //模拟付费接口
        log.info("付费成功。。。,{}",event.getValue().getDeviceInfoStr());
    }
}

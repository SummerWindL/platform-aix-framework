package com.platform.aix.service.processor.disruptor.record;

import com.lmax.disruptor.WorkHandler;
import com.platform.aix.service.processor.disruptor.SeriesDataEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 第二步记录用户的体征信息
 * @author Advance
 * @date 2022年05月18日 0:28
 * @since V1.0.0
 */
@Component
@Slf4j
public class RecordUserSignInformationHandler implements WorkHandler<SeriesDataEvent> {
    @Override
    public void onEvent(SeriesDataEvent event) throws Exception {
        log.info("RecordUserSignInformationHandler consumer start consumption ---");
        //模拟记录用户体征信息
        log.info("用户体征数据。。。成功, {}",event.getValue().getDeviceInfoStr());
    }
}

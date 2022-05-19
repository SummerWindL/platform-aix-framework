package com.platform.aix.service.processor.disruptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lmax.disruptor.WorkHandler;
import com.platform.aix.common.datacommon.db.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Advance
 * @date 2022年05月17日 16:11
 * @since V1.0.0
 */
@Component
public class SeriesDataEventHandler implements WorkHandler<SeriesDataEvent> {
    private Logger logger = LoggerFactory.getLogger(SeriesDataEventHandler.class);

    @Override
    public void onEvent(SeriesDataEvent event) {
        if (event.getValue() == null || StringUtils.isEmpty(event.getValue().getDeviceInfoStr())) {
            logger.warn("receiver series data is empty!");
        }

        logger.info("deviceInfoStr : {},data : {}",event.getValue().getDeviceInfoStr(), JSONObject.toJSONString(event.getValue().getData()));
    }
}

package com.platform.aix.service.processor.disruptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lmax.disruptor.WorkHandler;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.controller.HellowController;
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

        logger.info("class : {}",event.getValue().getClassT().getName());
        try {
            if(event.getValue().getClassT().newInstance() instanceof  HellowController){
                logger.info("------> 类型匹配");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(event.getValue().getData() instanceof HellowController){
            logger.info("------> 类型匹配");
        }

        logger.info("deviceInfoStr : {},data : {}",event.getValue().getDeviceInfoStr(), JSONObject.toJSONString(event.getValue().getData()));
    }
}

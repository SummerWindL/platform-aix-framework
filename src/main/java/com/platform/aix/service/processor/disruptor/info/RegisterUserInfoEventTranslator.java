package com.platform.aix.service.processor.disruptor.info;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorVararg;
import com.platform.aix.service.processor.disruptor.SeriesDataEvent;
import com.platform.common.util.JSONUtil;
import com.platform.common.util.JsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Advance
 * @date 2022年05月18日 0:47
 * @since V1.0.0
 */
public class RegisterUserInfoEventTranslator implements EventTranslatorVararg<SeriesDataEvent> {
    @Autowired
    private JsonAdaptor jsonAdaptor;

    private void generateData(SeriesDataEvent event) {
        //组装 用户注册信息
//        Map<String, Object> stringObjectMap = JSONUtil.object2Map(event.getValue().getDeviceInfoStr());
        try {
            Map map = jsonAdaptor.readValue(event.getValue().getDeviceInfoStr(), Map.class);
            System.out.println(JSONObject.toJSONString(map));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void translateTo(SeriesDataEvent event, long sequence, Object... args) {
        this.generateData(event);
    }
}

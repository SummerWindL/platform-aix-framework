package com.platform.aix.controller;

import com.cluster.platform.redis.common.IValue;
import com.cluster.platform.redis.common.Value;
import com.platform.aix.service.redisservice.param.service.UserInfoParamValService;
import com.platform.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Advance
 * @date 2022年05月20日 21:23
 * @since V1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private UserInfoParamValService userInfoParamValService;

    @GetMapping("/writePipeline")
    public void redis(){
        //redis 服务测试
        Map<String, Map<String, IValue<Object>>> paraValMap = new HashMap<>();
        Map<String,IValue<Object>> iValueMap = new HashMap<>();
        Value<Object> value = new Value<>(str -> str);
        value = new Value<>(str -> DateUtil.format(DateUtil.parse("20220520"),DateUtil.DEFAULT_DATE_FORMAT_EN));
        value.value(value.toString());
        //value.value("20220520");
        iValueMap.put("1111",value);
        paraValMap.put("2222",iValueMap);

        Map<String,IValue<Object>> numValueMap = new HashMap<>();
        Value<Object> num = new Value<>(str -> str);
        num = new Value<>(str -> new BigDecimal(1000));
        num.value(num.toString());
        numValueMap.put("3333",num);
        paraValMap.put("4444",numValueMap);
        userInfoParamValService.setParamVal(paraValMap);

    }

}

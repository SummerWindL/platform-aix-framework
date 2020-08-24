package com.platform.aix.common.handler;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @description: HTTP请求适配器
 * @author: fuyl
 * @create: 2020-08-18 16:44
 **/

public class HttpAdapter {

    public static ZxHttpRequest filter(String jsonStr, Map<String, String> link) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return filter(JSON.parseObject(jsonStr, Map.class), link);
    }

    public static ZxHttpRequest filter(Map obj, Map<String, String> link) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ZxHttpRequest zxHttpRequest = new ZxHttpRequest();
        for (String key : link.keySet()) {
            Object val = obj.get(link.get(key));
            ZxHttpRequest.class.getMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1), String.class).invoke(zxHttpRequest, val.toString());
        }
        return zxHttpRequest;
    }
}

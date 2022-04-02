package com.platform.aix.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * @author Advance
 * @date 2022年03月21日 14:01
 * @since V1.0.0
 */
public class JsonUtils {
    public static <T> T getObjectFromJson(String str, Class<T> cls) {
        return JSON.parseObject(str, cls);
    }

    public static <T> List<T> getObjectListFromJson(String str, Class<T> cls) {
        return JSON.parseArray(str, cls);
    }

    public static String getJsonFromObject(Object object) {
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }
}

package com.platform.aix.service.chain;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟审核
 * @author Advance
 * @date 2022年05月11日 15:59
 * @since V1.0.0
 */
public class AuthService {
    private static Map<String, Date> authMap = new ConcurrentHashMap<String, Date>();

    /**
     * 查询
     * @param uId
     * @param orderId
     * @return
     */
    public static Date queryAuthInfo(String uId,String orderId){
        return authMap.get(uId.concat(orderId));
    }

    /**
     * 审核
     * @param uId
     * @param orderId
     */
    public static void auth(String uId,String orderId){
        authMap.put(uId.concat(orderId),new Date());
    }
}

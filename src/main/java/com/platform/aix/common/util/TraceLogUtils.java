package com.platform.aix.common.util;

/**
 * @author Advance
 * @date 2022年03月01日 13:41
 * @since V1.0.0
 */
public class TraceLogUtils {
    /**
     * 生成 tracedid
     *
     * @return {@link String}
     * @author xiaoy
     * @since 2021/2/4 11:11
     */
    public static String getTraceId() {
        return UUID.generate();
    }
}

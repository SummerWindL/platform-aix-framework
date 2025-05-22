package com.platform.aix.common.util;

import java.util.UUID;
import org.slf4j.MDC;

/**
 * @author Advance
 * @date 2022年03月01日 13:39
 * @since V1.0.0
 */
public class TraceIdUtil {
    public static final String TRACE_ID = "traceId";

    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID);
        return traceId == null ? "" : traceId;
    }

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void remove() {
        MDC.remove(TRACE_ID);
    }

    public static void clear() {
        MDC.clear();
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}

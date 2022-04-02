package com.platform.aix.common.interceptor;

import com.platform.aix.common.constants.CommonCst;
import com.platform.aix.common.util.TraceLogUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 * @author Advance
 * @date 2022年03月01日 13:33
 * @since V1.0.0
 */
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(CommonCst.LOG_TRACE_ID);
        if (traceId == null) {
            traceId = TraceLogUtils.getTraceId();
        }

        MDC.put(CommonCst.LOG_TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //调用结束后删除
        MDC.remove(CommonCst.LOG_TRACE_ID);
    }

}

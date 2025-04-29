package com.platform.aix.controller.pikai.common.request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;
/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月29日 9:22
 */
@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 包装请求和响应以缓存内容
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            // 继续过滤器链 - 不要在此之前读取请求体
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            // 过滤器链执行完毕后记录请求信息
            logRequest(requestWrapper, startTime);

            // 保存请求信息到请求属性中，以便在异常处理器中使用
            saveRequestInfo(requestWrapper);

            // 复制响应内容到原始响应
            responseWrapper.copyBodyToResponse();
        }
    }

    private void saveRequestInfo(ContentCachingRequestWrapper request) {
        // 过滤器链执行后保存请求信息到请求属性中
        String requestBody = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
        request.setAttribute("requestBody", requestBody);
        request.setAttribute("requestStartTime", System.currentTimeMillis());
        request.setAttribute("requestQueryString", request.getQueryString());
    }

    private void logRequest(ContentCachingRequestWrapper request, long startTime) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // 获取查询参数
        String queryString = request.getQueryString();

        // 获取请求头
        String headers = Collections.list(request.getHeaderNames())
                .stream()
                .map(headerName -> headerName + ": " + request.getHeader(headerName))
                .collect(Collectors.joining(", "));

        // 获取请求体 - 此时请求体已经被处理过，可以安全读取
        String requestBody = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

        // 计算处理时间
        long duration = System.currentTimeMillis() - startTime;

        // 记录完整请求信息
        if (log.isDebugEnabled()) {
//            log.debug("Request: {} {} | Query: {} | Headers: [{}] | Body: {} | Duration: {}ms",
//                    method, requestURI, queryString, headers, requestBody, duration);
            // 记录错误日志
            log.debug("获取到API请求 - URI: {} | 方法: {} | 查询参数: {} | 请求体: {}",
                    requestURI,
                    method,
                    queryString != null ? queryString : "",
                    requestBody != null ? requestBody : "");
        } else {
//            log.info("Request: {} {} | Query: {} | Body Length: {} | Duration: {}ms",
//                    method, requestURI, queryString, requestBody.length(), duration);
            log.info("获取到API请求 - URI: {} | 方法: {} | 查询参数: {} | 请求体: {}",
                    requestURI,
                    method,
                    queryString != null ? queryString : "",
                    requestBody != null ? requestBody : "");
        }
    }
}

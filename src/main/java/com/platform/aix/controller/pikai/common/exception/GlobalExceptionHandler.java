package com.platform.aix.controller.pikai.common.exception;

import com.platform.aix.controller.pikai.common.ApiResponse;
import com.platform.aix.controller.pikai.common.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 全局异常处理类
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 19:20
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理业务异常
     * @return
     */
    @ExceptionHandler({BusinessException.class, HttpMessageNotReadableException.class})
    public ApiResponse<?> handleExceptions(Exception ex, WebRequest request, HttpServletRequest httpRequest) {
        String requestBody = (String) httpRequest.getAttribute("requestBody");
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        String queryString = (String) httpRequest.getAttribute("requestQueryString");

        // 如果属性中没有，则尝试直接从请求中获取
        if (queryString == null) {
            queryString = httpRequest.getQueryString();
        }

        // 记录错误日志
        log.error("API异常 - URI: {} | 方法: {} | 查询参数: {} | 请求体: {}",
                requestURI,
                method,
                queryString != null ? queryString : "",
                requestBody != null ? requestBody : "",
                ex
        );
        // 解析业务错误码和消息
        if (ex instanceof BusinessException) {
            BusinessException bex = (BusinessException) ex;
            return ApiResponse.error(bex.getRcode(), bex.getMessage());
        }

        // 默认系统错误
        return ApiResponse.error(500, "系统繁忙");
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ApiResponse.fail(ResponseCode.BAD_REQUEST, errorMsg);
    }
}

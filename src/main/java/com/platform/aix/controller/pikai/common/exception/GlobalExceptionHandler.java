package com.platform.aix.controller.pikai.common.exception;

import com.platform.aix.controller.pikai.common.ApiResponse;
import com.platform.aix.controller.pikai.common.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

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
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException ex, WebRequest request) {
        ResponseCode code = ex.getCode();
        // 记录带上下文的错误日志
//        log.error(
//                "[业务异常] 路径: {} | 错误码: {} | 信息: {} " +
//                        "请求参数: {} " +
//                        "堆栈跟踪: {}",
//                request.getDescription(false), // 请求路径
//                ex.getCode().getCode(),        // 错误码
//                ex.getCustomMessage(),         // 自定义消息
//                request.getParameterMap(),     // 请求参数
//                ExceptionUtils.getStackTrace(ex) // 完整堆栈（需 Apache Commons Lang3）
//        );
        // 记录错误日志（关键！）
        log.error("API异常 - URI: {} | 参数: {}",
                request.getDescription(false),
                request.getParameterMap(),
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

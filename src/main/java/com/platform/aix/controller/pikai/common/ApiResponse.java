package com.platform.aix.controller.pikai.common;

import com.platform.aix.controller.pikai.common.enums.ResponseCode;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 19:17
 */
@Builder
public class ApiResponse<T> implements Serializable {
    private int code;       // 业务状态码 (非HTTP状态码)
    private String message; // 提示信息
    private T data;         // 业务数据
    private long timestamp = System.currentTimeMillis(); // 时间戳

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    // 快速成功响应静态方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "成功", data);
    }

    // 失败响应静态方法
    public static <T> ApiResponse<T> fail(ResponseCode code, String customMessage) {
        return new ApiResponse<>(code.getCode(), customMessage != null ? customMessage : code.getMessage(), null);
    }

    // 错误响应静态方法
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ApiResponse(int code, String message, T data, long timestamp) {

        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
}

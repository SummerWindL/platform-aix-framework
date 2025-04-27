package com.platform.aix.controller.pikai.common.exception;

import com.platform.aix.controller.pikai.common.enums.ResponseCode;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 19:21
 */
public class BusinessException extends RuntimeException {
    private ResponseCode code;
    private String customMessage;
    private final int rcode;

    public BusinessException(int rcode, String message) {
        super(message);
        this.rcode = rcode;
    }

    public int getRcode() {
        return rcode;
    }

    public BusinessException(ResponseCode code, String customMessage, int rcode) {
        super(customMessage != null ? customMessage : code.getMessage());
        this.code = code;
        this.customMessage = customMessage;
        this.rcode = rcode;
    }

    public ResponseCode getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
}


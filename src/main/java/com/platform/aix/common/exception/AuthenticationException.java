package com.platform.aix.common.exception;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 16:11
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}

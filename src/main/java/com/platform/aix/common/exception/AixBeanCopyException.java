package com.platform.aix.common.exception;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 14:45
 **/

public class AixBeanCopyException extends AixException {
    private static final long serialVersionUID = -6901572848436003077L;

    public AixBeanCopyException(String msg) {
        super(msg);
    }

    public AixBeanCopyException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}

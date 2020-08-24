package com.platform.aix.common.exception;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 14:46
 **/

public class AixException extends RuntimeException {

    private static final long serialVersionUID = 7637174399993976947L;

    public AixException(){ }

    public AixException(String msg){
        super(msg);
    }

    public AixException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}

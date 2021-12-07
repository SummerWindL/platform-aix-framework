package com.platform.aix.common.notify.bean;

import lombok.Data;

/**
 * 线程处理结果
 * @author Advance
 * @date 2021年12月03日 9:54
 * @since V1.0.0
 */
@Data
public class CallableResult {
    /**
     * 是否执行成功
     */
    private boolean correctFlag;
    private String header;
    /**
     * 错误代码
     */
    private int errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;
}

package com.platform.aix.cmd.bean.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 14:29
 **/
@Data
public abstract class BaseRequest {
    // 跟踪请求
    private StringBuilder trace;

    // http请求 severlet
    private HttpServletRequest servlet;
}

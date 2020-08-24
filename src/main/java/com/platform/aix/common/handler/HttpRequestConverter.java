package com.platform.aix.common.handler;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @description: HTTP请求转换器
 * @author: fuyl
 * @create: 2020-08-18 17:19
 **/

public class HttpRequestConverter {

    /**
     *
     * @param request
     * @return ZxHttpRequest
     */
    public static ZxHttpRequest jsonConverter2Obj(HttpServletRequest servlet) throws IOException {
        ZxHttpRequest zxHttpRequest = new ZxHttpRequest();
        BufferedReader reader = servlet.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line  = reader.readLine()) != null) {
            sb.append(line);
        }

        String params = sb.toString();
        zxHttpRequest.setFactoryid(servlet.getParameter("factoryid"));
        zxHttpRequest.setFactorysecretkey(servlet.getParameter("factorysecretkey"));
        zxHttpRequest.setParams(params);
        return zxHttpRequest;
    }

    public static ZxHttpRequest formConverter2Obj(HttpServletRequest servlet){
        ZxHttpRequest zxHttpRequest = new ZxHttpRequest();
        zxHttpRequest.setFactoryid(servlet.getParameter("factoryid"));
        zxHttpRequest.setFactorysecretkey(servlet.getParameter("factorysecretkey"));
        zxHttpRequest.setParams(servlet.getParameter("params"));
        return zxHttpRequest;
    }
}

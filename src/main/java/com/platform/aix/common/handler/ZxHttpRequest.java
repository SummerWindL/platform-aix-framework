package com.platform.aix.common.handler;

import lombok.Data;

/**
 * @description: 统一请求参数类
 * @attribute
        factoryid: "",
        factorysecretkey: "",
        params: {}

 * @author: fuyl
 * @create: 2020-08-18 16:45
 **/
@Data
public class ZxHttpRequest {

    private String source;
    private String factoryid;
    private String factorysecretkey;
    private String params;
    private String ext;
}

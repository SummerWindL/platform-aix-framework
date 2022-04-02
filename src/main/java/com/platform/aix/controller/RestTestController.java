package com.platform.aix.controller;

import com.platform.aix.common.response.APIResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Advance
 * @date 2022年03月08日 10:55
 * @since V1.0.0
 */
@RestController
public class RestTestController {
    /**
     * 不带参的get请求
     * @return
     */
    @RequestMapping(value = "testGet", method = RequestMethod.GET)
    public APIResponse testGet(){
        APIResponse result = new APIResponse();
        result.setResultcode(200);
        result.setResultmsg("请求成功，方法：testGet");
        return result;
    }
}

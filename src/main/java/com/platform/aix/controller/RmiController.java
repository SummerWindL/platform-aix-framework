package com.platform.aix.controller;

import com.platform.aix.module.rmi.RMIParseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * 远程方法调用
 * @author Advance
 * @date 2022年09月12日 18:23
 * @since V1.0.0
 */
@RestController("/rmi")
public class RmiController {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @GetMapping("/doInvoke")
    public void test(){
        RMIParseDataService rmiService = (RMIParseDataService) webApplicationContext.getBean("rmiService");
        Map<String, Object> stringObjectMap = rmiService.parseData("", null, null);
    }
}

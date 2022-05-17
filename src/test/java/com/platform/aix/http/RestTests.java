package com.platform.aix.http;

import com.platform.aix.PlatformAixApplicationTests;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.config.ApisPorperties;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Advance
 * @date 2022年03月08日 10:57
 * @since V1.0.0
 */
public class RestTests extends PlatformAixApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Resource(name = "OkRestTemplate")
    private RestTemplate okRestTemplate;

    /**
     * 单元测试（不带参的get请求）
     */
    @Test
    public void testGet(){
        //请求地址
        String url = "http://localhost:8069/testGet";

        //发起请求,直接返回对象
        APIResponse responseBean = okRestTemplate.getForObject(url, APIResponse.class);
        System.out.println(responseBean.toString());
    }
}

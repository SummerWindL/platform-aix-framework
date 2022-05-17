package com.platform.aix.controller;

import com.platform.common.util.ProxyUtil;
import com.platform.comservice.callback.CommonCheckCallBack;
import com.platform.comservice.callback.RegistCallback;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Max;
import java.util.Map;

/**
 * @author Advance
 * @date 2022年03月19日 14:36
 * @since V1.0.0
 */
@Slf4j
public class Request implements CommonCheckCallBack, ProxyUtil.IService {
    @Max(value = 20,message = "最大长度为20")
    public String userId;
    public String password;

    public Request() {
    }

    /**
     * 带参数校验方法 必须是public
     * @author Advance
     * @date 2022/3/19 17:43
     * @param params
     */
    public void check(Map<String,Object> params){
        log.info("调用了校验方法！当前请求参数：{}", params.get("userId"));

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 响应
     * @author Advance
     * @date 2022/3/19 17:35
     */
    @Override
    public void slove() {
        System.out.println("响应");
    }

    @Override
    public void doInvoke() {

    }
}

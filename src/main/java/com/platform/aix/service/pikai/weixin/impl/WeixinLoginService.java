package com.platform.aix.service.pikai.weixin.impl;

import com.google.common.cache.Cache;
import com.platform.aix.controller.pikai.common.exception.BusinessException;
import com.platform.aix.service.pikai.weixin.ILoginService;
import com.platform.aix.service.pikai.weixin.adapter.ILoginAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月10日 14:13
 */
@Service
public class WeixinLoginService implements ILoginService {

    @Resource
    private ILoginAdapter loginAdapter;
    @Resource
    private Cache<String, String> openidToken;

    @Override
    public String createQrCodeTicket() {
        try{
            return loginAdapter.createQrCodeTicket();
        } catch (Exception e){
            throw new BusinessException(0,e.getMessage());
        }
    }

    @Override
    public String checkLogin(String ticket) {
        // 通过 ticket 判断，用户是否登录。如果登录了，会在内存里写入信息。
        return openidToken.getIfPresent(ticket);
    }

}

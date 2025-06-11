package com.platform.aix.service.pikai.weixin.impl;

import com.google.common.cache.Cache;
import com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuth;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiThirdPartyAuthService;
import com.platform.aix.common.util.SecurityUtil;
import com.platform.aix.controller.pikai.common.exception.BusinessException;
import com.platform.aix.service.pikai.weixin.ILoginService;
import com.platform.aix.service.pikai.weixin.adapter.ILoginAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    @Resource
    private PikaiThirdPartyAuthService pikaiThirdPartyAuthService;

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

    @Override
    public Map<String,String> accessLoginData(String ticket) {
        PikaiThirdPartyAuth thirdPartyAuth = pikaiThirdPartyAuthService.selectOne(openidToken.getIfPresent(ticket));
        Map<String,String> userMap = new HashMap<>();
        userMap.put("userId",thirdPartyAuth.getUserId());
        userMap.put("token",SecurityUtil.generateJwtToken(openidToken.getIfPresent(ticket)+"_"+openidToken.getIfPresent("token")));
        return userMap;
    }

}

package com.platform.aix.common.datacommon.db.service.pikai.impl;

import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PikaiThirdPartyAuthMapper;
import com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuth;
import com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuthKey;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiThirdPartyAuthService;
import com.platform.aix.common.util.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月11日 11:54
 */
@Service
public class PikaiThirdPartyAuthServiceImpl extends AsyncServiceImpl<String, PikaiThirdPartyAuth> implements PikaiThirdPartyAuthService {
    @Resource
    private PikaiThirdPartyAuthMapper pikaiThirdPartyAuthMapper;
    @Override
    public IMybatisMapper<PikaiThirdPartyAuth, String, ?> getMapper() {
        return pikaiThirdPartyAuthMapper;
    }

    @Override
    public void clearCache() {

    }

    @Override
    protected void update(PikaiThirdPartyAuth thirdPartyAuth) {

    }

    @Override
    protected void save(PikaiThirdPartyAuth thirdPartyAuth) {

    }

    @Override
    protected void delete(String PK) {

    }

    @Override
    public PikaiThirdPartyAuth saveThirdPartyAuth(PikaiUser user,String openId) {
        String accessToken = SecurityUtil.generateJwtToken(user);
        PikaiThirdPartyAuth thirdPartyAuth = new PikaiThirdPartyAuth();
        thirdPartyAuth.setUserId(user.getUserId());
        thirdPartyAuth.setProvider("weChat");
        thirdPartyAuth.setProviderUserId(openId);
        thirdPartyAuth.setAccessToken(accessToken);
        thirdPartyAuth.setRefreshToken(null);
        thirdPartyAuth.setExpiresAt(null);
        pikaiThirdPartyAuthMapper.insert(thirdPartyAuth);
        return thirdPartyAuth;
    }

    @Override
    public PikaiThirdPartyAuth selectOne(String openId) {
        PikaiThirdPartyAuthKey pikaiThirdPartyAuthKey = new PikaiThirdPartyAuthKey();
        pikaiThirdPartyAuthKey.setProvider("weChat");
        pikaiThirdPartyAuthKey.setProviderUserId(openId);
        return pikaiThirdPartyAuthMapper.selectByPrimaryKey(pikaiThirdPartyAuthKey);
    }
}

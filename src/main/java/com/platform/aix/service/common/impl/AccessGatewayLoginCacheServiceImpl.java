package com.platform.aix.service.common.impl;

import com.cluster.platform.redis.ICache;
import com.cluster.platform.redis.constants.CacheKeyConstants;
import com.cluster.platform.redis.model.TAccessGatewayLoginCache;
import com.platform.aix.config.ApisPorperties;
import com.platform.aix.service.common.AccessGatewayLoginCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Allen
 * @date: 2018/4/19
 * @description:
 */
@Service
public class AccessGatewayLoginCacheServiceImpl implements AccessGatewayLoginCacheService {

    @Autowired
    private ICache iCache;

    @Autowired
    private ApisPorperties apisPorperties;


    @Override
    public TAccessGatewayLoginCache getAccessGatewayLoginCache(String userid) {
        TAccessGatewayLoginCache loginCache = iCache.get(getAccessGatewayLoginCacheKey(userid));
        if (loginCache == null) {
            loginCache = new TAccessGatewayLoginCache();
        }
        return loginCache;
    }

    @Override
    public String getAccessGatewayLoginCacheKey(String userid) {
        String loginInfoKey = CacheKeyConstants.CACHE_ACCESS_GATEWAY_LOGIN_INFO + userid;
        return loginInfoKey;
    }

    @Override
    public void setAccessGatewayLoginCache(String userid, TAccessGatewayLoginCache cache) {
        iCache.setExpire(getAccessGatewayLoginCacheKey(userid), cache,
                apisPorperties.getTokenExpire());
    }

    @Override
    public void clearAccessGatewayLoginCache(String userid) {
        iCache.del(getAccessGatewayLoginCacheKey(userid));
    }

}

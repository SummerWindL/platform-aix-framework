package com.platform.aix.service.common.impl;

import com.cluster.platform.redis.ICache;
import com.cluster.platform.redis.constants.CacheKeyConstants;
import com.cluster.platform.redis.model.TUserLoginCache;
import com.platform.aix.config.ApisPorperties;
import com.platform.aix.service.common.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Advance
 * @date: 2018/4/19
 * @description:
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private ICache iCache;

    @Autowired
    private ApisPorperties apisPorperties;


    @Override
    public TUserLoginCache getUserLoginCache(String userid) {
        TUserLoginCache loginCache = iCache.get(getUserLoginCacheKey(userid));
        if (loginCache == null) {
            loginCache = new TUserLoginCache();
        }
        return loginCache;
    }

    @Override
    public String getUserLoginCacheKey(String userid) {
        String loginInfoKey = CacheKeyConstants.CACHE_USER_LOGIN_INFO + userid;
        return loginInfoKey;
    }

    @Override
    public void setUserLoginCache(String userid, TUserLoginCache cache) {
        iCache.setExpire(getUserLoginCacheKey(userid), cache,
                apisPorperties.getTokenExpire());
    }

    @Override
    public void clearUserLoginCache(String userid) {
        iCache.del(getUserLoginCacheKey(userid));
    }

}

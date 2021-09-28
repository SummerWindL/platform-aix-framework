package com.platform.aix.service.common;


import com.cluster.platform.redis.model.TUserLoginCache;

public interface CacheService {

    /*
      用户登录缓存
     */
    TUserLoginCache getUserLoginCache(String userid);

    String getUserLoginCacheKey(String userid);

    void setUserLoginCache(String userid, TUserLoginCache cache);

    void clearUserLoginCache(String userid);

}

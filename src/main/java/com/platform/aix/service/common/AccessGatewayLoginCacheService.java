package com.platform.aix.service.common;


import com.cluster.platform.redis.model.TAccessGatewayLoginCache;

/**
 * 接入网关登录缓存
 */
public interface AccessGatewayLoginCacheService {


    TAccessGatewayLoginCache getAccessGatewayLoginCache(String userid);

    String getAccessGatewayLoginCacheKey(String userid);

    void setAccessGatewayLoginCache(String userid, TAccessGatewayLoginCache cache);

    void clearAccessGatewayLoginCache(String userid);

}

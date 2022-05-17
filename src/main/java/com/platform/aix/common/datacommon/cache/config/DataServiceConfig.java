package com.platform.aix.common.datacommon.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataServiceConfig {
    //true 异步DB  false 同步DB
    @Value("${cacheType.asyncDb:true}")
    private boolean asyncDb = true;
    //true 写入redis  false 去掉redis
    @Value("${cacheType.useRedis:true}")
    private boolean useRedis = true;

    public boolean isAsyncDb() {
        return asyncDb;
    }

    public void setAsyncDb(boolean async) {
        asyncDb = async;
    }

    public boolean isUseRedis() {
        return useRedis;
    }

    public void setUseRedis(boolean useRedis) {
        this.useRedis = useRedis;
    }
}

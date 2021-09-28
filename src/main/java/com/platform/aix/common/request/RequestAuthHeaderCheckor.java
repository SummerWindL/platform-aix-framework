package com.platform.aix.common.request;

import com.cluster.platform.redis.ICache;
import com.cluster.platform.redis.model.TLoginCacheBase;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.platform.aix.common.util.JsonAdaptor;
import com.platform.aix.config.ApisPorperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author Allen_Song
 * @time 2017年6月9日 下午6:03:44
 */
@Component
public class RequestAuthHeaderCheckor {

    @Autowired
    private ICache iCache;

    @Autowired
    private JsonAdaptor mapper;

    @Autowired
    private ApisPorperties apisPorperties;

    public boolean checkRequestAuthHeaderValid(String auth) {

        // 若为空返回校验失败
        if (StringUtils.isEmpty(auth)) {
            return false;
        }

        try {

            if (checkAuth(auth)) {
                return true;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            // 返回校验失败
            return false;
        }

        // 返回校验失败
        return false;
    }

    /**
     * 鉴权
     * @author Advance
     * @date 2021/9/28 12:00
     * @param auth
     * @return boolean
     */
    private boolean checkAuth(String auth) throws IOException {
        RequestAuthHeader authHeader = mapper.readValue(auth, RequestAuthHeader.class);
        if (authHeader != null && authHeader.getId() != null) {
            String id = authHeader.getId();

            TLoginCacheBase loginCache = iCache.get(id);
            if (loginCache != null) {
                String token = loginCache.getToken();
                if (!StringUtils.isEmpty(token) && token.equals(authHeader.getToken())) {
                    // 更新登录信息过期时间
                    iCache.setExpire(id, loginCache, apisPorperties.getTokenExpire());
                    return true;
                }
            }

        }

        return false;
    }
}

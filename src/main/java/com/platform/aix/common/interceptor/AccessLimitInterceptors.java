package com.platform.aix.common.interceptor;

import com.cluster.platform.redis.ICache;
import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.annotation.Access;
import com.platform.aix.common.annotation.AccessLimit;
import com.platform.aix.common.handler.CommandHandler;
import com.platform.aix.common.handler.base.IBaseHandler;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @description: 请求拦截器
 * @author: fuyl
 * @create: 2020-08-25 17:18
 **/
@Component
public class AccessLimitInterceptors {
    @Autowired
    private ICache iCache;

    /**
     * 拦截所有请求
     * @param request
     * @param handler
     * @return
     */
    public boolean accessLimitInterceptor(HttpServletRequest request , CommandHandler handler) throws NoSuchMethodException {
        if (AopUtils.getTargetClass(handler).getAnnotation(Access.class)!=null) {
            Method method = handler.getClass().getMethod("execute", BaseRequest.class);
            if (!method.isAnnotationPresent(AccessLimit.class)) {
                return true;
            }
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int limit = accessLimit.limit();
            int sec = accessLimit.sec();
            String key = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
            //资源唯一标识
            String formatDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            //String key="request_"+formatDate;
            Integer maxLimit = iCache.getOpsValues(key);
            if (maxLimit == null) {
                //set时一定要加过期时间
                iCache.putOpsValue(key, 1, Long.valueOf(sec), TimeUnit.SECONDS);
            } else if (maxLimit < limit) {
                iCache.putOpsValue(key, maxLimit + 1, Long.valueOf(sec), TimeUnit.SECONDS);
            } else {
                return false;
            }
        }
        return true;
    }
}

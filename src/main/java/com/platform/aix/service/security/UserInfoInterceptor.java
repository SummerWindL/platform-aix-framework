package com.platform.aix.service.security;

import com.platform.aix.common.datacommon.db.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 用户信息拦截器
 * @author Advance
 * @date 2022年05月23日 11:38
 * @since V1.0.0
 */
@Component
@Aspect
public class UserInfoInterceptor {
    /**
     * 拦截点
     */
    @Pointcut("execution(* com.platform.aix.common.datacommon.db.service.*Service+.*(..))")
    public void excute() {}

    /**
     * 接口方法执行开始，绑定线程变量
     * @param joinPoint
     */
    @Before(value = "excute()")
    public void beforeExcute(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for(Object arg : args) {
                if(arg instanceof User) {
                    User user = (User)arg;
                    UserUtil.bindUser(user);
                }
            }
        }
    }

    /**
     * 接口方法执行完毕，清理线程变量
     * 无论是正常结束还是异常结束都会进入该方法
     * @param joinPoint
     */
    @After(value = "excute()")
    public void afterExcute(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for(Object arg : args) {
                if(arg instanceof User) {
                    UserUtil.clear();
                }
            }
        }
    }

}

package com.platform.aix.common.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 11:37
 **/

@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    /**
     * 标识
     * 指定sec时间段内的访问次数限制
     * @return
     */
    int limit() default 5;
    /**
     * 标识 时间段
     * @return
     */
    int sec() default 5;
}

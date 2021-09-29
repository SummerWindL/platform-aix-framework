package com.platform.aix.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 邮箱验证注解
 *
 * @ClassName        : Email
 * @author            : Advance
 * @date : 2017年4月27日 上午9:56:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Email {

    String msg() default "邮箱格式不正确";

}

package com.platform.aix.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 账号验证，验证手机或者邮箱
 *
 * @ClassName        : EmailOrPhone
 * @author            : Advance
 * @date : 2017年5月12日 上午10:38:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Account {

    String msg() default "账号格式不正确";

}

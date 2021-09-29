package com.platform.aix.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要解密数据注解
 *
 * @ClassName        : Decryption
 * @author            : Advance
 * @date : 2017年4月26日 下午1:49:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Decryption {

}

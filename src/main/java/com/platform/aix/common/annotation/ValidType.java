package com.platform.aix.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *类型校验注解
 *value 的值为一个类，
 *该类应拥有一个指定的isSupprotType或isSupportTypeList的静态方法（返回值为boolean）
 *如果要使用自定义的方法名，可以在自定义方法方法上添加@IsSupportType注解
 *@author rivers
 *@version 2.0
 *@see [相关类/方法]
 *@since [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidType {

    @SuppressWarnings("rawtypes")
    Class value();

    String msg() default "类型不匹配";
}


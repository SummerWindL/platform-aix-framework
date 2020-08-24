package com.platform.aix.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 涉及到文件上传的类 需要从请求头中获取请求参数
 * @author: fuyl
 * @create: 2020-07-09 19:14
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileUploadFunction {
}

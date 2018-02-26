package com.javaman.smart.framwork.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pengzhe
 * @date 2018/2/27 00:06
 * @description 方法注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    /**
     * 请求类型与路径
     *
     * @return
     */
    String value();
}

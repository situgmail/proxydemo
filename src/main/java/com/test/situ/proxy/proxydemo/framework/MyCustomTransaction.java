package com.test.situ.proxy.proxydemo.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类似spring，创建一些注解 可以追加控制 事务行为
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCustomTransaction {
    String value() default "default MyTransaction";
}

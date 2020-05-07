package com.test.situ.proxy.proxydemo.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在这里新增的注解，仅仅只是声明，应用到我们的方法上。
 * 简单地类似于spring的@Transactional注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionalService {
}

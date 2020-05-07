package com.test.situ.proxy.proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 内部服务，所有的执行，都是通过代理去实现，不需要再注入或者实例化所有的facade门面处理去执行
 * 所有内部服务。
 * MyInvocationHandler#invoke 方法将会执行 类实现接口的任何方法
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object object){
        this.target = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        System.out.println(method.getName() +  "executed in " + (System.nanoTime() - startTime));
        return result;
    }
}

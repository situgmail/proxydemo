package com.test.situ.proxy.proxydemo.framework;

import java.util.List;

/**
 * 这个容器类的主要功能为，根据指定接口以类型安全的模式 返回当前代理类
 */
public class MyCustomProxy {

    // 代理接口集合，注意不能超过源码里指定的65535
    private List<Class<?>> interfaces;
    private Object proxy;

    // 泛型创建实体类，create all services invoker
    public MyCustomProxy(List<Class<?>> interfaces, Object proxy){
        this.interfaces = interfaces;
        this.proxy = proxy;
    }


    public Object getJdkProxy(){
        return proxy;
    }


    public boolean hasInterface(Class<?> expectedInterface){
        return interfaces.contains(expectedInterface);
    }
}

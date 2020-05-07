package com.test.situ.proxy.proxydemo.framework;

import com.test.situ.proxy.proxydemo.MyInvocationHandler;
import com.test.situ.proxy.proxydemo.ProxyApp;
import org.reflections.Reflections;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 代理工厂，扫描classpath，很容易利用反射使用类库的实体类
 */
public class ProxyFactory  {

    private List<MyCustomProxy> beanRegistry;

    // 代理创建工厂，查找指定包里包含 自定义注解 跟进反射创建代理类
    public ProxyFactory(Package packageToLookUp){
        Reflections reflection = new Reflections(packageToLookUp.getName());
        Set<Class<?>> transactionalServiceClass = reflection.getTypesAnnotatedWith(TransactionalService.class);
        
        List<?> beans = instantiateBeans(transactionalServiceClass);

        beanRegistry = createProxies(beans);
    }

    private List<MyCustomProxy> createProxies(List<?> beans){
        return beans.stream().map(this::createProxy).collect(Collectors.toList());
    }

    /**
     *  用默认无参构造方式 实例化对象 返回集合
     * @param annotated
     * @return
     */
    private List<?> instantiateBeans(Set<Class<?>> annotated) {

        return annotated.stream().map(this::instantiateClass).collect(Collectors.toList());
    }

    /**
     * 根据指定的代理类 使用默认无参方式，实力化对象
     * @param aClass
     * @return
     */
    private Object instantiateClass(Class<?> aClass){

        try {
            return aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("实力创建失败，Could not instantiate class" + aClass);
        }
    }


    /**
     * Reflections create Object Instantiates
     * 反射 创建代理对象实例
     * @param bean
     * @return
     */
    private MyCustomProxy createProxy(Object bean){

        InvocationHandler handler = new MyInvocationHandler(bean);
        Object proxyObj =
                Proxy.newProxyInstance(ProxyApp.class.getClassLoader(), bean.getClass().getInterfaces(),
                handler);
        return new MyCustomProxy(Arrays.asList(bean.getClass().getInterfaces()), proxyObj);
    }

    /**
     * 返回指定类型的代理类对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> clazz){
        Object proxy = beanRegistry.stream()
                .filter(p -> p.hasInterface(clazz))
                .findFirst()
                .map(MyCustomProxy::getJdkProxy)
                .orElseThrow(() -> new RuntimeException("No Bean found for class"  + clazz));

        return clazz.cast(proxy);
    }

}

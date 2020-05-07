package com.test.situ.proxy.proxydemo;

import com.test.situ.proxy.proxydemo.framework.MyCustomTransaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;


/**
 * 自定义触发管理器
 */
public class MyTransactionInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Class<?> targetClass;

    public MyTransactionInvocationHandler(Object targetObject){
        this.target = targetObject;   // 代理类
        this.targetClass = targetObject.getClass();  // 代理类对象
    }

    /**
     * 获取所有代理类的方法（方法名，方法参数类型）
     * @param method
     * @return
     * @throws NoSuchMethodException
     */
    private Method getOverriddenMethod(Method method) throws NoSuchMethodException{

        // 获取所有代理类的方法（方法名，方法参数类型）
        return targetClass.getDeclaredMethod(method.getName(), method.getParameterTypes());

    }

    /**
     * 代理执行器
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method targetMethod = getOverriddenMethod(method);

        return getTransacactionalMethod(targetMethod).map(
                annotation -> handleTransactionalMethod(method, args, annotation))
                .orElseGet(() -> uncheckedInvoke(method, args));

    }

    private Object handleTransactionalMethod(Method method, Object[] args, MyCustomTransaction annotation){

        Object result;
        System.out.println(String.format("Opening transaction [%s] with param %s ", annotation.value(),
                Arrays.toString(args)));

        try {
            result = uncheckedInvoke(method, args);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(String.format("Rollback transaction %s ....", annotation));
            throw e;
        }

        System.out.println(String.format("Commit transaction %s....", annotation.value()));
        return result;
    }

    private Object uncheckedInvoke(Method method, Object[] args) throws RuntimeException {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ProxyInvocationFailedException("Could not invoke method " + method.getName(), e);
        }
    }


    /**
     *  ???
     * @param method
     * @return
     */
    private Optional<MyCustomTransaction> getTransacactionalMethod(Method method){
        Object result;

        return Optional.ofNullable(method.getDeclaredAnnotation(MyCustomTransaction.class));
    }
}

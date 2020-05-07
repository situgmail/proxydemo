package com.test.situ.proxy.proxydemo;

public class ProxyInvocationFailedException extends RuntimeException {

    public ProxyInvocationFailedException(String message, Throwable e) {
        super(message, e);
    }
}

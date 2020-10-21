package com.neeraj.dynamicProxy.handlers;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author neeraj on 21/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class ExceptionUnwrappingInvocationHandler implements InvocationHandler, Serializable {
    private static final Long serialVersionUID = 1L;

    private final InvocationHandler invocationHandler;

    public ExceptionUnwrappingInvocationHandler(InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return invocationHandler.invoke(proxy, method, args);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }

    public InvocationHandler getNestedInvocationHandler() {
        return invocationHandler;
    }
}

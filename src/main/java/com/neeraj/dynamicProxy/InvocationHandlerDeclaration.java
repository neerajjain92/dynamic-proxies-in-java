package com.neeraj.dynamicProxy;

import java.lang.reflect.Method;

/**
 * @author neeraj on 20/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * Any method called on the proxy object is dispatched to the invoke() method of the InvocationHandler.
 */
public class InvocationHandlerDeclaration {

    public interface InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
    }
}

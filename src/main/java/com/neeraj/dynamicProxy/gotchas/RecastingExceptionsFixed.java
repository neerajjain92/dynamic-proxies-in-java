package com.neeraj.dynamicProxy.gotchas;

import com.neeraj.dynamicProxy.handlers.ExceptionUnwrappingInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @author neeraj on 21/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class RecastingExceptionsFixed {

    public static void main(String[] args) {
        Comparable<String> comp = (Comparable<String>) Proxy.newProxyInstance(
                Comparable.class.getClassLoader(),
                new Class<?>[]{Comparable.class},
                new ExceptionUnwrappingInvocationHandler(
                        ((proxy, method, params) ->
                                method.invoke(params[0], proxy))
                )
        );

        System.out.println(comp.compareTo("hello"));
    }
}

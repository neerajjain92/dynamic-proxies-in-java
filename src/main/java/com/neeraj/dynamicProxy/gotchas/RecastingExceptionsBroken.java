package com.neeraj.dynamicProxy.gotchas;

import java.lang.reflect.Proxy;

/**
 * @author neeraj on 20/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * java.lang.reflect.UndeclaredThrowableException
 */
public class RecastingExceptionsBroken {

    public static void main(String[] args) {
        Comparable<String> comparable = (Comparable<String>) Proxy.newProxyInstance(
                Comparable.class.getClassLoader(),
                new Class<?>[]{Comparable.class},
                (proxy, method, params) ->
                        method.invoke(params[0], proxy)); // Here we are reversing the comparison

        System.out.println(comparable.compareTo("hello"));
    }
}

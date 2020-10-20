package com.neeraj.dynamicProxy;

import java.lang.reflect.Proxy;

/**
 * @author neeraj on 20/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class ProxyNameDemo {

    public static void main(String[] args) {
        System.out.println(
                Proxy.newProxyInstance(
                        ISODateParser.class.getClassLoader(),
                        new Class<?>[]{ISODateParser.class},
                        (proxy, method, args1) -> null
                ).getClass()
        );
    }
}

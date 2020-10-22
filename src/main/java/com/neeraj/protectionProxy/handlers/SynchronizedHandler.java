package com.neeraj.protectionProxy.handlers;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author neeraj on 22/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * Instead of handcrafting a synchronized protection proxy, we can write a
 * SynchronizedHandler. This synchronizes all calls to methods on our subject, using proxy as our monitor
 * lock.
 */
public class SynchronizedHandler<S> implements InvocationHandler, Serializable {
    private static final Long serialVersionUID = 1L;
    private final S subject;

    public SynchronizedHandler(S subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Synchronizing on proxy instance, which is similar to how
        // Vector and Collection.synchronizedList() work
        synchronized (proxy) {
            return method.invoke(subject, args);
        }

        /**
         * Our {@link com.neeraj.Proxies} façade now gets a synchronizedProxy() static factory method.
         */
    }
}

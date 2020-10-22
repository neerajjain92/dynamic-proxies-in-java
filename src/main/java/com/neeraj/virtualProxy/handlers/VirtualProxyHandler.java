package com.neeraj.virtualProxy.handlers;

import com.neeraj.virtualProxy.CustomHashMap;
import com.neeraj.virtualProxy.VirtualCustomMapVirtualProxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * @author neeraj on 21/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * In {@link VirtualCustomMapVirtualProxy} we wrote a virtual proxy for
 * lazily creating our {@link CustomHashMap} instances. Now here we are creating
 * {@link VirtualProxyHandler} that takes a {@link Supplier<? extends S>} as a parameter.
 */
public class VirtualProxyHandler<S> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 1L;
    private final Supplier<? extends S> subjectSupplier;
    private S subject;

    public VirtualProxyHandler(Supplier<? extends S> subjectSupplier) {
        this.subjectSupplier = subjectSupplier;
    }

    private S getSubject() {
        if (subject == null) subject = subjectSupplier.get();
        return subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(getSubject(), args);
    }
}

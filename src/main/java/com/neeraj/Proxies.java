package com.neeraj;

import com.neeraj.dynamicProxy.handlers.ExceptionUnwrappingInvocationHandler;
import com.neeraj.dynamicProxy.util.MethodTurboBooster;
import com.neeraj.protectionProxy.handlers.SynchronizedHandler;
import com.neeraj.virtualProxy.handlers.VirtualProxyHandler;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author neeraj on 21/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * Facade for all our dynamic proxies and related pattern implementation.
 */
public final class Proxies {

    private Proxies() {
    }

    // tag::castProxy()[]

    /**
     * @param intf    The interface to implment and cast to.
     * @param handler InvocationHandler for all methods.
     */
    public static <S> S castProxy(Class<? super S> intf,
                                  InvocationHandler handler) {
        Objects.requireNonNull(intf, "intf == null");
        Objects.requireNonNull(handler, "handler == null");

        return (S) MethodTurboBooster.boost(Proxy.newProxyInstance(
                intf.getClassLoader(),
                new Class<?>[]{intf},
                new ExceptionUnwrappingInvocationHandler(handler)
        ));
    }

    public static <S> S simpleProxy(Class<? super S> subjectInterface,
                                    S subject) {
        return castProxy(subjectInterface,
                (InvocationHandler & Serializable) (proxy, method, args) ->
                {
                    String formatted = String.format("Invoking %s.%s.(%s)",
                            method.getDeclaringClass().getCanonicalName(),
                            method.getName(),
                            args == null ? "" :
                                    Stream.of(args).map(String::valueOf)
                                            .collect(Collectors.joining(", "))
                    );
                    System.out.println(formatted);
                    return method.invoke(subject, args);
                }
        );
    }

    /**
     * PECS : Producer extends, Consumer Super principle.
     * Here subjectSupplier is a supplier which produces some subject, hence wild-card is
     */
    public static <S> S virtualProxy(Class<? super S> subjectInterface, Supplier<? extends S> subjectSupplier) {
        Objects.requireNonNull(subjectSupplier, "subjectSupplier==null");
        return castProxy(subjectInterface, new VirtualProxyHandler<>(subjectSupplier));
    }


    public static <S> S synchronizedProxy(Class<? super S> subjectInterface, S subject) {
        Objects.requireNonNull(subject, "subject==null");
        return castProxy(subjectInterface, new SynchronizedHandler<>(subject));
    }
}

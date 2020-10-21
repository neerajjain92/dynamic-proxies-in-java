package com.neeraj.dynamicProxy.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author neeraj on 20/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * Let us have a look at how to create our own InvocationHandler. In our LoggingInvocationHandler, we log
 * method calls and how long they take.
 */
public class LoggingInvocationHandler implements InvocationHandler {

    private final Logger logger;
    private final Object obj;

    public LoggingInvocationHandler(Logger logger, Object obj) {
        this.logger = logger;
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info(() -> "Entering..." + toString(method, args));

        // Optimization - nanoTime() is an expensive native call
        final boolean logFine = logger.isLoggable(Level.FINE);
        long start = logFine ? System.nanoTime() : 0;
        try {
            return method.invoke(obj, args);
        } finally {
            long nanos = logFine ? System.nanoTime() - start : 0;
            logger.info(() -> "Exiting..." + toString(method, args));
            if (logFine) logger.fine(() -> "Time--->" + nanos + " ns");
        }
    }

    private static String toString(Method method, Object[] args) {
        return String.format("%s.%s(%s)",
                method.getDeclaringClass().getCanonicalName(),
                method.getName(),
                args == null ? "" :
                        Stream.of(args).map(String::valueOf)
                                .collect(Collectors.joining(", ")));
    }
}

package com.neeraj.dynamicProxy.enhancedStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author neeraj on 23/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * Our {@link EnhancedStreamHandler} contains static inner class {@link Key}, that is used to determine "uniqueness"
 * among stream elements. All methods are routed via {@link EnhancedStreamHandler#invoke(Object, Method, Object[])} method.
 * Inside invoke() we first identify whether the method is our enhanced distinct() method. if it is we call it directly,
 * otherwise if the return type is {@link EnhancedStream} we find the matching method in our methodMap and invoke that on
 * our delegate. In this case we retun the proxy which is an instance of type {@link EnhancedStream}.
 * <p>
 * Alternatively we return the result of calling the method directly on our delegate.
 */
public class EnhancedStreamHandler<T> implements InvocationHandler {

    private Stream<T> delegate;

    public EnhancedStreamHandler(Stream<T> delegate) {
        this.delegate = delegate;
    }

    /**
     * Methods inside {@link EnhancedStream}
     */
    private static final Method ENHANCED_DISTINCT;
    private static final Method ENHANCED_DISTINCT_WITH_KEY;

    /**
     * methodMap contains a map from all non-static Stream method,
     * to the matching EnhancedStream methods.
     */
    private static final Map<Method, Method> methodMap =
            Stream.of(Stream.class.getMethods())
                    .filter(m -> !Modifier.isStatic(m.getModifiers()))
                    .collect(Collectors.toUnmodifiableMap(
                            EnhancedStreamHandler::getEnhancedStreamMethod,
                            Function.identity()
                    ));

    static {
        try {
            ENHANCED_DISTINCT = EnhancedStream.class.getMethod(
                    "distinct", ToIntFunction.class, BiPredicate.class, BinaryOperator.class);

            ENHANCED_DISTINCT_WITH_KEY = EnhancedStream.class.getMethod(
                    "distinct", Function.class, BinaryOperator.class);
        } catch (NoSuchMethodException ex) {
            throw new Error(ex);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getReturnType() == EnhancedStream.class) {
            if (method.equals(ENHANCED_DISTINCT)) {
                distinct((ToIntFunction<T>) args[0], (BiPredicate<T, T>) args[1], (BinaryOperator<T>) args[2]);
            } else if (method.equals(ENHANCED_DISTINCT_WITH_KEY)) {
                distinct((Function<T, ?>) args[0], (BinaryOperator<T>) args[1]);
            } else {
                /**
                 * These methods can be either filters.....
                 */
                Method matchMethod = methodMap.get(method);
                System.out.println("Matched Method from methodMap is ....." + matchMethod);
                this.delegate = (Stream<T>) matchMethod.invoke(delegate, args);
            }
            return proxy;
        } else {
            return method.invoke(this.delegate, args);
        }
    }

    private void distinct(ToIntFunction<T> hashCode,
                          BiPredicate<T, T> equals,
                          BinaryOperator<T> merger) {
        distinct(t -> new Key<>(t, hashCode, equals), merger);
    }

    private void distinct(Function<T, ?> keyGen,
                          BinaryOperator<T> merger) {
        delegate = delegate.collect(Collectors.toMap(keyGen::apply, Function.identity(), merger, LinkedHashMap::new))
                .values()
                .stream();
    }

    /**
     * Since {@link EnhancedStream} is a subclass of {@link Stream}, it has to
     * contain all the methods of Stream, we can safely do the lookup,
     * throwing an error if we can't find one.
     */
    private static Method getEnhancedStreamMethod(Method m) {
        try {
            return EnhancedStream.class.getMethod(
                    m.getName(), m.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new Error(e);
        }
    }

    private static final class Key<E> {
        private final E e;
        private final ToIntFunction<E> hashCode;
        private final BiPredicate<E, E> equals;

        public Key(E e, ToIntFunction<E> hashCode, BiPredicate<E, E> equals) {
            this.e = e;
            this.hashCode = hashCode;
            this.equals = equals;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) return false;
            Key<E> that = (Key<E>) obj;
            return equals.test(this.e, that.e);
        }

        @Override
        public int hashCode() {
            return hashCode.applyAsInt(e);
        }
    }
}

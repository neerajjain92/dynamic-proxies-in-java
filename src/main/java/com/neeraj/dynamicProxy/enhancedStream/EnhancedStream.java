package com.neeraj.dynamicProxy.enhancedStream;

import java.lang.reflect.Proxy;
import java.util.Comparator;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * @author neeraj on 22/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * Our interface EnhancedStream extends Stream and adds a new distinct() method, it also
 * overrides all the methods that are returning Stream to instead return an {@link EnhancedStream}
 * Lastly we have to static factory methods for creating streams
 */
public interface EnhancedStream<T> extends Stream<T> {

    // New Enhanced Distinct
    EnhancedStream<T> distinct(ToIntFunction<T> hashCode, BiPredicate<T, T> equals, BinaryOperator<T> merger);

    EnhancedStream<T> distinct(Function<T, ?> keyGenerator, BinaryOperator<T> merger);

    // Inherited Methods with Enhanced return type.

    /**
     * PECS principle : Producer extends, Consumer super.
     */
    EnhancedStream<T> filter(Predicate<? super T> predicate);

    <R> EnhancedStream<R> map(Function<? super T, ? extends R> mapper);

    <R> EnhancedStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    EnhancedStream<T> distinct();

    EnhancedStream<T> sorted();

    EnhancedStream<T> sorted(Comparator<? super T> comparator);

    EnhancedStream<T> peek(Consumer<? super T> action);

    EnhancedStream<T> limit(long maxSize);

    EnhancedStream<T> skip(long n);

    EnhancedStream<T> takeWhile(Predicate<? super T> predicate);

    EnhancedStream<T> dropWhile(Predicate<? super T> predicate);

    EnhancedStream<T> sequential();

    EnhancedStream<T> parallel();

    EnhancedStream<T> unordered();

    EnhancedStream<T> onClose(Runnable closeHandler);

    // static factory methods
    @SafeVarargs
    @SuppressWarnings("varargs")
    static <E> EnhancedStream<E> of(E... elements) {
        return from(Stream.of(elements));
    }

    static <E> EnhancedStream<E> from(Stream<E> stream) {
        return (EnhancedStream<E>) Proxy.newProxyInstance(
                EnhancedStream.class.getClassLoader(),
                new Class<?>[]{EnhancedStream.class},
                new EnhancedStreamHandler<>(stream)
        );
    }
}

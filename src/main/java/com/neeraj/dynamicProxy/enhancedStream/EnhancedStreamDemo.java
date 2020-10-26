package com.neeraj.dynamicProxy.enhancedStream;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author neeraj on 24/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class EnhancedStreamDemo {

    /**
     * Functional Interface that produces int valued result based on
     * method's Name hashCode and adding the parameter count to it.
     */
    private static final ToIntFunction<Method> HASH_CODE =
            method -> method.getName().hashCode() + method.getParameterCount();

    /**
     * Functional Interface that represent the predicate(equals in this case)
     * for the given 2 methods.
     * <p>
     * We are checking distinct based on
     * 1st Name
     * 2nd Parameter Count
     * 3rd Parameter Types.
     */
    private static final BiPredicate<Method, Method> EQUALS = ((method1, method2) -> method1.getName().equals(method2.getName()) &&
            method1.getParameterCount() == method2.getParameterCount() &&
            Arrays.equals(method1.getParameterTypes(),
                    method2.getParameterTypes()));

    /**
     * Functional interface that represents an operation upon 2 operands of the same type producing the result of the same
     * type as operands, this is a special case of {@link java.util.function.BiFunction} for the case where operands and the result
     * are of all same type.
     */
    private static final BinaryOperator<Method> MERGE =
            ((method1, method2) -> {
                if (method1.getReturnType().isAssignableFrom(method2.getReturnType())) {
                    return method2;
                }
                if (method2.getReturnType().isAssignableFrom(method1.getReturnType())) {
                    return method1;
                }

                throw new IllegalArgumentException("Conflicting Return types " + method1.getReturnType().getCanonicalName()
                        + " and " +
                        method2.getReturnType().getCanonicalName());
            });

    private static final Comparator<Method> METHOD_COMPARATOR =
            Comparator.comparing(Method::getName) // First comparing by name and if name matches then comparing based on Parameter types.
                    .thenComparing(method -> Arrays.toString(method.getParameterTypes()));

    public static void main(String[] args) {

        System.out.println("-------------->Normal ArrayDeque clone() Methods :: <--------------");

        Stream.of(ArrayDeque.class.getMethods())
                .filter(method -> method.getName().equalsIgnoreCase("clone"))
                .distinct()
                .forEach(EnhancedStreamDemo::print);

        System.out.println();

        System.out.println("--------------> Enhanced Distinct ArrayDeque :: <--------------");

        EnhancedStream.of(ArrayDeque.class.getMethods())
                .filter(method -> method.getName().equalsIgnoreCase("clone"))
                .distinct(HASH_CODE, EQUALS, MERGE)
                .forEach(EnhancedStreamDemo::print);

        System.out.println("--------------> Normal ConcurrentSkipList Set :: <--------------");

        Stream.of(ConcurrentSkipListSet.class.getMethods())
                .filter(method -> method.getName().contains("Set"))
                .distinct()
                .sorted(METHOD_COMPARATOR)
                .forEach(EnhancedStreamDemo::print);

        System.out.println("--------------> Enhanced Distinct ConcurrentSkipList Set :: <--------------");
        EnhancedStream.of(ConcurrentSkipListSet.class.getMethods())
                .filter(method -> method.getName().contains("Set"))
                .distinct(HASH_CODE, EQUALS, MERGE)
                .sorted(METHOD_COMPARATOR)
                .forEach(EnhancedStreamDemo::print);
    }

    private static void print(Method m) {
        System.out.println(
                Stream.of(m.getParameterTypes())
                        .map(Class::getSimpleName)
                        .collect(Collectors.joining(
                                ", ",
                                " " + m.getReturnType().getSimpleName() + " " + m.getName() + "(",
                                ")"))
        );
    }
}

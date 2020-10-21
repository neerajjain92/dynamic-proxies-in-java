package com.neeraj.dynamicProxy.util;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author neeraj on 21/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About : Turbo-boosting reflection methods
 * <p>
 * One of the parameters passed into the invoke() method of the InvocationHandler is the Method that was
 * called. We often need to invoke this Method using reflection, and want this to be as fast as possible.
 * Since the proxy implements public methods, we also know that any Method field stored inside the
 * dynamic proxy classes is also public. Public methods are accessible, but unfortunately reflection
 * suffers from amnesia. Every time we call invoke() on the Method object, it checks from the stack who
 * the calling class is and whether it has the correct permissions. We can turn this check off by calling
 * setAccessible(true) on each of the Method fields. This reduces the overhead of method calls when using
 * reflection.
 */
public class MethodTurboBooster {

    /**
     * Method turbo boosting is enabled by default.  We call
     * setAccessible(true) on Method objects. Exceptions are silently ignored.
     * <p>
     * Method turbo boosting can be disabled with
     * -Dcom.neeraj.dynamicProxy.util.MethodTurboBooster.disabled=true
     */
    private final static Booster BOOSTER =
            Boolean.getBoolean(MethodTurboBooster.class.getName() + ".disabled") ? new BoosterOff() : new BoosterOn();

    public static <E> E boost(E proxy) {
        return BOOSTER.turboBoost(proxy);
    }

    public static Method boost(Method method) {
        return BOOSTER.turboBoost(method);
    }

    private interface Booster {
        <E> E turboBoost(E proxy);

        Method turboBoost(Method method);
    }

    private static class BoosterOn implements Booster {

        @Override
        public <E> E turboBoost(E proxy) {
            if (!(proxy instanceof Proxy))
                throw new IllegalArgumentException("Can Only turbo-boost instances of Proxy");

            try {
                for (var field : proxy.getClass().getDeclaredFields()) {
                    if (field.getType() == Method.class) {
                        field.setAccessible(true);
                        turboBoost((Method) field.get(null));
                    }
                }
                return proxy;
            } catch (IllegalAccessException | RuntimeException ex) {
                // could not turbo-boost - return proxy unchanged
                return proxy;
            }
        }

        @Override
        public Method turboBoost(Method method) {
            try {
                method.setAccessible(true);
            } catch (RuntimeException ex) {
                // could not turbo-boost - return method unchanged
            }
            return method;
        }
    }

    private static class BoosterOff implements Booster {

        @Override
        public <E> E turboBoost(E proxy) {
            if (!(proxy instanceof Proxy)) {
                throw new IllegalArgumentException("Can Only turbo-boost instances of Proxy");
            }
            return proxy;
        }

        @Override
        public Method turboBoost(Method method) {
            return method;
        }
    }
}

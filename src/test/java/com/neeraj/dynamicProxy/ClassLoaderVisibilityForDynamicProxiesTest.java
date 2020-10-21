package com.neeraj.dynamicProxy;

import com.neeraj.virtualProxy.CustomMap;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author neeraj on 20/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * In incorrectClassLoader test,
 * Map.class.getClassLoader() is a JDK class and thus loaded by Bootstrap class loader,
 * which doesn't know anything about CustomMap.
 * <p>
 * Correct approach, we should use the class loader that loaded our CustomMap, in this case
 * SystemClass Loader
 */
public class ClassLoaderVisibilityForDynamicProxiesTest {

    /**
     * Exception trace :
     * <p>
     * java.lang.IllegalArgumentException:
     * com.neeraj.handcraftedVirtualProxies.CustomMap referenced from a method is not visible
     * from class loader
     */
    @Test(expected = IllegalArgumentException.class)
    public void incorrectClassLoader() {
        try {
            Proxy.newProxyInstance(
                    Map.class.getClassLoader(),
                    new Class<?>[]{CustomMap.class},
                    (proxy, method, args) -> null
            );
        } catch (IllegalArgumentException ex) {
//            ex.printStackTrace(); // Uncomment this to see the real exception
            throw ex;
        }
    }

    @Test
    public void correctClassLoader() {
        Proxy.newProxyInstance(
                CustomMap.class.getClassLoader(), // Classloader where our new proxy class is being loaded.
                new Class<?>[]{CustomMap.class}, // interfaces array contains all interfaces that our proxy needs to implement.
                (proxy, method, args) -> null // Invocation Handler, is a functional interface that will be invoked whenever a
                // new method is being called on the proxy.
        );
    }


}

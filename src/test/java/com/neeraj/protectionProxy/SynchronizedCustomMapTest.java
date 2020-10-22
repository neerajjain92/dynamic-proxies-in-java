package com.neeraj.protectionProxy;

import com.neeraj.Proxies;
import com.neeraj.virtualProxy.CustomHashMap;
import com.neeraj.virtualProxy.CustomMap;
import org.junit.Test;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class SynchronizedCustomMapTest extends ConcurrentTest {

    @Test
    public void testSynchronizedCustomMap() {
        check(new SynchronizedCustomMap<>(new CustomHashMap<>()));
    }

    /**
     * here we are taking help of {@link com.neeraj.protectionProxy.handlers.SynchronizedHandler}
     * to achieve the Synchronization.
     */
    @Test
    public void testSynchronizedCustomMapUsingSynchronizedProxy() {
        check(Proxies.synchronizedProxy(CustomMap.class, new CustomHashMap<>()));
    }
}

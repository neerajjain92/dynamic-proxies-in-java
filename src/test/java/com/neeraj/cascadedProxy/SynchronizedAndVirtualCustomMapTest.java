package com.neeraj.cascadedProxy;

import com.neeraj.handcraftedVirtualProxies.CustomHashMap;
import com.neeraj.handcraftedVirtualProxies.VirtualCustomMapVirtualProxy;
import com.neeraj.protectionProxy.ConcurrentTest;
import com.neeraj.protectionProxy.SynchronizedCustomMap;
import org.junit.Test;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * The original GoF proxy class diagram has an association from Proxy to RealSubject.
 * A more flexible approach is for Proxy to instead point to the Subject interface.
 * This is how we’ve coded every one of our proxy examples so far. By pointing to Subject,
 * we can cascade our proxies: a protection proxy might
 * point to a virtual proxy, which in turn might point to a remote proxy, and then to the
 * RealSubject
 * <p>
 * In the below example, the instantiation of the Custom HashMap is delayed until actual
 * put() method is being invoked, and they are being proptected with concurrent access using Synchronized
 * proxy
 * <p>
 * --------------------Cascaded proxies in nut shell------------------
 * / Synchronized Proxy ------> Virtual Proxy --------> Real Subject. /
 * -------------------------------------------------------------------
 */
public class SynchronizedAndVirtualCustomMapTest extends ConcurrentTest {

    @Test
    public void testCascadedSynchronizedVirtualCustomMap() {
        var map = new SynchronizedCustomMap<Integer, Integer>
                (new VirtualCustomMapVirtualProxy<>(CustomHashMap::new));
        check(map);
        System.out.println("Map = " + map);
    }
}

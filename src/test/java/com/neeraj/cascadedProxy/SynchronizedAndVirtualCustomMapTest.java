package com.neeraj.cascadedProxy;

import com.neeraj.Proxies;
import com.neeraj.protectionProxy.ConcurrentTest;
import com.neeraj.protectionProxy.SynchronizedCustomMap;
import com.neeraj.virtualProxy.CustomHashMap;
import com.neeraj.virtualProxy.CustomMap;
import com.neeraj.virtualProxy.VirtualCustomMapVirtualProxy;
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
 * / Synchronized Proxy ------> Virtual Proxy --------> Real Subject Supplier. /
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

    /**
     * In the above example, we cascaded a synchronized protection proxy and a virtual
     * proxy using Handcrafted proxies, we can do the same with Handlers... our newly minted Dynamic Proxies
     * <p>
     * Important Point :
     * Note that we need to add a type witness to the call to virtualProxy() to specify that the generic type S
     * is CustomMap<Integer, Integer>.  Otherwise, the compiler will cast our virtual proxy to CustomHashMap,
     * resulting in a ClassCastException.
     * ||
     * ||
     * ||
     * \/
     * java.lang.ClassCastException: class com.sun.proxy.$Proxy4 cannot be cast to class com.neeraj.virtualProxy.CustomHashMap
     * (com.sun.proxy.$Proxy4 and com.neeraj.virtualProxy.CustomHashMap are in unnamed module of loader 'app')
     */
    @Test
    public void testCascadingViaHandlers() {
        CustomMap<Integer, Integer> map =
                /**
                 * Since this guy (synchronized proxy) needs a CustomMap, if in Proxies.virtualProxy we do not add a type witness of
                 * Proxies.<CustomMap<Integer, Integer>>virtualProxy(blah, blah) it will end up providing CustomHashMap to synchronized Proxy
                 * and that's and issue.
                 */
                Proxies.synchronizedProxy(CustomMap.class,
                        Proxies.<CustomMap<Integer, Integer>>virtualProxy(
                                CustomMap.class, CustomHashMap::new
                        )
                );

        check(map);

        // This time, the toString() method is auto-magically routed by our dynamic proxies.
        System.out.println("Map =" + map);
    }
}

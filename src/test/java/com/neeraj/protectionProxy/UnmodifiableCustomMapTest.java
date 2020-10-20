package com.neeraj.protectionProxy;

import com.neeraj.handcraftedVirtualProxies.CustomHashMap;
import org.junit.Test;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class UnmodifiableCustomMapTest extends ConcurrentTest {

    @Test
    public void testUnModifiableCustomMap() {
        check(new UnmodifiableCustomMap<>(new CustomHashMap<>()));
    }
}

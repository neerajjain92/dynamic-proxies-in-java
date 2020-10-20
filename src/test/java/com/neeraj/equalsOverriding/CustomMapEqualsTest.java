package com.neeraj.equalsOverriding;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class CustomMapEqualsTest {

    @Test
    public void testEqualsContract() {
        var real = new FixedEqualsInCustomHashMap.CustomHashMap<Integer, Integer>();
        IntStream.range(0, 10)
                .forEach(i -> real.put(i, i * i));

        var proxy1 = new FixedEqualsInHandcraftedProxies.UnmodifiableCustomMap<>(real);
        var proxy2 = new FixedEqualsInHandcraftedProxies.UnmodifiableCustomMap<>(real);
        var empty = new FixedEqualsInCustomHashMap.CustomHashMap<>();

        // reflexive
        assertEquals(real, real); // This will go inside FixedEqualsInCustomHashMap.CustomHashMap's equal
        assertEquals(proxy1, proxy1); // This will go inside FixedEqualsInHandcraftedProxies.UnmodifiableCustomMap
        assertEquals(proxy2, proxy2);

        // Symmetric
        assertEquals(real, proxy1);
        assertEquals(proxy1, real);
        assertEquals(proxy1, proxy2);
        assertEquals(proxy2, proxy1);


        // transitive
        if (real.equals(empty)
                && empty.equals(proxy2))
            assertEquals(real, proxy2);

        if (real.equals(proxy1)
                && proxy1.equals(proxy2))
            assertEquals(real, proxy2);

    }
}

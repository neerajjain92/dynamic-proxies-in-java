package com.neeraj.protectionProxy;

import com.neeraj.virtualProxy.CustomMap;

import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About:
 * <p>
 * ConcurrentTest modifies map concurrently using parallel streams.
 */
public class ConcurrentTest {

    public static final int SQUARES = 46_000;

    public void check(CustomMap<Integer, Integer> map) {
        System.out.println("Checking...." + map.getClass().getSimpleName());

        try {
            IntStream.range(0, SQUARES)
                    .parallel() // This will cause issues in simple HashMap
                    .forEach(i -> map.put(i, i * i));
        } catch (Exception ex) {
            System.err.println(ex); // carry on with stack.
        }

        // Let's count actual entries
        var entries = new LongAdder();
        map.forEach((k, v) -> entries.increment());

        System.out.println("entries = " + entries);
        System.out.println("Map Size = " + map.size());

        /**
         * The consequences could be worse than merely an incorrect value for size() and
         * missing entries. Corruption inside a HashMap might cause a node inside the map to
         * point back to itself, in turn causing an infinite loop when calling methods such as
         * forEach(). Even get(), put(), and remove() might not return if our key matches the
         * bucket containing the node cycle.
         */

        assertTrue("entries=" + entries + "," + "map.size=" + map.size(), entries.intValue() == map.size());
    }
}

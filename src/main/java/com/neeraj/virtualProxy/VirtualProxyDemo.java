package com.neeraj.virtualProxy;

import com.neeraj.Proxies;

import java.util.function.Supplier;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class VirtualProxyDemo {

    public static void main(String[] args) {
        CustomMap<String, Integer> map = new VirtualCustomMapVirtualProxy<>(CustomHashMap::new);
        System.out.println("Virtual Map created....");

        map.put("one", 1);
        map.put("ten", 10);

        System.out.println("map.get(\"one\") = " + map.get("one"));
        System.out.println("map.size() = " + map.size());
        System.out.println("Clearing map..");
        map.clear();
        System.out.println("map.size() = " + map.size());


        /**
         * Now we will create a virtual proxy using {@link com.neeraj.Proxies#virtualProxy(Class, Supplier)} method.
         */
        CustomMap<String, Integer> customMap = Proxies.virtualProxy(CustomMap.class, CustomHashMap::new);
        System.out.println("Custom Map using Virtual Proxy created....");

        customMap.put("Hello", 500);
        customMap.put("World", 100);
        System.out.println(customMap);
    }
}

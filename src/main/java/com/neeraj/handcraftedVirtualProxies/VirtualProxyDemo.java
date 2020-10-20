package com.neeraj.handcraftedVirtualProxies;

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
    }
}

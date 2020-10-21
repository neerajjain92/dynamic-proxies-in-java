package com.neeraj.virtualProxy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class CustomHashMap<K, V> implements CustomMap<K, V> {
    private final Map<K, V> map = new HashMap<>();

    {
        System.out.println("Custom HashMap Constructed");
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void forEach(BiConsumer action) {
        map.forEach(action);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}

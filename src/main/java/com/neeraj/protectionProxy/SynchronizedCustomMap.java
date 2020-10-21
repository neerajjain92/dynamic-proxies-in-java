package com.neeraj.protectionProxy;

import com.neeraj.virtualProxy.CustomMap;

import java.util.function.BiConsumer;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * To solve the race condition in CustomHashMap which we faced in {@link ConcurrentHashMapTest},
 * we will proxy it with a SynchronizedCustomMap.
 * This marks all methods as synchronized before delegating to the CustomMap methods.
 */
public class SynchronizedCustomMap<K, V> implements CustomMap<K, V> {

    private final CustomMap<K, V> map;

    public SynchronizedCustomMap(CustomMap<K, V> map) {
        this.map = map;
    }

    @Override
    public synchronized int size() {
        return map.size();
    }

    @Override
    public synchronized V get(Object key) {
        return map.get(key);
    }

    @Override
    public synchronized V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public synchronized V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public synchronized void clear() {
        map.clear();
    }

    @Override
    public synchronized void forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }
}

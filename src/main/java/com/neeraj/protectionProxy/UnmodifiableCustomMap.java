package com.neeraj.protectionProxy;

import com.neeraj.virtualProxy.CustomMap;

import java.util.function.BiConsumer;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * UnmodifiableCustomMap throws UnsupportedOperationException for modifier methods
 * <p>
 * If we wanted to make sure that whoever got the map would not be able to modify it, we could send
 * them an UnmodifiableCustomMap. This throws an UnsupportedOperationException whenever we call a
 * modifier method such as put().
 */
public class UnmodifiableCustomMap<K, V> implements CustomMap<K, V> {

    private final CustomMap<K, V> map;

    public UnmodifiableCustomMap(CustomMap<K, V> map) {
        this.map = map;
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
    public V put(Object key, Object value) {
        throw new UnsupportedOperationException("unmodifiable");
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException("unmodifiable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("unmodifiable");
    }

    @Override
    public void forEach(BiConsumer action) {
        map.forEach(action);
    }
}

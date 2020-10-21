package com.neeraj.virtualProxy;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class VirtualCustomMapVirtualProxy<K, V> implements CustomMap<K, V> {
    private final Supplier<CustomMap<K, V>> mapSupplier;
    private CustomMap<K, V> realMap;

    public VirtualCustomMapVirtualProxy(Supplier<CustomMap<K, V>> mapSupplier) {
        this.mapSupplier = mapSupplier;
    }

    private CustomMap<K, V> getRealMap() { // not thread-safe
        if (realMap == null) realMap = mapSupplier.get();
        return realMap;
    }

    @Override
    public int size() {
        return getRealMap().size();
    }

    @Override
    public V get(Object key) {
        return getRealMap().get(key);
    }

    @Override
    public V put(K key, V value) {
        return getRealMap().put(key, value);
    }

    @Override
    public V remove(Object key) {
        return getRealMap().remove(key);
    }

    @Override
    public void clear() {
        getRealMap().clear();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        getRealMap().forEach(action);
    }
}

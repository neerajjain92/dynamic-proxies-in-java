package com.neeraj.virtualProxy;

import java.util.function.BiConsumer;

/**
 * @author neeraj on 14/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public interface CustomMap<K, V> {

    int size();

    V get(Object key);

    V put(K key, V value);

    V remove(Object key);

    void clear();

    void forEach(BiConsumer<? super K, ? super V> action);

    String toString();
}

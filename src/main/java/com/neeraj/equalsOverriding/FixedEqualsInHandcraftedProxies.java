package com.neeraj.equalsOverriding;

import com.neeraj.virtualProxy.CustomMap;

import java.util.function.BiConsumer;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class FixedEqualsInHandcraftedProxies {
    public static class UnmodifiableCustomMap<K, V> implements CustomMap<K, V> {

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

        @Override
        public int hashCode() {
            return map.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return map.equals(obj);
        }
    }
}

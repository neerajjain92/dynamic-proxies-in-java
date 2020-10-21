package com.neeraj.equalsOverriding;

import com.neeraj.virtualProxy.CustomMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * The CustomHashMap requires a bit more work.
 * <p>
 * 1. We ensure that the parameter is an instance of CustomMap. This returns false when the parameter is
 * either null or an object other than a CustomMap.
 * <p>
 * 2. We check whether the class of the argument is a CustomHashMap. If it is the same class, we downcast
 * and compare the two map fields. A best practice is to mark the equals() method in CustomHashMap as final
 * to prevent unexpected overriding, as that could create non-symmetric equals() methods.
 * Since hashCode() needs to be paired with equals(), we also mark that as final.
 * <p>
 * 3. Otherwise, we return the result of reversing the equals condition as o.equals(this). Without the
 * reversing, we would end up in an infinite loop. Since equals() is required to be symmetric, the
 * result of o.equals(this) has to be the same as this.equals(o).
 */
public class FixedEqualsInCustomHashMap {
    public static class CustomHashMap<K, V> implements CustomMap<K, V> {
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

        @Override
        public final boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof CustomMap)) return false;

            if (obj instanceof CustomHashMap) {
                CustomHashMap<?, ?> that = (CustomHashMap<?, ?>) obj;
                return this.map.equals(that.map);
            }
            return obj.equals(this); // Reversing the equals condition.
        }

        @Override
        public final int hashCode() {
            return map.hashCode();
        }
    }

}

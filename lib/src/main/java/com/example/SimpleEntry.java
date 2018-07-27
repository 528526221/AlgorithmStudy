package com.example;

import java.util.Map;

/**
 * Date：2018/7/11
 * Desc：
 * Created by xuliangchun.
 */

public class SimpleEntry<K,V> implements Map.Entry<K,V>{
    private K key;
    private V value;

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V v) {
        V oldValue = value;
        value = v;
        return oldValue;
    }
}

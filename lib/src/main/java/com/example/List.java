package com.example;

/**
 * Date：2018/7/24
 * Desc：
 * Created by xuliangchun.
 */

public interface List<T> {
    int length();
    T get(int index);
    void insert(T element,int index);
    void add(T element);
    void addAtHeader(T element);
    T removeAt(int index);
    boolean empty();
    void clear();
}

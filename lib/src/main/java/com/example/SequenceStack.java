package com.example;

import java.util.Arrays;

/**
 * Date：2018/7/25
 * Desc：自定义栈，顺序存储结构实现
 * Created by xuliangchun.
 */

public class SequenceStack<T> {

    private int capacity;//数组的长度
    private int size;//元素的个数
    private Object[] elements;


    public SequenceStack() {
        capacity = 10;
        elements = new Object[capacity];
    }


    public SequenceStack(int capacity) {
        this.capacity = capacity;
        elements = new Object[capacity];
    }

    /**
     * 返回栈的长度
     * @return
     */
    public int length(){
        return size;
    }

    /**
     * 弹出
     */
    public T pop(){
        if (size==0){
            throw new IndexOutOfBoundsException("栈中元素个数为0");
        }
        T oldValue = (T) elements[size-1];
        elements[--size] = null;
        return oldValue;
    }

    /**
     * 入栈
     * @param element
     */
    public void push(T element){
        ensureCapacity(size+1);
        elements[size++] = element;
    }

    /**
     * 确保这个长度在数组范围内，如果超出则扩容
     * @param minCapacity
     */
    private void ensureCapacity(int minCapacity) {
        //如果数组的长度小于目前所需的长度
        if (capacity<minCapacity){
            while (capacity<minCapacity){
                capacity <<= 1;
            }
            elements = Arrays.copyOf(elements,capacity);
        }

    }

    /**
     * 判断栈是否空
     * @return
     */
    public boolean empty(){
        return size == 0;
    }

    /**
     * 清空栈
     */
    public void clear(){
        Arrays.fill(elements,null);
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0){
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            for (int i=0;i<size;i++){
                sb.append(elements[i]+",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("]");
            return sb.toString();
        }

    }
}

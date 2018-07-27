package com.example;

/**
 * Date：2018/7/25
 * Desc：自定义队列，顺序存储结构实现
 * Created by xuliangchun.
 */

public class SequenceQueue<T> {
    private int capacity;//数组容量
    private Object[] elements;//数组保存队列中的元素
    private int front;//即将出队列的元素的索引
    private int rear;//即将进入队列的元素的索引
    //队列和线性表的顺序存储的一般实现（如ArrayList）最大的不同就在于此，队列中每个元素在数组中的位置是固定不变的,变的只是front和rear.
    //有新的元素进入队列时，rear+1；有元素从队列中移除时，front+1

    //固定容量就会存在一个问题，假满


    public SequenceQueue() {
        capacity = 5;
        elements = new Object[capacity];
        front = 0;
        rear = 0;
    }

    public SequenceQueue(int capacity) {
        this.capacity = capacity;
        elements = new Object[capacity];
    }

    public void add(T element){
        if (rear>capacity-1){
            throw new IndexOutOfBoundsException("队列已满"+element);
        }else {
            elements[rear++] = element;
        }
    }

    public T remove(){
        if (front == rear){
            throw new IndexOutOfBoundsException("空队列");
        }else {
            T del = (T) elements[front];
            elements[front++] = null;
            return del;
        }

    }


    @Override
    public String toString() {
        if (front == rear){
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            for (int i=front;i<rear;i++){
                sb.append(elements[i]+",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("]");
            return sb.toString();
        }

    }
}

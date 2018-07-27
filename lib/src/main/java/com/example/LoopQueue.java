package com.example;

/**
 * Date：2018/7/26
 * Desc：循环队列  顺序存储结构  避免假满现象
 * Created by xuliangchun.
 */

public class LoopQueue<T> {
    private int capacity;//数组容量
    private Object[] elements;//数组保存队列中的元素
    private int front;//即将出队列的元素的索引
    private int rear;//即将进入队列的元素的索引


    public LoopQueue() {
        capacity = 5;
        elements = new Object[capacity];
        front = 0;
        rear = 0;
    }

    public void add(T element){
        if (rear == front && elements[front] != null){
            throw new IndexOutOfBoundsException("队列已满");
        }
        elements[rear++] = element;
        rear = rear == capacity ? 0 : rear;
    }


    public T remove(){
        if (empty()){
            throw new IndexOutOfBoundsException("队列为空");
        }
        T del = (T) elements[front];
        elements[front++] = null;
        front = front == capacity ? 0 : front;

        return del;
    }

    public boolean empty(){
        return rear == front && elements[front] == null;
    }

    public int length(){
        if (empty()){
            return 0;
        }
        if (rear>front){
            return rear - front;
        }else {
            return capacity-front+rear;
        }
    }


    @Override
    public String toString() {
        if (empty()){
            return "[]";
        }else {
            if (front<rear){
                StringBuilder sb = new StringBuilder("[");
                for (int i=front;i<rear;i++){
                    sb.append(elements[i]+",");
                }
                sb.deleteCharAt(sb.length()-1);
                sb.append("]");
                return sb.toString();
            }else {
                StringBuilder sb = new StringBuilder("[");
                for (int i=front;i<capacity;i++){
                    sb.append(elements[i]+",");
                }
                for (int i=0;i<rear;i++){
                    sb.append(elements[i]+",");
                }
                sb.deleteCharAt(sb.length()-1);
                sb.append("]");
                return sb.toString();
            }

        }

    }
}

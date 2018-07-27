package com.example;

/**
 * Date：2018/7/26
 * Desc：双向链表 每个节点保留两个引用prev和next
 * Created by xuliangchun.
 */

public class DuLinkList<T> {
    private class Node{
        private T data;
        private Node prev;
        private Node next;

        public Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node header;//头节点
    private Node tail;//尾节点
    private int size;//节点数量

    public DuLinkList() {
        header = null;
        tail = null;
    }
    public int length(){
        return size;
    }

    public T get(int index){
        return getNodeByIndex(index).data;
    }

    private Node getNodeByIndex(int index){
        if (index < 0 || index > size-1){
            throw new IndexOutOfBoundsException("线性表索引越界");
        }
        if (index<=size/2){
            Node current = header;
            for (int i=0;i<=size/2 && current != null;i++,current = current.next){
                if (i == index){
                    return current;
                }
            }

        }else {
            Node current = tail;
            for (int i = size-1;i>size/2 && current != null;i--,current = current.prev){
                if (i == index){
                    return current;
                }
            }
        }

        return null;

    }

    public int locate(T element){
        Node current = header;
        for (int i=0;i<size && current != null;i++,current = current.next){
            if (current.data.equals(element)){
                return i;
            }
        }
        return -1;
    }

    public void insert(T element,int index){
        if (header == null){
            add(element);
        }else {
            if (index == 0){
                Node old = header;
                header = new Node(element,null,old);
                old.prev = header;
            }else {
                Node prev = getNodeByIndex(index - 1);
                Node next = prev.next;
                Node node = new Node(element,prev,next);
                prev.next = node;
                next.prev = node;
            }
        }
        size++;
    }

    public void add(T element){
        if (header == null){
            header = new Node(element,null,null);
            tail = header;
        }else {
            Node node = new Node(element,tail,null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T remove(){
        return delete(size-1);
    }

    public T delete(int index){
        if (index<0 && index>size-1){
            throw new IndexOutOfBoundsException("线性表索引越界");
        }
        Node del = null;
        if (index == 0){
            //删除头节点
            del = header;
            header = header.next;
            header.prev = null;
            del.next = null;
        }else {
            Node prev = getNodeByIndex(index-1);
            del = prev.next;
            Node next = del.next;
            prev.next = next;
            //避免del为尾节点
            if (next != null){
                next.prev = prev;
            }
            del.prev = null;
            del.next = null;
        }

        size --;
        return null;
    }

    public boolean empty(){
        return size == 0;
    }

    public void clear(){
        header = null;
        tail = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0){
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            Node node = header;
            for (int i=0;i<size;i++){
                sb.append(node.data+",");
                node = node.next;
            }

            return sb.deleteCharAt(sb.length()-1).append("]").toString();




        }
    }
}

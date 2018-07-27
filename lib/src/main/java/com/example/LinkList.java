package com.example;

/**
 * Date：2018/7/24
 * Desc：线性表链式结构
 * Created by xuliangchun.
 */

public class LinkList<T> implements List<T>{

    private Node header;
    private Node tail;
    private int size;


    public LinkList() {
        header = null;
        tail = null;
        size = 0;
    }

    public LinkList(T element) {
        header = new Node(element,null);
        tail = header;
        size++;
    }




    public Node getNodeByIndex(int index){
        Node current = header;
        for (int i=0;i<size;i++){
            if (i == index){
                return current;
            }else {
                current = current.next;
            }
        }
        return null;

    }

    @Override
    public int length() {
        return size;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).data;
    }

    @Override
    public void insert(T element, int index) {
        if (index == 0){
            addAtHeader(element);
        }else {
            Node prev = getNodeByIndex(index-1);
            prev.next = new Node(element,prev.next);
            size++;
        }

    }

    @Override
    public void add(T element) {
        if (header == null){
            header = new Node(element,null);
            tail = header;
        }else {
            tail.next = new Node(element,null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void addAtHeader(T element) {
        header = new Node(element,header);
        if (tail == null){
            tail = header;
        }
        size++;
    }

    @Override
    public T removeAt(int index) {
        Node current = null;

        if (index == 0){
            //删除头节点
            current = header;
            header = header.next;
            if (current.equals(tail)){
                tail = null;
            }
        }else {
            Node prev = getNodeByIndex(index-1);
            current = prev.next;
            prev.next = current.next;
            if (current.equals(tail)){
                tail = prev;
            }
        }
        size--;


        return current.data;
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public void clear() {
        header = null;
        tail = null;
        size = 0;
    }


    public class Node {
        private T data;
        private Node next;

        public Node() {

        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (data != null ? !data.equals(node.data) : node.data != null) return false;
            return next != null ? next.equals(node.next) : node.next == null;

        }

        @Override
        public int hashCode() {
            int result = data != null ? data.hashCode() : 0;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LinkList{" +
                "header=" + header +
                ", tail=" + tail +
                ", size=" + size +
                '}';
    }
}

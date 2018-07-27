package com.example;

/**
 * Date：2018/7/26
 * Desc：队列 链式存储结构
 * Created by xuliangchun.
 */

public class LinkQueue<T> {
    private int size;//队列中节点数
    private Node front;//头节点
    private Node rear;//尾节点

    private class Node {
        private T data;
        private Node next;

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

    public int length() {
        return size;
    }

    public void add(T element) {
        if (front == null) {
            front = new Node(element, null);
            rear = front;
        } else {
            Node node = new Node(element, null);
            rear.next = node;
            rear = node;
        }
        size++;
    }

    /**
     * 移除
     *
     * @return
     */
    public T remove() {
        if (size == 0){
            throw new IndexOutOfBoundsException("队列为空");
        }
        Node del = front;
        front = front.next;
        del.next = null;
        size--;
        if (size == 0){
            rear = null;
        }
        return del.data;
    }

    @Override
    public String toString() {
        if (size == 0){
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            Node node = front;
            for (int i=0;i<size;i++){
                sb.append(node.data+",");
                node = node.next;
            }

            return sb.deleteCharAt(sb.length()-1).append("]").toString();
        }
    }


//    @Override
//    public String toString() {
//        return "LinkQueue{" +
//                "size=" + size +
//                ", front=" + front +
//                ", rear=" + rear +
//                '}';
//    }
}

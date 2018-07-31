package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/7/30
 * Desc：子节点链表示法：让父节点记住它的所有子节点，子节点以节点链形式表示
 * Created by xuliangchun.
 */

public class TreeChild<E> {
    public static class Node<T>{
        private T data;
        private SonNode first;//第一个子节点

        public Node(T data, SonNode first) {
            this.data = data;
            this.first = first;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", first=" + first +
                    '}';
        }
    }

    public static class SonNode{
        private int pos;//当前节点的位置，在数组中的位置
        private SonNode next;

        public SonNode(int pos, SonNode next) {
            this.pos = pos;
            this.next = next;
        }
    }

    private Node<E>[] nodes;
    private int nodeNums;//节点数
    private int treeSize = 10;//树的容量，也就是数组的容量大小

    public TreeChild() {
        nodes = new Node[treeSize];
        nodeNums = 0;
    }

    /**
     * 为指定节点添加子节点
     * @param data
     * @param parent
     */
    public void addNode(E data,Node<E> parent){
        if (nodeNums>nodes.length-1){
            throw new IndexOutOfBoundsException("树已满");
        }
        if (parent == null){
            //添加根节点
            nodes[nodeNums++] = new Node<>(data,null);
        }else {
            //区分parent是不是已经有子节点
            SonNode first = parent.first;
            if (first == null){
                parent.first = new SonNode(nodeNums,null);
                nodes[nodeNums++] = new Node<>(data,null);
            }else {
                while (first.next != null){
                    first = first.next;
                }
                first.next = new SonNode(nodeNums,null);
                nodes[nodeNums++] = new Node<>(data,null);
            }
        }



    }

    /**
     * 树是否为空
     * @return
     */
    public boolean empty(){
        return nodeNums == 0;
    }

    /**
     * 树的根节点
     * @return
     */
    public Node<E> getRootNode(){
        return nodes[0];
    }

    /**
     * 获取指定节点的所有子节点
     * @param parent
     * @return
     */
    public List<Node<E>> getNodeChild(Node<E> parent){
        List<Node<E>> nodeList = new ArrayList<>();
        SonNode next = parent.first;
        while (next != null){
            nodeList.add(nodes[next.pos]);
            next = next.next;
        }
        return nodeList;
    }

    /**
     * 获取指定节点的父节点
     * @param child
     * @return
     */
    public Node getNodeParent(Node<E> child){
        for (int i=0;i<nodeNums;i++){
            Node<E> node = nodes[i];
            List<Node<E>> childs = getNodeChild(node);
            for (int j=0;j<childs.size();j++){
                if (childs.get(j) == child){
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * 获取指定节点的第index个子节点
     * @param parent
     * @param index
     * @return
     */
    public Node getNodeChild(Node<E> parent,int index){
        SonNode next = parent.first;

        for (int i=0;next!=null;i++){
            if (i == index){
                return nodes[next.pos];
            }
        }
        return null;
    }

    /**
     * 获取节点在数组中的位置
     * @param node
     * @return
     */
    public int getNodePos(Node<E> node){
        for (int i=0;i<treeSize;i++){
            if (nodes[i] == node){
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取树的深度
     * @return
     */
    public int deep(){
        //一直找子节点
        int deep = 0;
        for(int i=0;i<nodeNums;i++){
            Node<E> node = nodes[i];
            int currentDeep = 1;
            SonNode next = node.first;
            while (next != null){
                currentDeep++;
                next = next.next;
            }
            if (currentDeep>deep){
                deep = currentDeep;
            }
        }
        return deep;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i=0;i<nodeNums;i++){
            sb.append(nodes[i].data+",");
        }
        return sb.deleteCharAt(sb.length()-1).append("]").toString();
    }

}

package com.example;

import java.util.ArrayList;
import java.util.List;
/**
 * Date：2018/7/27
 * Desc：父节点表示法：每个子节点都记录它的父节点
 * Created by xuliangchun.
 */

public class TreeParent<E> {

    public static class Node<T>{
        private T data;
        int parent;

        public Node(T data, int parent) {
            this.data = data;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", parent=" + parent +
                    '}';
        }
    }

    private Node<E>[] nodes;//使用一个数组来记录树里的所有节点
    private int nodeNums;//记录节点数

    public TreeParent() {
        nodes = new Node[10];
        nodeNums = 0;
    }

    public void addNode(E data,Node parent){
        Node<E> node = new Node<>(data,getNodePos(parent));
        if (nodeNums > nodes.length - 1){
            throw new IndexOutOfBoundsException("树已满");
        }else {
            nodes[nodeNums++] = node;
        }
    }

    private int getNodePos(Node node){
        for (int i=0;i<nodeNums;i++){
            if (nodes[i] == node){
                return i;
            }
        }
        return -1;
    }

    public boolean empty(){
        return nodeNums == 0;
    }

    public Node getRootNode(){
        return nodes[0];
    }

    public Node getNodeParent(Node node){
        for (int i=0;i<nodeNums;i++){
            if (i == node.parent){
                return nodes[i];
            }
        }
        return null;
    }

    public List<Node> getNodeChild(Node node){
        int nodePosition = getNodePos(node);
        List<Node> nodeList = new ArrayList<>();
        for (int i=0;i<nodeNums;i++){
            if (nodePosition == nodes[i].parent){
                nodeList.add(nodes[i]);
            }
        }
        return nodeList;
    }

    public int deep(){
        //一直找父节点
        int deep = 0;
        for (int i=0;i<nodeNums;i++){
            int currentDeep = 1;
            Node node = nodes[i];
            while (node.parent!=-1){
                node = nodes[node.parent];
                currentDeep++;
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

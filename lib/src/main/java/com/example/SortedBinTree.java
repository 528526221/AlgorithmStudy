package com.example;

import java.util.*;
import java.util.List;

/**
 * Date：2018/8/7
 * Desc：排序二叉树，按中序遍历就可以得到由小到大的有序序列
 * Created by xuliangchun.
 */

public class SortedBinTree<T extends Comparable> {
    static class Node<E>{
        E data;
        Node parent;
        Node left;
        Node right;

        public Node(E data, Node parent, Node left, Node right) {

            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?> node = (Node<?>) o;

            if (data != null ? !data.equals(node.data) : node.data != null) return false;
            if (parent != null ? !parent.equals(node.parent) : node.parent != null) return false;
            if (left != null ? !left.equals(node.left) : node.left != null) return false;
            return right != null ? right.equals(node.right) : node.right == null;

        }

        @Override
        public int hashCode() {
            int result = data != null ? data.hashCode() : 0;
            result = 31 * result + (parent != null ? parent.hashCode() : 0);
            result = 31 * result + (left != null ? left.hashCode() : 0);
            result = 31 * result + (right != null ? right.hashCode() : 0);
            return result;
        }
    }

    private Node root;

    public SortedBinTree(T data) {
        root = new Node(data,null,null,null);
    }

    /**
     * 添加节点
     * 从根节点开始，遇到键值较大则向左，遇到键值较小则向右，直到尾端即插入点
     * @param data
     */
    public void add(T data){

        Node current = root;

        while (current != null){
            if (data.compareTo(current.data)>0){
                if (current.right != null){
                    current = current.right;
                }else {
                    current.right = new Node(data,current,null,null);
                    current = null;
                }
            }else {
                if (current.left != null){
                    current = current.left;
                }else {
                    current.left = new Node(data,current,null,null);
                    current = null;
                }
            }
        }

    }

    /**
     * 移除节点   对于二叉排序树中的节点A，删除分为3种情况：
     * 1、A无子节点 ： 从A的parent中移除A即可
     * 2、A有一个子节点 ： A的子节点直接连接到A的parent
     * 3、A有2个字子节点 ： 就以A节点右子树内的最小节点取代A
     * @param data
     */
    public void remove(T data){
        Node target = getNode(data);
        if (target.left == null && target.right == null){
            //叶子节点
            if (target == root){
                root = null;
            }else {
                if (target == target.parent.left){
                    target.parent.left = null;
                }else {
                    target.parent.right = null;
                }
            }
            target.parent = null;

        }else if (target.left == null){
            //左子节点为null,右子节点不为空
            if (target == root){
                root = target.right;
            }else {
                if(target == target.parent.left){
                    target.parent.left = target.right;
                }else {
                    target.parent.right = target.right;
                }
                target.right.parent = target.parent;
            }

        }else if (target.right == null){
            if (target == root){
                root = target.left;
            }else {
                if (target == target.parent.left){
                    target.parent.left = target.left;
                }else {
                    target.parent.right = target.left;
                }
            }
            target.left.parent = target.parent;

        }else {
            Node node = target.right;
            while (node.left != null){
                node = node.left;
            }
            if (target == target.parent.left){
                node.left = target.right;

                target.parent.left = node;
            }else {
                node.left = target.left;
                target.parent.right = node;

            }
            node.parent = target.parent;
        }
    }

    /**
     * 根据节点的值得到节点
     * @param data
     * @return
     */
    private Node getNode(T data) {
        Node current = root;
        while (current!=null){
            if (data.compareTo(current.data)>0){
                current = current.right;
            }else if (data.compareTo(current.data)<0){
                current = current.left;
            }else {
                return current;
            }
        }

        return null;
    }

    /**
     * 广度优先遍历，利用队列来实现
     * @return
     */
    public java.util.List<Node> breadthFirst(){
        Queue<Node> queue = new ArrayDeque<>();
        List<Node> nodes = new ArrayList<>();
        if (root!=null){
            queue.offer(root);
        }
        while (!queue.isEmpty()){
            nodes.add(queue.peek());
            Node node = queue.poll();
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
        return nodes;
    }
}

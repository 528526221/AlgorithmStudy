package com.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Date：2018/8/6
 * Desc：哈夫曼树 最优二叉树，带权路径最短二叉树
 * Created by xuliangchun.
 */

public class HuffmanTree {
    public static class Node<E>{
        private E data;
        private double weight;
        private Node leftChild;
        private Node rightChild;

        public Node(E data, double weight) {
            this.data = data;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", weight=" + weight +
                    '}';
        }
    }

    /**
     * 构造哈夫曼树
     * @param nodes 节点集合
     * @return 构造出来的哈夫曼树的根节点
     */
    private static Node createTree(java.util.List<Node> nodes){
        while (nodes.size() > 1){
            quickNodes(nodes);
            //以权值最小的树作为左右子树构造出一棵新的二叉树，直到最后只剩下一棵树
            //获取权值最小的两个节点
            Node left = nodes.get(nodes.size()-1);
            Node right = nodes.get(nodes.size()-2);
            //生成新节点，新节点的权值为两个子节点的权值之和
            Node parent = new Node(null,left.weight+right.weight);
            parent.leftChild = left;
            parent.rightChild = right;
            //删除权值最小的两个节点
            nodes.remove(nodes.size()-1);
            nodes.remove(nodes.size()-1);
            //新生成的节点添加到集合中
            nodes.add(parent);
        }
        //
        return nodes.get(0);
    }

    /**
     * 广度优先遍历（按层遍历）
     * @param root
     * @return
     */
    public static List<Node> breadthFirst(Node root){
        List<Node> list = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();
        if (root != null){
            queue.offer(root);
        }
        while (!queue.isEmpty()){
            list.add(queue.peek());
            Node node = queue.poll();
            if (node.leftChild != null){
                queue.offer(node.leftChild);
            }
            if (node.rightChild != null){
                queue.offer(node.rightChild);
            }
        }
        return list;
    }


    /**
     * 快速排序 暂未实现
     * @param nodes
     */
    private static void quickNodes(List<Node> nodes) {

    }
}

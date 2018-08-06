package com.example;

import java.util.*;
import java.util.List;

/**
 * Date：2018/8/1
 * Desc：二叉树三叉链表结构
 * Created by xuliangchun.
 */

public class ThreeLinkBinTree<T> {
    public static class TreeNode<E>{
        private E data;
        private TreeNode parent;
        private TreeNode left;
        private TreeNode right;


        public TreeNode(E data, TreeNode parent, TreeNode left, TreeNode right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;

        }

        public TreeNode(E data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "data=" + data +
//                    ", parent=" + parent +
//                    ", left=" + left +
//                    ", right=" + right +
                    '}';
        }
    }

    private TreeNode root;

    public ThreeLinkBinTree(T data) {
        root = new TreeNode(data,null,null,null);
    }

    /**
     * 为指定节点添加子节点
     * @param parent
     * @param data
     * @param isLeft
     * @return
     */
    public TreeNode addNode(TreeNode parent,T data,boolean isLeft){
        if (parent == null){
            throw new RuntimeException("空节点下无法添加子节点");
        }
        if (isLeft && parent.left != null){
            throw new RuntimeException("节点已有左子节点");
        }
        if (!isLeft && parent.right != null){
            throw new RuntimeException("节点已有右子节点");
        }

        TreeNode node = new TreeNode(data);
        if (isLeft){
            parent.left = node;
        }else {
            parent.right = node;
        }
        node.parent = parent;
        return node;
    }

    /**
     * 树是否为空
     * @return
     */
    public boolean empty(){
        return root==null || root.data == null;
    }

    /**
     * 根节点
     * @return
     */
    public TreeNode getRoot(){
        if (empty()){
            throw new RuntimeException("树为空，无法访问根节点");
        }
        return root;
    }

    /**
     * 父节点
     * @param node
     * @return
     */
    public TreeNode getParent(TreeNode node){
        if (node == null){
            throw new RuntimeException("节点为null");
        }
        return node.parent;
    }

    /**
     * 左子节点
     * @param node
     * @return
     */
    public TreeNode leftChild(TreeNode node){
        if (node == null){
            throw new RuntimeException("节点为null");
        }
        return node.left;
    }

    /**
     * 右子节点
     * @param node
     * @return
     */
    public TreeNode rightChild(TreeNode node){
        if (node == null){
            throw new RuntimeException("节点为null");
        }
        return node.right;
    }

    /**
     * 树的深度
     * @return
     */
    public int deep(){
        return deep(root);
    }

    /**
     * 递归方法 每棵树的深度为其所有子树的最大深度+1
     * @param node
     * @return
     */
    private int deep(TreeNode node){
//        int deep1 = getDeepForNode(node);
//        int deep2 = getDeepForNode(node.left);
//        int deep3 = getDeepForNode(node.right);
//        return Math.max(Math.max(deep1,deep2),deep3);
        if (node == null){
            return 0;
        }
        if (node.left == null && node.right == null){
            return 1;
        }
        int leftDeep = deep(node.left);
        int rightDeep = deep(node.right);
        return Math.max(leftDeep,rightDeep)+1;
    }


    private int getDeepForNode(TreeNode node){
        if (node == null){
            return 0;
        }
        int deep = 1;
        while (node.parent!=null){
            node = node.parent;
            deep++;
        }
        return deep;
    }


    /**
     * 深度优先遍历中，是以根节点的遍历时机来区分的，先处理根节点就是先序遍历，其次处理根节点就是中序遍历，最后处理根节点就是后序遍历
     */

    /**
     * 先序遍历 DLR
     * @return
     */
    public java.util.List<TreeNode> preIterator(TreeNode node){
        List<TreeNode> list = new ArrayList<>();
        list.add(node);
        if (node.left != null){
            list.addAll(preIterator(node.left));
        }
        if (node.right != null) {
            list.addAll(preIterator(node.right));
        }
        return list;
    }

    /**
     * 中序遍历 LDR  主要在于处理这个节点的顺序
     * @param node
     * @return
     */
    public List<TreeNode> inIterator(TreeNode node){
        List<TreeNode> list = new ArrayList<>();
        if (node.left != null){
            list.addAll(preIterator(node.left));
        }
        list.add(node);
        if (node.right != null) {
            list.addAll(preIterator(node.right));
        }
        return list;
    }

    /**
     * 后序遍历 LRD
     * @param node
     * @return
     */
    public List<TreeNode> postIterator(TreeNode node){
        List<TreeNode> list = new ArrayList<>();

        if (node.right != null) {
            list.addAll(preIterator(node.right));
        }

        if (node.left != null){
            list.addAll(preIterator(node.left));
        }
        list.add(node);

        return list;
    }

    /**
     * 利用队列的FIFO特性来实现广度优先遍历（按层遍历）
     * 返回特殊值的版本和抛出异常的版本 添加  删除  访问front元素
     * offer和add
     * poll和remove
     * peek和element
     * @return
     */
    public List<TreeNode> breadthFirst(){
        Queue<TreeNode> queue = new ArrayDeque<>();
        List<TreeNode> list = new ArrayList<>();
        if (root != null){
            queue.offer(root);
        }
        while (!queue.isEmpty()){
            //将队列front端元素加入到list
            list.add(queue.peek());
            //将队列front端元素从队列中移除
            TreeNode treeNode = queue.poll();
            //左右子节点不为空，则加入队列
            if (treeNode.left != null){
                queue.offer(treeNode.left);
            }
            if (treeNode.right != null){
                queue.offer(treeNode.right);
            }
        }
        return list;
    }






}

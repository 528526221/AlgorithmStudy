package com.example;

/**
 * Date：2018/7/31
 * Desc：二叉树，二叉链表存储。线性结构中，链式存储我们需要定义header头节点和tail尾节点，但是在树这样的非线性结构中，我们只需要定义root根节点
 * Created by xuliangchun.
 */

public class TwoLinkBinTree<T> {
    public static class TreeNode<E>{
        private E data;
        private TreeNode<E> left;
        private TreeNode<E> right;

        public TreeNode(E data, TreeNode<E> left, TreeNode<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "data=" + data +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
    private TreeNode<T> root;//根节点


    public TwoLinkBinTree(T data) {
        root = new TreeNode<>(data,null,null);
    }

    /**
     * 指定节点添加子节点
     * @param parent
     * @param data
     * @param isLeft
     */
    public TreeNode addNode(TreeNode parent,T data,boolean isLeft){
        if (parent == null){
            throw new RuntimeException("节点为null，无法添加子节点");
        }
        if (isLeft && parent.left != null){
            throw new RuntimeException("当前节点已存在左子节点");
        }
        if (!isLeft && parent.right != null){
            throw new RuntimeException("当前节点已存在右子节点");
        }
        TreeNode node = new TreeNode<T>(data,null,null);
        if (isLeft){
            parent.left = node;
        }else {
            parent.right = node;
        }
        return node;
    }

    public boolean empty(){
        return root == null || root.data == null;
    }

    public TreeNode<T> getRoot(){
        return root;
    }


    /**
     * 返回指定节点的父节点
     * @param node
     * @return
     */
    public TreeNode<T> getParent(TreeNode node){
//        resultNode = null;
//        ergodicNode(root,node);
        return findParent(root,node);
    }


    private TreeNode findParent(TreeNode current,TreeNode node){
        if (current.left != null){
            if (current.left.equals(node)){
                return current;
            }else {
                TreeNode node1 = findParent(current.left,node);
                if (node1!=null){
                    return node1;
                }
            }
        }
        if (current.right != null){
            if (current.right.equals(node)){
                return current;
            }else {
                TreeNode node1 = findParent(current.right,node);
                if (node1!=null){
                    return node1;
                }
            }
        }
        return null;

    }

    private TreeNode<T> resultNode;

    private void ergodicNode(TreeNode<T> current, TreeNode node) {
        if (current.left != null){
            if (current.left.equals(node)){
                resultNode = current;
            }else {
                ergodicNode(current.left,node);
            }
        }
        if (current.right != null){
            if (current.right.equals(node)){
                resultNode = current;
            }else {
                ergodicNode(current.right,node);
            }
        }
    }

    /**
     * 左子节点
     * @param parent
     * @return
     */
    public TreeNode<T> left(TreeNode<T> parent){
        if (parent.left == null){
            throw new RuntimeException("左子节点为null");
        }
        return parent.left;
    }
    /**
     * 右子节点
     * @param parent
     * @return
     */
    public TreeNode<T> right(TreeNode<T> parent){
        if (parent.right == null){
            throw new RuntimeException("右子节点为null");
        }
        return parent.right;
    }

    /**
     * 计算树的深度
     * @return
     */
    public int deep(){
        return deep(root);
    }


    private int deep(TreeNode node){
        if (node == null){
            return 0;
        }
        if (node.left == null && node.right == null){
            return 1;
        }else {
            //记录左右子树的最大深度
            int leftDeep = deep(node.left);
            int rightDeep = deep(node.right);
            //最大深度+1
            return Math.max(leftDeep,rightDeep)+1;
        }


    }



}

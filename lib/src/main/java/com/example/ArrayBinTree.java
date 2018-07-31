package com.example;

/**
 * Date：2018/7/30
 * Desc：二叉树 顺序结构存储实现，充分利用满二叉树的特性，如果空出来的节点，那么对应的数组元素留空
 * Created by xuliangchun.
 */

public class ArrayBinTree<T> {
    private Object[] datas;//数组来保存二叉树节点
    private int arraySize;//数组大小
    private int deep;

    public ArrayBinTree() {
        deep = 4;
        arraySize = (int) (Math.pow(2,deep)-1);// 1 3 7 15 --加1后--  2 4 8 16
        datas = new Object[arraySize];
    }

    /**
     * 为指定节点添加子节点
     * @param data 新子节点的数据
     * @param index 需要添加新子节点的父节点的索引
     * @param left 是否是左节点
     */
    public void add(T data,int index,boolean left){
        if (datas[index] == null){
            throw new RuntimeException(index+"处的节点为空，无法添加子节点");
        }
        if (index*2+1 > arraySize-1){
            throw new IndexOutOfBoundsException("树底层的数组已满，树越界异常");
        }

        //注意index从0开始，分析问题的编号从1开始
        //对编号为i的节点而言，当2i<=n时，有左孩子，左孩子的编号是2
        //(index+1)*2-1 = index*2+1
        if (left){
            datas[index*2+1] = data;
        }else {
            datas[index*2+2] = data;
        }
    }

    /**
     * 二叉树是否为空
     * @return
     */
    public boolean empty(){
        return datas[0] == null;
    }

    /**
     * 返回根节点
     * @return
     */
    public T getRoot(){
        return (T) datas[0];
    }

    /**
     * 获取父节点
     * @param index
     * @return
     */
    public T getParent(int index){
        return (T) datas[(index-1)/2];
    }

    /**
     * 节点的左子节点
     * @param index
     * @return
     */
    public T left(int index){
        if (index*2+1 > arraySize-1){
            throw new RuntimeException("该节点为叶子节点，无子节点");
        }
        return (T) datas[index*2+1];
    }

    /**
     * 节点的右子节点
     * @param index
     * @return
     */
    public T right(int index){
        if (index*2+1 > arraySize-1){
            throw new RuntimeException("该节点为叶子节点，无子节点");
        }
        return (T) datas[index*2+2];
    }

    /**
     * 返回指定节点的位置
     * @param data
     * @return
     */
    public int pos(T data){
        for (int i=0;i<arraySize;i++){
            if (datas[i].equals(data)){
                return i;
            }
        }
        return -1;
    }


}

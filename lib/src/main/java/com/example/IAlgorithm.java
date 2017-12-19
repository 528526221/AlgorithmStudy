package com.example;

/**
 * Date：2017/11/23
 * Desc：算法接口
 * Created by xuliangchun.
 */

public interface IAlgorithm<T extends Comparable> {
    void onSort(T[] arr);//排序算法需要实现该方法
}

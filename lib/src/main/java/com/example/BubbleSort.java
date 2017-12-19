package com.example;

/**
 * Date：2017/11/24
 * Desc：冒泡排序（O(N^2)）  最大的数一直往后靠
 * Created by xuliangchun.
 */

public class BubbleSort implements IAlgorithm {
    @Override
    public void onSort(Comparable[] arr) {
        //冒泡排序是两两依次比较，并作交换，交换的次数多
        //而选择排序是选定一个位置，循环与选定位置的值比较找出最值，最后将值调整到合适位置，交换的次数少
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr.length-1-i;j++){
                if (arr[j].compareTo(arr[j+1])>0){
                    Comparable temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
//        SortTestHelper.printResult(arr);

    }
}

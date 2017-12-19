package com.example;

/**
 * Date：2017/11/24
 * Desc：插入排序（O(N^2)）
 * Created by xuliangchun.
 */

public class InsertionSort implements IAlgorithm {
    @Override
    public void onSort(Comparable[] arr) {
        //基本思路
        //1.外循环是我们挨个要插入的数
        //2.内循环 拿着我们要插入的数和有序数组进行一次逆向检查 发现后面的比前面小就调换一次反之则break
//        SortTestHelper.printResult(arr);

        for (int i = 1; i < arr.length; i++) {
            //寻找元素arr[i]合适的插入位置
//            for (int j=i;j>0;j--){
//                if (arr[j].compareTo(arr[j-1])<0){
//                    SortTestHelper.swap(arr,j,j-1);
//                    count++;
//                }else {
//                    break;
//                }
//            }


            //优化后的算法
            Comparable temp = arr[i];
            for (int j=i-1;j>=0;j--){
                if (temp.compareTo(arr[j])<0){
                    arr[j+1] = arr[j];
                    if (j==0)
                        arr[j] = temp;
                }else {
                    arr[j+1] = temp;
                    break;
                }
            }

            //优化后的算法老师写的代码

//            Comparable temp = arr[i];
//            int j;// j保存temp应该要插入的位置
//            for (j=i;j>0 && arr[j-1].compareTo(temp)>0;j--){
//                arr[j] = arr[j-1];
//            }
//            arr[j] = temp;

        }
//        SortTestHelper.printResult(arr);
    }
}

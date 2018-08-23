package com.example;

/**
 * Date：2018/8/16
 * Desc：
 * Created by xuliangchun.
 */

public class SortUtil<T extends Comparable> {
    public void selectSort(T[] arr){
        for (int i=0;i<arr.length-1;i++){
            int minIndex = i;
            for (int j = i+1;j<arr.length;j++){
                if (arr[minIndex].compareTo(arr[j])>0){
                    minIndex = j;
                }
            }
            if (minIndex != i){

            }

        }
    }
}

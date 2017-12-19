package com.example;


/**
 * Date：2017/11/23
 * Desc：选择排序（O(N^2)）
 * Created by xuliangchun.
 */

public class SelectionSort implements IAlgorithm {
    @Override
    public void onSort(Comparable[] arr) {
        //基本思路
        //1.外循环：循环每个位置（其实就是选择了这个位置，然后用内循环去选择一个合适的数，放到这个位置）；
        //2.内循环：在无序元素中选择一个合适的数；
        //3.把第2步选中的数据放到第一步选中的位置上就可以了；


        // 循环每个位置，为该位置选择合适数据

        for (int i=0;i<arr.length;i++){
            int minIndex = i ;//选择了这个位置

            for (int j=i+1;j<arr.length;j++){
                if(arr[j].compareTo(arr[minIndex])<0) {
                    minIndex = j;// 选择合适数据
                }
            }
            SortTestHelper.swap(arr,minIndex,i);// 把选择好的数据放到外循环中选中的位置中
        }

//        SortTestHelper.printResult(arr);
    }

    public void testLog(String s){
        System.out.print(s);
    }


}

package com.example;

import static com.example.SortTestHelper.printResult;
import static com.example.SortTestHelper.swap;

/**
 * Date：2018/8/28
 * Desc：快速排序
 * Created by xulc.
 */

public class KuaiSort {
    public static void main(String[] args){
        Integer[] data = new Integer[]{5,2,7,-2,9,0,-9};
        quickSort(data);
        printResult(data);
    }

    private static void quickSort(Integer[] data) {
        subSort(data,0,data.length-1);
    }

    private static void subSort(Integer[] data, int start, int end) {

        if (start < end){
            //以start的位置元素作为基准值
            int base = start;
            //指示两个哨兵i和j,哨兵i从左找出来大于基准值的位置，哨兵j从右找出小于基准值的位置
            int i = start;
            int j = end + 1;

            while (true){
                while (i < end && data[++i] <= data[base]);
                while (j > start && data[--j] >= data[base]);
                if (i < j){
                    //交换i和j的元素，继续让哨兵移动寻找
                    swap(data,i,j);
                }else {
                    break;
                }
            }
            //交换base 和 j的元素，此时j作为分隔，左边全是小于基准值，右边全是大于基准值
            swap(data,base,j);
            subSort(data,start,j-1);
            subSort(data,j+1,end);
        }

    }


}

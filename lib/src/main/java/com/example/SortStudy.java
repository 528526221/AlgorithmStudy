package com.example;

import static com.example.SortTestHelper.printResult;
import static com.example.SortTestHelper.swap;

/**
 * Date：2018/8/29
 * Desc：
 * Created by xulc.
 */

public class SortStudy {
    public static void main(String[] args) {
        Integer[] data = new Integer[]{9, -16, 21, 23, -30, -49, 21, 30, 30};
        halfInsertSort(data);
        SortTestHelper.printResult(data);
    }

    /**
     * 1.选择排序：直接选择排序和堆排序
     */
    //直接选择排序算法的关键就是n-1趟比较，每趟比较的目的就是选择出本趟比较中最小的数据，并将该数据放在本趟中的第1位
    private static void directSelectSort(Integer[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[minIndex]) {
                    minIndex = j;//找出最小的数据index
                }
            }
            if (i != minIndex) {
                swap(data, i, minIndex);
            }
        }
    }

    //堆排序 建大顶堆，拿堆的根节点和最后一个节点交换
    private static void heapSort(Integer[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            buildMaxHeap(data, data.length - 1 - i);
            swap(data, 0, data.length - 1 - i);//把大顶堆堆顶和最后一个节点交换
        }
    }

    //建立大顶堆 从最后一个非叶子节点开始，比较该节点和两个子节点的值，如果某个子节点的值大于父节点，把父节点和较大的子节点进行交换，向前逐步遍历到根节点
    private static void buildMaxHeap(Integer[] data, int lastIndex) {
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            int biggerIndex = i;//参照选择排序的思想，减少交换的次数，先找出来最大的是谁，最后再交换
            if (data[i] < data[2 * i + 1]) {
//                swap(data,i,2*i+1);
                biggerIndex = 2 * i + 1;
            }
            //判断i的右子节点是否存在
            if ((2 * i + 2) <= lastIndex) {
                if (data[i] < data[2 * i + 2]) {
//                    swap(data,i,2*i+2);
                    biggerIndex = 2 * i + 2;
                }
            }
            if (biggerIndex != i) {
                swap(data, biggerIndex, i);
            }
        }
    }

    /**
     * 2.交换排序：冒泡排序和快速排序
     */
    //冒泡排序 两两比较，把最大的丢到最后面去
    private static void bubbleSort(Integer[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            boolean swapFlag = false;
            for (int j = 0; j < data.length - 1 - i; j++) {
                if (data[j] > data[j + 1]) {
                    swap(data, j, j + 1);
                    swapFlag = true;
                }
            }
            if (!swapFlag) {
                break;
            }
        }
    }

    //快速排序 从待排的数据序列中任取一个数据作为分界值，所有比它小的数放在左边，比它大的数放在右边，经过这样一趟下来，该序列形成左右两个序列
    //左边序列元素都比分界值小，右边序列元素都比分界值大，接下来继续递归两个子序列
    private static void quickSort(Integer[] data) {
        subQuickSort(data, 0, data.length - 1);
    }

    private static void subQuickSort(Integer[] data, int start, int end) {
        if (start < end) {
            //以start的位置元素作为基准值
            int base = start;
            //指示两个哨兵i和j,哨兵i从左找出来大于基准值的位置，哨兵j从右找出小于基准值的位置
            int i = start;
            int j = end + 1;

            while (true) {
                while (i < end && data[++i] <= data[base]) ;
                while (j > start && data[--j] >= data[base]) ;
                if (i < j) {
                    //交换i和j的元素，继续让哨兵移动寻找
                    swap(data, i, j);
                } else {
                    break;
                }
            }
            //交换base 和 j的元素，此时j作为分隔，左边全是小于基准值，右边全是大于基准值
            swap(data, base, j);
            subQuickSort(data, start, j - 1);
            subQuickSort(data, j + 1, end);
        }

    }

    /**
     * 插入排序：直接插入排序
     *
     * @param data
     */

    //直接插入排序：依次将待排序的数据元素按其值的大小插入前面的有序序列
    private static void directInsertSort(Integer[] data) {
        for (int i = 1; i < data.length; i++) {
            int temp = data[i];
            if (data[i] < data[i - 1]) {
                int j = i - 1;//从i-1开始往后比对，谁大谁往后挪一步
                for (; j >= 0 && temp < data[j]; j--) {
                    data[j + 1] = data[j];//大就往后挪
                }
                data[j + 1] = temp;

                //选出j的位置，再进行挪位
//                while (j>=0){
//                    if (temp>data[j]){
//                        break;
//                    }else {
//                        j--;
//                    }
//                }
//                for (int k=i-1;k>=j+1;k--){
//                    data[k+1] = data[k];
//                }
//                data[j+1] = temp;
            }

        }
    }

    //折半插入排序，对直接插入排序进行改进，要在一个有序序列找到合适的位置可以找到中点
    private static void halfInsertSort(Integer[] data) {
        printResult(data);

        for (int i=1;i<data.length;i++){
            int temp = data[i];
            int start = 0;
            int end = i-1;
            int half ;
            while (true){
                if (start+1>=end){
                    //不能折半了之后，考虑比start小，比end大，在start和end之间
                    if (temp<data[start]){
                        //start往后挪
                        for (int k=i-1;k>=start;k--){
                            data[k+1] = data[k];
                        }
                        data[start] = temp;
                    }else if (temp<data[end]){
                        //end往后挪
                        for (int k=i-1;k>=end;k--){
                            data[k+1] = data[k];
                        }
                        data[end] = temp;
                    }

                    break;
                }else {
                    half = (start+end)/2;
                    if (temp>data[half]){
                        start = half+1;
                    }else {
                        end = half-1;
                    }
                }
            }
            printResult(data);

        }
    }




}

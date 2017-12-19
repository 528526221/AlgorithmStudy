package com.example;

import java.util.Random;

/**
 * Date：2017/11/28
 * Desc：快速排序(O(N*logN))
 * Created by xuliangchun.
 */

public class QuickSort implements IAlgorithm {
    @Override
    public void onSort(Comparable[] arr) {
//        quickSortRecursion(arr, 0, arr.length - 1);
//        quickSortRecursionDouble(arr, 0, arr.length - 1);
        quickSortRecursionThree(arr, 0, arr.length - 1);

        SortTestHelper.printResult(arr);

    }



    private void quickSortRecursion(Comparable[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(arr, left, right);
        quickSortRecursion(arr, left, p - 1);
        quickSortRecursion(arr, p + 1, right);
    }


    /**
     * 双路快速排序法
     * @param arr
     * @param left
     * @param right
     */
    private void quickSortRecursionDouble(Comparable[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partitionDouble(arr, left, right);
        quickSortRecursionDouble(arr, left, p - 1);
        quickSortRecursionDouble(arr, p + 1, right);
    }

    private int partitionDouble(Comparable[] arr, int left, int right) {
        Comparable v = arr[left];
//        int j = right;
//        for (int i=left+1;i<=right;i++){
//            if (arr[i].compareTo(v)<=0){
//
//            }else {
//                Comparable temp = arr[j];
//                arr[j] = arr[i];
//                arr[i] = temp;
//                j--;
//                i--;
//            }
//
//            if (i>=j){
//                break;
//            }
//            if (arr[j].compareTo(v)>=0){
//                j--;
//                i--;
//            }else {
//                Comparable temp = arr[j];
//                arr[j] = arr[i];
//                arr[i] = temp;
//            }
//            if (i>=j){
//                break;
//            }
//        }

        //老师的方法  比起我们自己写的要快速很多 我们自己写的效率很低 没有真正达到双路排序
        int i = left+1;
        int j = right;
        while (true){
            while (i<=right && arr[i].compareTo(v)<0){
                i++;
            }
            while (j >= left+1 && arr[j].compareTo(v)>0){
                j--;
            }
            if (i>j){
                break;
            }
            //以上条件不满足  就意味着刚好二者可以交换一次  这次其实很大的减少了交换的次数
            Comparable temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            //交换完成后 i右移一位j左移一位
            i++;
            j--;

        }


        Comparable temp = arr[j];
        arr[j] = v;
        arr[left] = temp;

        return j;
    }

    /**
     * 三路快速排序
     * @param arr
     * @param left
     * @param right
     */
    private void quickSortRecursionThree(Comparable[] arr, int left, int right) {
        if (left>=right){
            return;
        }
        Comparable v = arr[left];
        int lt = left;
        int gt = right+1;
        int i = left+1;
        while (i<gt){
            if (arr[i].compareTo(v)<0){
                swap(arr,i,lt+1);
                lt++;
                i++;
            }else if (arr[i].compareTo(v)>0){
                swap(arr,i,gt-1);
                gt--;
            }else {
                i++;
            }
        }
        swap(arr,lt,left);

        quickSortRecursionThree(arr,left,lt-1);
        quickSortRecursionThree(arr,gt,right);
    }


    /**
     * 返回p，使得arr[left...p-1]<arr[p];arr[p+1...right]>arr[p]
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private int partition(Comparable[] arr, int left, int right) {
        //以arr[left]作为排序的标准
        //这里做一次优化  取随机数作为排序的标准  避免一个本身近乎有序的数组使得拆分不对称 ，时间复杂度无限退化成O(N^2)
        Random random = new Random();
        int randomPosition = random.nextInt(right - left + 1) + left;
        Comparable randomTemp = arr[left];
        arr[left] = arr[randomPosition];
        arr[randomPosition] = randomTemp;

        Comparable v = arr[left];
        int j = left;//分界点
        //i是我们在考察的元素,考察的元素如果大于标准值那算了，如果小于标准值的话，那么将j+1与考察的元素位置互换，再将分界点右移一位
        for (int i = left + 1; i <= right; i++) {
            if (arr[i].compareTo(v) < 0) {
                Comparable t = arr[i];
                arr[i] = arr[j + 1];
                arr[j + 1] = t;
                //left部分相当于多了一个元素，所以分界点要右移一位
                j++;
            }
        }
        //此时数组呈现3区域：标准值_____小于标准值_____大于标准值
        Comparable temp = arr[j];
        arr[j] = v;
        arr[left] = temp;

        return j;
    }

    private void swap(Comparable[] arr,int a,int b){
        Comparable temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}

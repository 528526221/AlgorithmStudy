package com.example;

/**
 * Date：2017/11/28
 * Desc：自底向上的归并排序（O(N*logN)）  无法理解....
 * Created by xuliangchun.
 */

public class MergeSortBU implements IAlgorithm {
    @Override
    public void onSort(Comparable[] arr) {
        int n = arr.length;
        for (int sz = 1;sz <= n;sz += sz){
            for (int i=0;i+sz < n; i+=sz+sz){
                mergeOrderedArray(arr,i,i+sz-1,Math.min(i+sz+sz-1,n-1));
            }
        }
        SortTestHelper.printResult(arr);

    }

    /**
     * 归并数组
     * @param arr
     * @param left
     * @param middle
     * @param right
     */
    private void mergeOrderedArray(Comparable[] arr, int left, int middle, int right) {
        Comparable[] arrTemp = new Comparable[right - left + 1];
        for (int i = left; i <= right; i++) {
            arrTemp[i - left] = arr[i];
        }
        int a1 = left;
        int a2 = middle + 1;
        for (int k = left; k <= right; k++) {

            if (a1>middle){
                arr[k] = arrTemp[a2-left];
                a2++;
            }else if (a2>right){
                arr[k] = arrTemp[a1-left];
                a1++;
            }else if (arrTemp[a1 - left].compareTo(arrTemp[a2 - left]) < 0) {
                arr[k] = arrTemp[a1 - left];
                a1++;
            } else {
                arr[k] = arrTemp[a2 - left];
                a2++;
            }
        }
    }
}

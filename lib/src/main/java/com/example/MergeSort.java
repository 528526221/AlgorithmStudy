package com.example;

/**
 * Date：2017/11/24
 * Desc：归并排序（O(N*logN)）
 * Created by xuliangchun.
 */

public class MergeSort implements IAlgorithm {
    private int cc;//求逆数对
    @Override
    public void onSort(Comparable[] arr) {
        //对于N*logN时间复杂度的底数究竟是多少的问题？
        //在N趋于无穷时，假设两个分别以X、Y为底的算法复杂度 logX(N)/logY(N)的极限是等于lnY/lnX,也就是一个常数
        //也就是说在N趋于无穷大时，这两个东西仅差一个常数
        //所以从研究算法的角度log的底数并不重要

        //自己先利用选择排序将一个数组分成两个排好序的数组，再进行归并排序：）
//        Comparable[] arr1 = new Comparable[arr.length/2];
//        Comparable[] arr2 = new Comparable[arr.length-arr.length/2];
//        for (int i=0;i<arr.length;i++){
//            if (i<arr.length/2){
//                arr1[i] = arr[i];
//            }else {
//                arr2[i-arr1.length] = arr[i];
//            }
//        }
//
//        SortTestHelper.testSort(SelectionSort.class.getName(),arr1);
//        SortTestHelper.testSort(SelectionSort.class.getName(),arr2);
//
//        SortTestHelper.printResult(arr1);
//        SortTestHelper.printResult(arr2);
//
//        Comparable[] arrResult = new Comparable[arr.length];
//        int a1 =0,a2=0;//当前正在比较的两个数组中的元素的位置 二者之和就是比较完成之后要放到结果数组中的位置
//
//
//
//        for (int i=0;i<arrResult.length;i++){
//            if (a1>=arr1.length){
//                arrResult[i] = arr2[a2];
//                a2++;
//            }else if (a2>=arr2.length){
//                arrResult[i] = arr1[a1];
//                a1++;
//            }else {
//                if (arr1[a1].compareTo(arr2[a2])>0){
//                    arrResult[i] = arr2[a2];
//                    a2++;
//                }else {
//                    arrResult[i] = arr1[a1];
//                    a1++;
//                }
//            }
//
//        }


//        //哪个数组中的元素放好了正确位置，那么那个数组正在比较的位置就往后挪一位，直到无法挪了为止
//        while (a1<arr1.length&&a2<arr2.length){
//            if (arr1[a1].compareTo(arr2[a2])<0){
//                arrResult[a1+a2] = arr1[a1];
//                a1++;
//            }else {
//                arrResult[a1+a2] = arr2[a2];
//                a2++;
//            }
//        }
//        //当然无法挪了只是代表接下来的数据不需要通过归并放到合适位置了，而是可以把多出来的数据直接循环放到结果数组中
//        if (a1>=arr1.length){
//            while (a2<arr2.length){
//                arrResult[a1+a2] = arr2[a2];
//                a2++;
//
//            }
//        }else if (a2>=arr2.length){
//            while (a1<arr1.length){
//                arrResult[a1+a2] = arr1[a1];
//                a1++;
//            }
//        }
//        SortTestHelper.printResult(arrResult);


        //运用递归的思路来实现归并，而不是通过手动拆分数组
        //数组存在左边界和右边界
        SortTestHelper.printResult(arr);

        mergeRecursion(arr, 0, arr.length - 1);
        SortTestHelper.printResult(arr);

        System.out.print(cc);
    }

    /**
     * 递归归并
     * @param arr
     * @param left
     * @param right
     */
    private void mergeRecursion(Comparable[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = (left + right) / 2;

        mergeRecursion(arr, left, middle);
        mergeRecursion(arr, middle + 1, right);
//        System.out.print("此时left=" + left + "&right=" + right + "&middle=" + middle + "\n ");
        //递归有个重要的特性  最初的反而最后
        //递归的最后 肯定是大家都是有序的 单值数组
        //既然有序的话  那我们该合并了：）
        if (arr[middle].compareTo(arr[middle+1])>0){
            //优化：本身已经有序的话就不用合并了
            mergeOrderedArray(arr, left, middle, right);
        }


    }

    /**
     * 在一个数组中存在两部分有序的数据，middle是他们的分界线
     * 把原数组中的数据复制一份留存，然后就是挨个比对往原数组位置赋值
     * @param arr
     * @param left
     * @param middle
     * @param right
     */
    private void mergeOrderedArray(Comparable[] arr, int left, int middle, int right) {

        Comparable[] temp = new Comparable[right-left+1];
        for (int i=0;i<temp.length;i++){
            temp[i] = arr[i+left];
        }
        int a = left;
        int b = middle+1;

        for (int i=left;i<=right;i++){
            if (a>middle){
                arr[i] = temp[b-left];
                b++;
            }else if (b>right){
                arr[i] = temp[a-left];
                a++;
            }else if (temp[a-left].compareTo(temp[b-left])<0){
                arr[i] = temp[a-left];
                a++;
            }else {
                cc += middle-a+1;
                arr[i] = temp[b-left];
                b++;
            }
        }
//        Comparable[] arrTemp = new Comparable[right - left + 1];
//        for (int i = left; i <= right; i++) {
//            arrTemp[i - left] = arr[i];
//        }
//        int a1 = left;
//        int a2 = middle + 1;
//        for (int k = left; k <= right; k++) {
//
//            if (a1>middle){
//                arr[k] = arrTemp[a2-left];
//                a2++;
//            }else if (a2>right){
//                arr[k] = arrTemp[a1-left];
//                a1++;
//            }else if (arrTemp[a1 - left].compareTo(arrTemp[a2 - left]) < 0) {
//                arr[k] = arrTemp[a1 - left];
//                a1++;
//            } else {
//                arr[k] = arrTemp[a2 - left];
//                a2++;
//            }
//        }
    }
}

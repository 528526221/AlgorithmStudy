package com.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Date：2017/11/22
 * Desc：辅助工具类
 * Created by xuliangchun.
 */

public class SortTestHelper {

    /**
     * 交换数组中两个元素的位置
     * @param arr
     * @param a
     * @param b
     */
    public static <T> void swap(T[] arr,int a,int b){
        T c = arr[a];
        arr[a] = arr[b];
        arr[b] = c;
    }

    /**
     * 打印数组内容
     * @param arr
     * @param <T>
     */
    public static <T> void printResult(T[] arr){
        //打印数组中的值
        for (T anArr : arr) {
            System.out.print(anArr.toString() + "\n");
        }
        System.out.print("\n");
    }

    /**
     * 生成随机数组
     * @param n 数组长度
     * @param rangeL 左值
     * @param rangeR 右值
     * @return
     */
    public static Integer[] generateRandomArray(int n,int rangeL,int rangeR){
        if (rangeR<rangeL){
            //在抛出异常时注意，运行异常是不需要我们捕获的;在调用该方法时，上层可以手动增加捕获，但无必要
            //如果是抛出非运行时异常的话，那么在抛出时就需要捕获或者交由上层捕获
            throw new IllegalArgumentException("rangeR必须大于等于rangeL！");
        }
        Integer[] array = new Integer[n];
        Random random = new Random();
        for (int i=0;i<array.length;i++){
            //Math.random()随机选取[0,1)的伪随机数
//            array[i] = (int) (Math.random()*(rangeR-rangeL+1)+rangeL);
            //random.nextInt(int n)随机选取[0,n)的伪随机整数；如果没有参数n，则取值范围为int范围
            array[i] = random.nextInt(rangeR-rangeL+1)+rangeL;
        }
        return array;
    }

    /**
     * 测试算法的性能
     * @param sortClassName 类名
     * @param array 需要排序的数组
     */
    public static void testSort(String sortClassName,Comparable[] array){
        //依据反射执行类名中的sort方法
        try {
            Class sortClass = Class.forName(sortClassName);
            //这里需要注意一个问题  数组作为参数时，如果不作任何处理的话，那么调用getMethod方法会
            //把数组打散成为若干个单独的参数，这样是错误的。解决方法有2种：
            //1.重新构建一个object数组，那个参数数组作为唯一的元素存在
            //2.把数组看成一个object对象  试验证明这种处理运算更快
            //第1种方法
//            Method sortMethod = sortClass.getMethod("onSort",(Class<?>) Comparable[].class);
//            long start = System.currentTimeMillis();
//            sortMethod.invoke(sortClass.newInstance(),(Object) array);
//            long end = System.currentTimeMillis();
//            System.out.print("执行时长："+(end-start));
            //第2种方法
            Method sortMethod = sortClass.getMethod("onSort",new Class[]{Comparable[].class});
            long start = System.currentTimeMillis();
            sortMethod.invoke(sortClass.newInstance(),new Object[]{array});
            long end = System.currentTimeMillis();
            System.out.print(sortClassName+"执行时长："+(end-start)+"\n");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void testLog(String sortClassName){
        try {
            Class aClass = Class.forName(sortClassName);
            Method method = aClass.getMethod("testLog",String.class);
            method.invoke(aClass.newInstance(),"猜猜我是怎么来的~~~");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}

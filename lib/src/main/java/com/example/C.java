package com.example;

/**
 * Date：2018/4/8
 * Desc：
 * Created by xuliangchun.
 */

public class C extends A{
    private Number n;
    @Override
    public Number getT() {
        return n;
    }
//    @Override
    public void setT(Number n) {
        this.n = n;
        System.out.print(A.class);
    }
}

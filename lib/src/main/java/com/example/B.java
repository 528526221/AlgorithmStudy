package com.example;

/**
 * Date：2018/1/3
 * Desc：
 * Created by xuliangchun.
 */

public class B extends A<Number> {
    private Number n;

    @Override
    public Number getT() {
        return n;
    }

    @Override
    public void setT(Number n) {
        this.n = n;
    }
}

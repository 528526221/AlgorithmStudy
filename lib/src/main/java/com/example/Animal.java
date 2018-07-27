package com.example;

/**
 * Date：2018/6/22
 * Desc：
 * Created by xuliangchun.
 */

public class Animal {
    private String desc;
    int count = 2;

    public Animal() {
        this.desc = getDesc();
    }

    public String getDesc() {
        return "Animal";
    }

    private final void eat(){

    }
}

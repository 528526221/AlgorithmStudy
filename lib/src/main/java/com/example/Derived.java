package com.example;

/**
 * Date：2018/6/22
 * Desc：
 * Created by xuliangchun.
 */

public class Derived extends Base{
    private int i = 22;

    public Derived() {
        i = 222;
    }

    public void display(){
        System.out.print("Derived:"+i+"\n");
    }

    public void sub(){
        System.out.print("Derived:sub"+"\n");
    }
}

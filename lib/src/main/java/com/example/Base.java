package com.example;

/**
 * Date：2018/6/22
 * Desc：
 * Created by xuliangchun.
 */

public class Base {
    private int i  =2;

    public Base() {
        System.out.print(this.i+"\n");
        System.out.print(this.getClass()+"\n");
        this.display();
        this.sub();
    }

    public void display(){
        System.out.print("Base:"+i+"\n");
    }

    public void sub(){

    }
}

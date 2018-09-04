package com.example.designpattern.factory;

/**
 * Date：2018/9/4
 * Desc：工厂方法的本质也是用来选择实现的，跟简单工厂的区别在于工厂方法是把选择具体实现的功能延迟到子类去实现
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        AbstractFactory factory = new AppleFactory();
        factory.createJuice();
    }
}

package com.example.designpattern.facade;

/**
 * Date：2018/9/4
 * Desc：为了让外部减少与子系统内多个模块的交互，松散耦合。外观应该是包装已有的功能，而不是添加新的实现
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        new FacadeImpl().test();
        new FacadeImpl().a2();
    }
}

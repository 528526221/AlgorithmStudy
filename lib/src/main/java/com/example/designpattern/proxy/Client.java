package com.example.designpattern.proxy;

/**
 * Date：2018/9/13
 * Desc：
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        OrderApi orderApi = new DynamicProxy().getProxyInterface(new Order(1,"xulc"));
        orderApi.getOrderOperator();
        orderApi.setOrderNum(2,"xul");
    }
}

package com.example.designpattern.proxy;

/**
 * Date：2018/9/12
 * Desc：
 * Created by xulc.
 */

public class OrderProxy implements OrderApi {
    private Order order;//持有被代理的具体的目标对象


    public OrderProxy(Order order) {
        this.order = order;
    }

    @Override
    public int getOrderNum() {
        return order.getOrderNum();
    }

    @Override
    public String getOrderOperator() {
        return order.getOrderOperator();
    }

    @Override
    public void setOrderNum(int orderNum, String operator) {

    }
}

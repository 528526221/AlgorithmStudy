package com.example.designpattern.proxy;

/**
 * Date：2018/9/12
 * Desc：
 * Created by xulc.
 */

public class Order implements OrderApi{
    private int orderNum;
    private String orderOperator;
    @Override
    public int getOrderNum() {
        System.out.print("获取订单数量信息");
        return orderNum;
    }

    @Override
    public String getOrderOperator() {
        System.out.print("获取操作员信息");
        return orderOperator;
    }

    @Override
    public void setOrderNum(int orderNum,String operator) {
        System.out.print("修改订单数量");
        this.orderNum = orderNum;

    }


    public Order(int orderNum, String orderOperator) {
        this.orderNum = orderNum;
        this.orderOperator = orderOperator;
    }
}

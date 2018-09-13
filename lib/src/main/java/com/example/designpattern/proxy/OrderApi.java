package com.example.designpattern.proxy;

/**
 * Date：2018/9/12
 * Desc：
 * Created by xulc.
 */

public interface OrderApi {
    int getOrderNum();
    String getOrderOperator();
    void setOrderNum(int orderNum,String operator);
}

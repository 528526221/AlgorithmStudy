package com.example.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Date：2018/9/12
 * Desc：
 * Created by xulc.
 */

public class DynamicProxy implements InvocationHandler{

    private OrderApi order;//被代理的对象


    public OrderApi getProxyInterface(Order order){
        this.order = order;//设置被代理的对象，好方便invoke里面的操作
        //把真正的订单对象和动态代理关联起来
        OrderApi orderApi = (OrderApi) Proxy.newProxyInstance(order.getClass().getClassLoader(),
                order.getClass().getInterfaces(),this);
        return orderApi;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (method.getName().startsWith("set")){
            if (order.getOrderOperator() != null && order.getOrderOperator().equals(objects[1])){
                return method.invoke(order,objects);
            }else {
                System.out.print("对不起，你无权修改本订单数据");
            }
        }else {
            return method.invoke(order,objects);
        }
        return null;
    }
}

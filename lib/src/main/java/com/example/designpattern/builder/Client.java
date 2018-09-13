package com.example.designpattern.builder;

/**
 * Date：2018/9/11
 * Desc：
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        Product product = new Product.Builder().buildDesc("描述").buildName("名字").build();

    }
}

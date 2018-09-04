package com.example.designpattern.facade;

/**
 * Date：2018/9/4
 * Desc：
 * Created by xulc.
 */

public class BModuleImpl implements BModuleApi {
    @Override
    public void testB() {
        System.out.print("B模块中测试");
    }

    @Override
    public void b1() {

    }
}

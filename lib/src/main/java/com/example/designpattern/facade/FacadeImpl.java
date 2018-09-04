package com.example.designpattern.facade;

/**
 * Date：2018/9/4
 * Desc：
 * Created by xulc.
 */

public class FacadeImpl implements FacadeApi{
    @Override
    public void test() {
        new AModuleImpl().testA();
        new BModuleImpl().testB();
    }

    @Override
    public void a2() {
        new AModuleImpl().a2();
    }
}

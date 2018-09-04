package com.example.designpattern.factory;

/**
 * Date：2018/9/4
 * Desc：抽象工厂退化成只有一个实现，不分层次，那么就是简单工厂
 * Created by xulc.
 */

public class AppleFactory extends AbstractFactory{
    @Override
    Juice createJuice() {
        return new AppleJuice();
    }

    @Override
    Pie createPie() {
        return new ApplePie();
    }
}

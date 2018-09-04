package com.example.designpattern.factory;

/**
 * Date：2018/9/4
 * Desc：
 * Created by xulc.
 */

public class BananaFactory extends AbstractFactory{
    @Override
    Juice createJuice() {
        return new BananaJuice();
    }

    @Override
    Pie createPie() {
        return new BananaPie();
    }
}

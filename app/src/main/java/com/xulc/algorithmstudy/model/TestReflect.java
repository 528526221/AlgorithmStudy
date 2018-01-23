package com.xulc.algorithmstudy.model;

/**
 * Date：2018/1/23
 * Desc：
 * Created by xuliangchun.
 */

public class TestReflect {
    private String name;
    private int age;

    public TestReflect(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String getName() {
        return name;
    }


    private int getAge() {
        return age;
    }
}

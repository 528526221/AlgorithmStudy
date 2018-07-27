package com.example;

/**
 * Date：2018/7/18
 * Desc：
 * Created by xuliangchun.
 */

public class Apple extends Fruit{

    static class SmallApple{

    }

    @Override
    public void test() {
        super.test();
    }

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof Apple) {

        }

        Apple apple = (Apple) o;

        return name != null ? name.equals(apple.name) : apple.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

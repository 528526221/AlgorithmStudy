package com.example;

/**
 * Date：2018/6/22
 * Desc：
 * Created by xuliangchun.
 */

public class Wolf extends Animal {
    private String name;
    private double weight;
    int count = 4;

    public Wolf(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String getDesc() {
        return "Wolf{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';    }

    @Override
    public String toString() {
        return "Wolf{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", count=" + count +
                '}';
    }
}

package com.example;

/**
 * Date：2018/4/4
 * Desc：
 * Created by xuliangchun.
 */

public class OuterClass {
    private int a;
    private void testOuter(){
        InnerClass innerClass = new InnerClass();
        innerClass.testInner();//为了访问内部类的私有方法/属性，编译器自动为InnerClass这个内部类合成access&100方法供外部类调用
    }
    private class InnerClass{
        private int b;
        private void testInner(){
            OuterClass outerClass = new OuterClass();
            outerClass.testOuter();//为了访问外部类的私有方法/属性，编译器自动为外部类生成access&**方法供内部类调用
        }
    }
}

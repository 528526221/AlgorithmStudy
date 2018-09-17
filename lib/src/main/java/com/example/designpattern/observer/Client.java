package com.example.designpattern.observer;

/**
 * Date：2018/9/11
 * Desc：
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        Reader reader = new Reader("张三");
        Reader reader1 = new Reader("李四");

        NewPaper paper = new NewPaper();
        paper.addObserver(reader);
        paper.addObserver(reader1);

        paper.setContent("新消息123");

        paper.deleteObserver(reader);
        paper.setContent("新消息456");


    }

}

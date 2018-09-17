package com.example.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Date：2018/9/14
 * Desc：
 * Created by xulc.
 */

public class Reader implements Observer {
    private String name;

    public Reader(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.print(name+"收到通知"+((NewPaper)observable).getContent()+"\n");
    }
}

package com.example.designpattern.observer;

import java.util.Observable;

/**
 * Date：2018/9/14
 * Desc：
 * Created by xulc.
 */

public class NewPaper extends Observable {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.setChanged();
        this.notifyObservers();
    }
}

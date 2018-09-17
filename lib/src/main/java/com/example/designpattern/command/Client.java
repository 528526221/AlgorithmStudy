package com.example.designpattern.command;

/**
 * Date：2018/9/11
 * Desc：
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        Box box = new Box(new OpenCommand(new GIgaMainBoard()));
        box.pressOpenButton();
    }
}

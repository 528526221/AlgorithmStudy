package com.example.designpattern.command;

/**
 * Date：2018/9/14
 * Desc：
 * Created by xulc.
 */

public class Box {
    private Command command;

    public Box(Command command) {
        this.command = command;
    }

    public void pressOpenButton(){
        command.execute();
    }
}

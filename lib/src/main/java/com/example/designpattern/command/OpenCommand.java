package com.example.designpattern.command;

/**
 * Date：2018/9/14
 * Desc：
 * Created by xulc.
 */

public class OpenCommand implements Command {
    private MainBoard mainBoard;

    public OpenCommand(MainBoard mainBoard) {
        this.mainBoard = mainBoard;
    }

    @Override
    public void execute() {
        mainBoard.open();
    }
}

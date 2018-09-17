package com.example.designpattern.command;

import com.example.MyUtil;

/**
 * Date：2018/9/14
 * Desc：
 * Created by xulc.
 */

public class GIgaMainBoard implements MainBoard{
    @Override
    public void open() {
        MyUtil.printLog("开机中");
    }
}

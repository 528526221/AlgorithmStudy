package com.example.designpattern.adapter;

/**
 * Date：2018/9/5
 * Desc：
 * Created by xulc.
 */

public class LogFileImpl implements LogFileApi{
    @Override
    public String readFileLog() {
        System.out.print("读取文件日志\n");
        return null;
    }

    @Override
    public boolean writeFileLog(String log) {
        System.out.print("写入文件日志\n" );
        return false;
    }
}

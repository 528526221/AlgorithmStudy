package com.example.designpattern.adapter;

/**
 * Date：2018/9/5
 * Desc：
 * Created by xulc.
 */

public interface LogFileApi {
    String readFileLog();
    boolean writeFileLog(String log);
}

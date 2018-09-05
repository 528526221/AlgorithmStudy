package com.example.designpattern.adapter;

/**
 * Date：2018/9/5
 * Desc：
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        LogFileApi api = new LogFileImpl();
        api.writeFileLog("");
        api.readFileLog();
    }
}

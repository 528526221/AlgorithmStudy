package com.example.designpattern.Singleton;

/**
 * Date：2018/9/6
 * Desc：
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        Util.getInstance().operation();
        EasySingleton.INSTANCE.operation();
    }
}

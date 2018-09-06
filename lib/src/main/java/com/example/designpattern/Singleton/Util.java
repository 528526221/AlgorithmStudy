package com.example.designpattern.Singleton;

/**
 * Date：2018/9/6
 * Desc：
 * Created by xulc.
 */

public class Util {
    public static Util getInstance(){
        return UtilHolder.instance;
    }

    //静态内部类相当于外部类的成员，只有在第一次使用时才会被装载
    private static class UtilHolder{
        private static Util instance = new Util();
    }

    public void operation(){

    }
}

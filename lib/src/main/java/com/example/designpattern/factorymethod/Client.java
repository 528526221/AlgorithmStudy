package com.example.designpattern.factorymethod;

/**
 * Date：2018/9/6
 * Desc：
 * Created by xulc.
 */

public class Client {
    public static void main(String[] args){
        AbstractExportOperate operate = new ExportTxtFileOperate();
        operate.doSomeOperation("测试");
    }
}

package com.example.designpattern.factorymethod;

/**
 * Date：2018/9/6
 * Desc：
 * Created by xulc.
 */

public class ExportTxtFile implements ExportFileApi{
    @Override
    public boolean export(String data) {
        System.out.print("这里具体实现了怎么导出");
        return true;
    }
}

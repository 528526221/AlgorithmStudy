package com.example.designpattern.factorymethod;

/**
 * Date：2018/9/6
 * Desc：
 * Created by xulc.
 */

public class ExportTxtFileOperate extends AbstractExportOperate{
    @Override
    protected ExportFileApi factoryMethod() {
        return new ExportTxtFile();
    }
}

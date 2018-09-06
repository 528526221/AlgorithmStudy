package com.example.designpattern.factorymethod;

/**
 * Date：2018/9/6
 * Desc：
 * Created by xulc.
 */

//在父类不知道具体实现的情况下，完成自身的功能调用，而具体实现延迟到子类实现，类似开发一个框架，把实现交给子类，先走通流程再说
public abstract class AbstractExportOperate {

    protected abstract ExportFileApi factoryMethod();

    public void doSomeOperation(String data){
        ExportFileApi product = factoryMethod();
        product.export(data);
    }
}

//如果把父类实现成为一个具体的类，那么就需要提供一个缺省的工厂方法。同时子类也可以覆盖父类的实现

package com.example;

/**
 * Date：2018/6/21
 * Desc：javap -c <classes> 分解方法代码，显示每个方法具体的字节码
 * Created by xuliangchun.
 */

public class JavaToolTest {
    String name = "xulc";
    int  count = 20;
    {
        count = 12;
    }

    public JavaToolTest() {
        System.out.print(count);
    }

    public JavaToolTest(String name) {
        System.out.print(name);
    }

}

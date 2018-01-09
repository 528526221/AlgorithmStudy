package com.example;

import java.sql.SQLException;

/**
 * Date：2018/1/3
 * Desc：
 * Created by xuliangchun.
 */

public class B extends A {
    @Override
    protected Integer test() throws SQLException{
        return 1;
    }
}

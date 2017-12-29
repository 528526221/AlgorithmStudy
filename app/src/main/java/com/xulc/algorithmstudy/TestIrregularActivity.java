package com.xulc.algorithmstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Date：2017/12/20
 * Desc：
 * Created by xuliangchun.
 */

public class TestIrregularActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_irregular_activity);
        IrregularLayout irregularLayout = (IrregularLayout) findViewById(R.id.irregularLayout);
        Button button = new Button(this);
        button.setText("我是一只小小鸟");
        irregularLayout.addView(button);
        Button button1 = new Button(this);
        button1.setText("我是一只小小鸟");
        irregularLayout.addView(button1);
        Button button2 = new Button(this);
        button2.setText("我是一只小小鸟");
        irregularLayout.addView(button2);
        Button button3 = new Button(this);
        button3.setText("我是一只小小鸟");
        irregularLayout.addView(button3);

    }
}

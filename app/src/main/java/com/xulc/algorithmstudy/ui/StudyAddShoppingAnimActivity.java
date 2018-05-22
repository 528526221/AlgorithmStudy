package com.xulc.algorithmstudy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.util.AddShoppingAnimUtil;

/**
 * Date：2018/5/22
 * Desc：
 * Created by xuliangchun.
 */

public class StudyAddShoppingAnimActivity extends BaseActivity implements View.OnClickListener, AddShoppingAnimUtil.AddShoppingAnimListener {
    private Button btnStart1,btnStart2,btnStart3,btnStart4,btnEnd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_add_shopping_anim);
        btnStart1 = (Button) findViewById(R.id.btnStart1);
        btnStart2 = (Button) findViewById(R.id.btnStart2);
        btnStart3 = (Button) findViewById(R.id.btnStart3);
        btnStart4 = (Button) findViewById(R.id.btnStart4);

        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnStart1.setOnClickListener(this);
        btnStart2.setOnClickListener(this);
        btnStart3.setOnClickListener(this);
        btnStart4.setOnClickListener(this);
        btnEnd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart1:
                AddShoppingAnimUtil.startAnim(this,btnStart1,btnEnd,1000L,this);
                break;
            case R.id.btnStart2:
                AddShoppingAnimUtil.startAnim(this,btnStart2,btnEnd,1000L,this);
                break;
            case R.id.btnStart3:
                AddShoppingAnimUtil.startAnim(this,btnStart3,btnEnd,1000L,this);
                break;
            case R.id.btnStart4:
                AddShoppingAnimUtil.startAnim(this,btnStart4,btnEnd,1000L,this);
                break;
            case R.id.btnEnd:
                AddShoppingAnimUtil.startAnim(this,btnEnd,btnStart3,1000L,this);
                break;
        }
    }

    @Override
    public void onFinishAnim() {
        Log.i("xlc","动画结束");
    }

    @Override
    protected void onDestroy() {
        AddShoppingAnimUtil.stopAnim();
        super.onDestroy();
    }
}

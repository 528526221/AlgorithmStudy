package com.xulc.algorithmstudy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.util.AddShoppingAnimUtil;
import com.xulc.algorithmstudy.widget.RoundImageView;


/**
 * Date：2018/5/22
 * Desc：
 * Created by xuliangchun.
 */

public class StudyAddShoppingAnimActivity extends BaseActivity implements View.OnClickListener, AddShoppingAnimUtil.AddShoppingAnimListener {
    private Button btnStart1,btnStart2,btnStart3,btnEnd;
    private RoundImageView roundIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_add_shopping_anim);
        btnStart1 = (Button) findViewById(R.id.btnStart1);
        btnStart2 = (Button) findViewById(R.id.btnStart2);
        btnStart3 = (Button) findViewById(R.id.btnStart3);
        roundIv = (RoundImageView) findViewById(R.id.roundIv);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnStart1.setOnClickListener(this);
        btnStart2.setOnClickListener(this);
        btnStart3.setOnClickListener(this);
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

    public void changePicture(View view) {
        if (view.getTag() == null){
            view.setTag(1);
            Glide.with(this).load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2049667773,1018633067&fm=26&gp=0.jpg").into(roundIv);
        }else {
            view.setTag(null);
            Glide.with(this).load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2804169900,3992834783&fm=26&gp=0.jpg").into(roundIv);
        }
    }
}

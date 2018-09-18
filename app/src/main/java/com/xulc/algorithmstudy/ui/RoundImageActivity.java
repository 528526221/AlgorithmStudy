package com.xulc.algorithmstudy.ui;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.widget.RoundImageView;

public class RoundImageActivity extends BaseActivity {
    private RoundImageView roundIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image);
        roundIv = (RoundImageView) findViewById(R.id.roundIv);
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

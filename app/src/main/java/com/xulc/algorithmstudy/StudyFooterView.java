package com.xulc.algorithmstudy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Date：2017/12/18
 * Desc：
 * Created by xuliangchun.
 */

public class StudyFooterView extends LinearLayout implements StudyRefreshView.IFooterCallBack{
    private TextView tvLoad;
    public StudyFooterView(Context context) {
        this(context,null);
    }

    public StudyFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_study_footer_view,this,true);
        tvLoad = findViewById(R.id.tvLoad);
    }

    @Override
    public void onStateLoading(int footerHeight) {
        tvLoad.setText("正在为您加载呢...");

    }

    @Override
    public void onStateNormal() {
        tvLoad.setText("上拉我看看");
    }

    @Override
    public void onStateFinish(boolean loadNoMoreData) {
        if (loadNoMoreData){
            tvLoad.setText("啥都没有了啊：）");
        }else {
            tvLoad.setText("加载成功");
        }
    }

    @Override
    public void onFooterMove(int footerHeight, int offsetY) {
        if (offsetY>=footerHeight){
            tvLoad.setText("松开我看看");
        }else {
            tvLoad.setText("上拉我看看");
        }
    }
}

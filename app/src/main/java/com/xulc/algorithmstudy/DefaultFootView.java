package com.xulc.algorithmstudy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public class DefaultFootView extends LinearLayout implements IFootCallBack{
    private TextView tvLoadTip;
    private ProgressBar progressBar;
    public DefaultFootView(Context context) {
        this(context,null);
    }

    public DefaultFootView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_foot_view,this,true);
        progressBar = findViewById(R.id.progressBar);
        tvLoadTip = findViewById(R.id.tvLoadTip);
        setVisibility(INVISIBLE);//初始状态对外不可见
    }

    @Override
    public void startLoading() {
        tvLoadTip.setText("正在加载...");
        progressBar.setVisibility(VISIBLE);
        setVisibility(VISIBLE);

    }

    @Override
    public void loadFinish(boolean isSuccess) {
        tvLoadTip.setText("加载完成");
        progressBar.setVisibility(GONE);
    }

    @Override
    public void loadFinishShowNoMore() {
        tvLoadTip.setText("--已经没有更多了--");
        progressBar.setVisibility(GONE);
    }

    @Override
    public void reset() {
        setVisibility(INVISIBLE);//重置初始状态
    }
}

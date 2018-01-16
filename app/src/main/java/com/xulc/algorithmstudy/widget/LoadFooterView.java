package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.xulc.algorithmstudy.R;

/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public class LoadFooterView extends LinearLayout implements RefreshFooter{

    private ProgressBar progressBar;
    private TextView tvLoadTip;
    private boolean isLoading;
    public LoadFooterView(Context context) {
        this(context,null);
    }

    public LoadFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_foot_view,this,true);
        progressBar = findViewById(R.id.progressBar);
        tvLoadTip = findViewById(R.id.tvLoadTip);

    }

    @Override
    public void onPullingUp(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onPullReleasing(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onLoadmoreReleased(RefreshLayout layout, int footerHeight, int extendHeight) {

    }

    @Override
    public boolean setLoadmoreFinished(boolean finished) {
        Log.d("xlc","setLoadmoreFinished");

        isLoading = false;
        if (finished){
            tvLoadTip.setText("--没有更多数据了--");
            progressBar.setVisibility(GONE);
        }
        return true;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        Log.d("xlc","onFinish");
        isLoading = false;
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState){
            case Loading:
                Log.d("xlc","loading");
                if (!isLoading){
                    isLoading = true;
                    tvLoadTip.setText("正在加载...");
                    progressBar.setVisibility(VISIBLE);
                }

                break;
        }
    }
}

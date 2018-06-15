package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xulc.algorithmstudy.R;

/**
 * Date：2018/5/23
 * Desc：波浪刷新header
 * Created by xuliangchun.
 */

public class WaveHeaderView extends LinearLayout implements StudyRefreshView.IHeaderCallBack {
    private WaveCircleLoadingView wave;
    private TextView tvLoadTip;
    private boolean isRefreshing;//正在刷新中

    public WaveHeaderView(Context context) {
        this(context, null);
    }

    public WaveHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context mContext) {
        LayoutInflater.from(mContext).inflate(R.layout.layout_wave_header_view, this, true);
        wave = findViewById(R.id.wave);
        tvLoadTip = findViewById(R.id.tvLoadTip);
    }

    @Override
    public void onStateRefresh(int headerHeight, StudyRefreshView.Status status) {
        isRefreshing = true;
        tvLoadTip.setText("正在刷新");
        wave.setRefreshing(true);
    }

    @Override
    public void onStateNormal(StudyRefreshView.Status status) {
        tvLoadTip.setText("下拉刷新");
    }

    @Override
    public void onStateFinish(StudyRefreshView.Status status) {
        isRefreshing = false;
        tvLoadTip.setText("下拉刷新");
    }

    @Override
    public void onHeaderMove(int headerHeight, int offsetY, StudyRefreshView.Status status) {
        if (!isRefreshing) {
            wave.setOffsetPercent(Math.min(1.0f, (float) offsetY / headerHeight));
            if (offsetY == 0) {
                tvLoadTip.setText("下拉刷新");
            } else if (offsetY >= headerHeight) {
                tvLoadTip.setText("松开立即刷新");
            } else {
                tvLoadTip.setText("下拉刷新");
            }
        }
    }
}

package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xulc.algorithmstudy.util.DensityUtil;
import com.xulc.algorithmstudy.R;


/**
 * Date：2017/9/4
 * Desc：自定义下拉刷新头布局
 * Created by xuliangchun.
 */

public class StudyHeaderView extends LinearLayout implements StudyRefreshView.IHeaderCallBack {
    private LoadingCircle loadCircle;//加载圆圈
    private TextView tvLoadTip;//提示文字
    private boolean isRefreshing;//正在刷新中


    public StudyHeaderView(Context context) {
        this(context,null);
    }

    public StudyHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context mContext) {
        LayoutInflater.from(mContext).inflate(R.layout.layout_study_header_view,this,true);
        loadCircle = findViewById(R.id.loadCircle);
        tvLoadTip = findViewById(R.id.tvLoadTip);
        DensityUtil.getInstance().setViewMargin(loadCircle,199,0,0,0);

        tvLoadTip.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtil.getInstance().getRateHeight(24));
    }


    @Override
    public void onStateRefresh(int headerHeight,StudyRefreshView.Status status) {
        isRefreshing = true;
        tvLoadTip.setText("加载中....");
        DensityUtil.getInstance().setViewMargin(tvLoadTip,30,0,0,0);
        loadCircle.startRotate((int) (headerHeight/0.75));
    }

    @Override
    public void onStateNormal(StudyRefreshView.Status status) {
        isRefreshing = false;
        tvLoadTip.setText("下拉即可刷新...");

    }

    @Override
    public void onStateFinish(StudyRefreshView.Status status) {
        isRefreshing = false;
        loadCircle.stopRotate();
        tvLoadTip.setText("下拉即可刷新...");
    }

    @Override
    public void onHeaderMove(int headerHeight, int offsetY,StudyRefreshView.Status status) {
        //以头布局高度的4/3作为圆圈转满一圈的阈值
        if (!isRefreshing){
            loadCircle.setCurrentAngle((int) (headerHeight/0.75),offsetY);
            if (offsetY==0){
                tvLoadTip.setText("下拉即可刷新...");
                DensityUtil.getInstance().setViewMargin(tvLoadTip,0,0,0,0);
            }else if (offsetY>=headerHeight){
                tvLoadTip.setText("释放即可刷新...");
            }else {
                tvLoadTip.setText("下拉即可刷新...");
            }
        }
    }
}

package com.xulc.algorithmstudy;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;


/**
 * Date：2017/9/4
 * Desc：自定义下拉刷新头布局
 * Created by xuliangchun.
 */

public class CircleHeaderView extends LinearLayout implements RefreshHeader {
    private LoadingCircle loadCircle;//加载圆圈
    private TextView tvLoadTip;//提示文字
    private boolean isRefreshing;//正在刷新中
    private int headViewHeight;//头布局高度


    public CircleHeaderView(Context context) {
        this(context,null);
    }

    public CircleHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }




    private void init(Context mContext) {
        LayoutInflater.from(mContext).inflate(R.layout.layout_circle_header_view,this,true);
        loadCircle = findViewById(R.id.loadCircle);
        tvLoadTip = findViewById(R.id.tvLoadTip);
        DensityUtil.getInstance().setViewMargin(loadCircle,199,0,0,0);

        tvLoadTip.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtil.getInstance().getRateHeight(24));
    }

    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {
        headViewHeight = headerHeight;
        //以头布局高度的4/3作为圆圈转满一圈的阈值
        if (!isRefreshing){
            loadCircle.setCurrentAngle((int) (headerHeight/0.75),offset);
            if (offset==0){
                tvLoadTip.setText("下拉即可刷新...");
                DensityUtil.getInstance().setViewMargin(tvLoadTip,0,0,0,0);

            }
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onRefreshReleased(RefreshLayout layout, int headerHeight, int extendHeight) {

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
        isRefreshing = false;
        loadCircle.stopRotate();
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState){
            case None:
            case PullDownToRefresh:
                tvLoadTip.setText("下拉即可刷新...");
                break;
            case Refreshing:
                isRefreshing = true;
                tvLoadTip.setText("加载中....");
                DensityUtil.getInstance().setViewMargin(tvLoadTip,30,0,0,0);
                loadCircle.startRotate((int) (headViewHeight/0.75));
                break;
            case ReleaseToRefresh:
                tvLoadTip.setText("释放即可刷新...");

                break;
        }
    }
}

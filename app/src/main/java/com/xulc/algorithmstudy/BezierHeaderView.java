package com.xulc.algorithmstudy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Date：2017/12/20
 * Desc：
 * Created by xuliangchun.
 */

public class BezierHeaderView extends LinearLayout implements StudyRefreshView.IHeaderCallBack{
    private TextView tvLoadTip;
    private BezierView bezierView;
    private boolean isRefreshFinishMove;
    public BezierHeaderView(Context context) {
        this(context,null);
    }

    public BezierHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_bezier_header,this,true);
        bezierView = findViewById(R.id.bezierView);
        tvLoadTip = findViewById(R.id.tvLoadTip);
    }



    @Override
    public void onStateRefresh(int headerHeight) {
        isRefreshFinishMove = true;
    }

    @Override
    public void onStateNormal() {

    }

    @Override
    public void onStateFinish() {

    }


    @Override
    public void onHeaderMove(int headerHeight, int offsetY) {
        //刷新完成后的头部位移不需要通知贝塞尔
        if (!isRefreshFinishMove&&offsetY<=headerHeight){
            bezierView.setOffsetY(offsetY);

        }
        if (offsetY==0){
            isRefreshFinishMove = false;
        }

    }

    public void stopRefresh(StudyRefreshView studyRefreshView){
        bezierView.stopRefresh(studyRefreshView);
    }

}

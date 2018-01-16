package com.xulc.algorithmstudy.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xulc.algorithmstudy.R;


/**
 * Date：2017/12/20
 * Desc：贝塞尔自定义header
 * Created by xuliangchun.
 */

public class BezierHeaderView extends LinearLayout implements StudyRefreshView.IHeaderCallBack{
    private ImageView ivInlife;
    private int startRotateDistance = 160;//inlife开始作旋转操作的临界位置点
    private BezierView bezierView;
    private boolean isRefreshFinishMove;
    private Animation scaleAnimation;
    public BezierHeaderView(Context context) {
        this(context,null);
    }

    public BezierHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.layout_bezier_header,this,true);
        bezierView = findViewById(R.id.bezierView);
        ivInlife = findViewById(R.id.ivInlife);
        //定义缩放动画
        scaleAnimation = new ScaleAnimation(1.0f,1.2f,1.0f,1.2f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setRepeatCount(-1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        //初始状态inlife
        ivInlife.setScaleX(0f);
        ivInlife.setScaleY(0f);
    }



    @Override
    public void onStateRefresh(int headerHeight,StudyRefreshView.Status status) {
        isRefreshFinishMove = true;
        ivInlife.startAnimation(scaleAnimation);
    }

    @Override
    public void onStateNormal(StudyRefreshView.Status status) {

    }

    @Override
    public void onStateFinish(StudyRefreshView.Status status) {

    }


    @Override
    public void onHeaderMove(int headerHeight, int offsetY,StudyRefreshView.Status status) {
        //刷新完成后的头部位移不需要通知贝塞尔，贝塞尔的绘制注意偏移量要控制在headerHeight以内
        if (!isRefreshFinishMove){
            bezierView.setOffsetY(Math.min(headerHeight,offsetY));
            //下拉到某个临界位置时，开始对inlife进行旋转
            if (offsetY>=startRotateDistance){
                //接下来到真正能够刷新的距离做180等分
                //显示图标
                ivInlife.setScaleX(1.0f);
                ivInlife.setScaleY(1.0f);

                //加一个容错
                if (offsetY>=headerHeight){
                    ivInlife.setRotation(180);
                }else {
                    int angle = 180*(offsetY-startRotateDistance)/(headerHeight-startRotateDistance);
                    ivInlife.setRotation(angle);
                }
            }
        }

        //重置一些条件
        if (offsetY==0){
            isRefreshFinishMove = false;
            //图标要回到初始位置 初始状态
            ObjectAnimator anim = ObjectAnimator.ofFloat(ivInlife, "translationY", 0, 0);
            anim.start();
            ivInlife.setRotation(0);
        }


        //在达到inlife旋转的临界值前，进行一次0-1的展现
        if (offsetY<startRotateDistance){
            float alpha = (float) offsetY/(float) startRotateDistance;
            ivInlife.setScaleX(alpha);
            ivInlife.setScaleY(alpha);
        }

    }

    /**
     * 刷新完成
     * @param studyRefreshView
     */
    public void stopRefresh(StudyRefreshView studyRefreshView){
        //这个地方需要判断当前组件的状态 否则会误触发刷新完成
        ivInlife.clearAnimation();
        bezierView.stopRefresh(studyRefreshView);
        //刷新完成后 图标也要往上位移
        ObjectAnimator anim = ObjectAnimator.ofFloat(ivInlife, "translationY", 0, -getHeight());
        anim.setDuration(250);
        anim.start();
    }

}

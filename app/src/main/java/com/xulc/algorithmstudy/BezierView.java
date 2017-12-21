package com.xulc.algorithmstudy;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Date：2017/12/20
 * Desc：贝塞尔函数曲线
 * Created by xuliangchun.
 */

public class BezierView extends View {
    private StudyRefreshView studyRefreshView;
    private Path mPath;
    private Paint mPaint;
    private int offsetY;//偏移值，在各个方法中代表的含义不一定相同

    private int bounceDistance = 40;//给刷新完成的线上下弹动预留的距离

    private int canvasType;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPath.reset();
//        mPath.moveTo(0,getHeight()-offsetY);
//        mPath.quadTo(getWidth()/2,getHeight()+offsetY,getWidth(),getHeight()-offsetY);
//        mPath.lineTo(getWidth(),getHeight()+offsetY);
//        mPath.lineTo(0,getHeight()+offsetY);
//        mPath.close();
//        //绘制上部分红色区域有时候会和父布局中间隔一条线，所以改成绘制下部分白色区域
//        canvas.drawPath(mPath,mPaint);
        if (canvasType == 1){
            drawPulling(canvas);
        }else if(canvasType == 2){
            drawFinishBounce(canvas);
        }else if (canvasType == 3){
            drawBounceTop(canvas);
        }else {
            drawPulling(canvas);
        }

    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
        this.canvasType = 1;
        invalidate();

    }

    /**
     * 绘制下拉的时候
     * @param canvas
     */
    private void drawPulling(Canvas canvas){
        mPath.reset();
        if (offsetY <= bounceDistance) {
            //先绘制长方形
            mPaint.setColor(Color.parseColor("#e12828"));
            mPath.moveTo(0, getHeight() - offsetY);
            mPath.lineTo(0, getHeight());
            mPath.lineTo(getWidth(), getHeight());
            mPath.lineTo(getWidth(), getHeight() - offsetY);
            mPath.close();
        } else {
            //绘制长方形加贝塞尔曲线区域
            //采取补集绘制
            mPaint.setColor(Color.parseColor("#ffffff"));

            mPath.moveTo(0, getHeight() - offsetY+bounceDistance);
            mPath.quadTo(getWidth() / 2, getHeight() + offsetY-bounceDistance, getWidth(), getHeight() - offsetY+bounceDistance);
            mPath.lineTo(getWidth(), getHeight() + offsetY-bounceDistance);
            mPath.lineTo(0, getHeight() + offsetY-bounceDistance);
            mPath.close();
        }
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 绘制回弹
     * @param canvas
     */
    private void drawFinishBounce(Canvas canvas){
        mPath.reset();
        //采取补集绘制
        mPath.lineTo(0,bounceDistance);
        mPath.quadTo(getWidth()/2,offsetY,getWidth(),bounceDistance);
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);

    }

    /**
     * 绘制上弹-下弹-归位
     * @param canvas
     */
    private void drawBounceTop(Canvas canvas){
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPath.reset();
        mPath.lineTo(0,bounceDistance);
        mPath.quadTo(getWidth()/2,offsetY,getWidth(),bounceDistance);
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }


    /**
     * 刷新完成后的回弹
     */
    private void finishBounce(){
        this.canvasType = 2;
        int start = getHeight() + offsetY-bounceDistance;
        //动画生成offset的值
        ValueAnimator animator = ValueAnimator.ofInt(start,bounceDistance);
        animator.setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offsetY = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startBounceTopAnimator();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 上弹-下弹—归位
     */
    private void startBounceTopAnimator(){
        //二阶贝塞尔的控制点暂时还没有搞明白，所以这个地方控制点的坐标只是自己修正的
        canvasType = 3;
        ValueAnimator animator1 = ValueAnimator.ofInt(bounceDistance,-bounceDistance+5,2*bounceDistance,bounceDistance);
        animator1.setDuration(250);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offsetY = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        animator1.start();
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                studyRefreshView.stopRefresh();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 刷新完成
     * @param studyRefreshView
     */
    public void stopRefresh(StudyRefreshView studyRefreshView){

        this.studyRefreshView = studyRefreshView;
        //先处理好本身的回弹，最后再调用组件的刷新完成
        finishBounce();

    }
}

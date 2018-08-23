package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xulc.algorithmstudy.R;

/**
 * Date：2018/8/23
 * Desc：
 * Created by xulc.
 */

public class CircleProgressView extends View{
    private Paint mPaint;
    private int darkColor;
    private int lightColor;
    private int thickness;
    private int circleRadius;//实际上是整个view的大小的一半
    private int ballRadius;
    private int scaleValue;
    private RectF rectF;


    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);

        darkColor = array.getColor(R.styleable.CircleProgressView_DarkColor,getResources().getColor(R.color.darkColor));
        lightColor = array.getColor(R.styleable.CircleProgressView_LightColor,getResources().getColor(R.color.lightColor));
        scaleValue = array.getInteger(R.styleable.CircleProgressView_ScaleValue,50);
        circleRadius = (int) array.getDimension(R.styleable.CircleProgressView_CircleRadius,200);
        thickness = (int) array.getDimension(R.styleable.CircleProgressView_Thickness,20);
        ballRadius = (int) array.getDimension(R.styleable.CircleProgressView_BallRadius,30);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(circleRadius/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(thickness);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(lightColor);
        if (rectF == null){
            rectF = new RectF(ballRadius-thickness/2,ballRadius-thickness/2,getWidth()-ballRadius+thickness/2,getHeight()-ballRadius+thickness/2);
        }
        canvas.drawArc(rectF,90,360*scaleValue/100,false,mPaint);



        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(darkColor);
        canvas.drawArc(rectF,90+360*scaleValue/100,360*(100-scaleValue)/100,false,mPaint);
        mPaint.setColor(lightColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getTrackXByScaleValue(),getTrackYByScaleValue(),ballRadius-thickness/2,mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(scaleValue),(getWidth()-mPaint.measureText(String.valueOf(scaleValue)))/2,getHeight()/2+circleRadius/5,mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST){
            //(2*ballRadius-thickness)/2是ball多出来的高度
            setMeasuredDimension(circleRadius*2,circleRadius*2);
        }

    }


    //假设圆心:o (x0,y0)
    //半径:r
    //角度:angle (角度是相对于图中红点位置而言，逆时针为负数，顺时针为正)
    //计算公式：
    //p2 (x1,y1), 其中angle = 30
    //x1 = x0 + r * cos(angle * PI / 180)
    //y1 = y0 + r * sin(angle * PI /180)

    /**
     * 计算当前刻度下ball的X中心坐标
     * @return
     */
    private float getTrackXByScaleValue(){
        return (float) (getWidth()/2 + (getWidth()/2 - ballRadius+thickness/2)*Math.cos((scaleValue*360/100+90) * Math.PI / 180));
    }

    /**
     * 计算当前刻度下ball的Y中心坐标
     * @return
     */
    private float getTrackYByScaleValue(){
        return (float) (getHeight()/2 + (getWidth()/2 - ballRadius+thickness/2)*Math.sin((scaleValue*360/100+90) * Math.PI / 180));
    }


    private boolean drag;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (Math.abs(event.getX()-getTrackXByScaleValue()) <= ballRadius && Math.abs(event.getY()-getTrackYByScaleValue()) <= ballRadius){
                    drag = true;
                }else {
                    drag = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (drag){
                    //计算与圆心两点的距离
                    float x = event.getX();
                    float y = event.getY();
                    double distanceX = Math.abs(x - getWidth()/2);
                    double distanceY = Math.abs(y - getHeight()/2);
                    double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                    if (distance>circleRadius/2){
                        //计算scaleValue的值

                        if (x>=getWidth()/2 && y<=getHeight()/2){
                            //第一象限
                            double a = Math.asin((x-getWidth()/2)/distance);
                            scaleValue = (int) (100*(a/Math.PI * 180+180)/360);
                            invalidate();
                        }else if (x<=getWidth()/2 && y<=getHeight()/2){
                            double a = Math.asin((getWidth()/2-x)/distance);
                            scaleValue = (int) (100*(180-a/Math.PI * 180)/360);
                            invalidate();
                        }else if (x<=getWidth()/2 && y>=getHeight()/2){
                            double a = Math.asin((getWidth()/2-x)/distance);
                            scaleValue = (int) (100*(a/Math.PI * 180)/360);
                            invalidate();
                        }else if (x>=getWidth()/2 && y>=getHeight()/2){
                            double a = Math.asin((x-getWidth()/2)/distance);
                            scaleValue = (int) (100*(360-a/Math.PI * 180)/360);
                            invalidate();
                        }

                    }else {
                        drag = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                drag = false;
                break;
        }
        return true;
    }
}

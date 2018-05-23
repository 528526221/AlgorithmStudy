package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.xulc.algorithmstudy.R;


/**
 * Date：2018/5/22
 * Desc：波浪加载
 * Created by xuliangchun.
 */

public class WaveLoadingView extends View{
    private Path mPath;
    private Paint mPaint;
    private RectF oval;

    private int startx = 0;//平移的关键值
    private int startFastx = 0;//平移的关键值

    private float waveHeight;//波浪的高度
    private float waveBaseLine;//波浪的基线Y值 值越大，波浪要满
    private int waveColor;//波浪颜色
    private int waveFastColor;//快浪颜色
    private int waveCircleBackground;//背景色
    private float waveCircleThickness;//圆圈的厚度
    private boolean waveUpAndDown;//波浪是否上下浪
    private boolean waveDown;//标记 当前波浪正在往下浪

    public WaveLoadingView(Context context) {
        this(context,null);
    }

    public WaveLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WaveLoadingView);
        waveBaseLine = array.getDimension(R.styleable.WaveLoadingView_WaveBaseLine,0);
        waveHeight = array.getDimension(R.styleable.WaveLoadingView_WaveHeight,0);
        waveColor = array.getColor(R.styleable.WaveLoadingView_WaveColor,Color.WHITE);
        waveFastColor = array.getColor(R.styleable.WaveLoadingView_WaveFastColor,Color.parseColor("#DDDEDE"));
        waveCircleBackground = array.getColor(R.styleable.WaveLoadingView_WaveCircleBackground, ContextCompat.getColor(context,R.color.wave_loading_background));
        waveCircleThickness = array.getDimension(R.styleable.WaveLoadingView_WaveCircleThickness,5);
        waveUpAndDown = array.getBoolean(R.styleable.WaveLoadingView_WaveUpAndDown,false);
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(waveCircleThickness);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (waveBaseLine == 0 || waveBaseLine >=height){
            waveBaseLine = height/2;
        }
        if (waveHeight == 0){
            waveHeight = height/10;
        }

//想调整波浪周期随着baseLine的值而改变，暂时先不搞
//        width = (int) (2*Math.sqrt((width/2)*(width/2)-(width/2-waveBaseLine)*(width/2-waveBaseLine)));

        drawCircle(canvas,width,height);


        drawWaveFast(canvas,width,height);


        drawWave(canvas,width,height);


        drawCircleOutside(canvas,width,height);

        if (waveUpAndDown){
            if (waveDown){
                waveBaseLine+=2;
                if (waveBaseLine>=height-10){
                    waveDown = false;
                    waveBaseLine-=2;
                }
            }else {
                waveBaseLine-=2;
                if (waveBaseLine<=10){
                    waveDown = true;
                    waveBaseLine+=2;
                }
            }
        }

        postInvalidateDelayed(3);
    }



    private void drawCircleOutside(Canvas canvas, int width, int height) {
        //下面半圆之外先绘制白色
        mPaint.setColor(Color.WHITE);
        mPath.reset();
        if (oval == null){
            oval = new RectF(0, 0, width, height);
        }
        mPath.arcTo(oval, 0, 180);
        mPath.lineTo(0,height);
        mPath.lineTo(width,height);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
        //把圆之外的白色部分用背景色再绘制一遍
        mPaint.setColor(waveCircleBackground);
        canvas.drawPath(mPath,mPaint);

        //上面半圆之外先绘制白色
        mPaint.setColor(Color.WHITE);
        mPath.reset();
        mPath.arcTo(oval, 0, -180);
        mPath.lineTo(0,0);
        mPath.lineTo(width,0);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
        //把圆之外的白色部分用背景色再绘制一遍
        mPaint.setColor(waveCircleBackground);
        canvas.drawPath(mPath,mPaint);



    }

    private void drawWaveFast(Canvas canvas, int width, int height) {
        mPath.reset();
        mPaint.setColor(waveFastColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.moveTo(startFastx-width*4/6, waveBaseLine);
        mPath.quadTo(startFastx-width*3/6, waveBaseLine -waveHeight,startFastx-width*2/6, waveBaseLine);

        mPath.quadTo(startFastx-width/6, waveBaseLine +waveHeight,startFastx+0, waveBaseLine);
        mPath.quadTo(startFastx+width/6, waveBaseLine -waveHeight,startFastx+width*2/6, waveBaseLine);
        mPath.quadTo(startFastx+width*3/6, waveBaseLine +waveHeight,startFastx+width*4/6, waveBaseLine);
        mPath.quadTo(startFastx+width*5/6, waveBaseLine -waveHeight,startFastx+width*6/6, waveBaseLine);

        mPath.lineTo(width,height);
        mPath.lineTo(0,height);

        mPath.close();
        canvas.drawPath(mPath,mPaint);

        startFastx += 5;
        if (startFastx>=width*4/6){
            startFastx = 0;
        }
    }

    private void drawWave(Canvas canvas, int width, int height) {
        mPath.reset();
        mPaint.setColor(waveColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.moveTo(startx-width*4/6, waveBaseLine);
        mPath.quadTo(startx-width*3/6, waveBaseLine -waveHeight,startx-width*2/6, waveBaseLine);

        mPath.quadTo(startx-width/6, waveBaseLine +waveHeight,startx+0, waveBaseLine);
        mPath.quadTo(startx+width/6, waveBaseLine -waveHeight,startx+width*2/6, waveBaseLine);
        mPath.quadTo(startx+width*3/6, waveBaseLine +waveHeight,startx+width*4/6, waveBaseLine);
        mPath.quadTo(startx+width*5/6, waveBaseLine -waveHeight,startx+width*6/6, waveBaseLine);

        mPath.lineTo(width,height);
        mPath.lineTo(0,height);
        mPath.close();

        canvas.drawPath(mPath,mPaint);
        startx += 3;
        if (startx>=width*4/6){
            startx = 0;
        }
    }

    private void drawCircle(Canvas canvas,int width,int height) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(waveColor);
        canvas.drawCircle(width/2,height/2,width/2,mPaint);
    }


}

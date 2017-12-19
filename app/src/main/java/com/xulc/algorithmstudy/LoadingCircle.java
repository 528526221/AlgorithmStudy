package com.xulc.algorithmstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Date：2017/9/4
 * Desc：下拉刷新加载的圆圈
 * Created by xuliangchun.
 */

public class LoadingCircle extends View {
    private Paint paint;
    private int circleRadius;//圆圈半径
    private int padding;//圆圈外间距
    private int strokeWidth;//线宽
    private int arrowWidth;//箭头宽
    private int currentAngle;//当前圆圈度数
    private RectF rectF = null;
    private Animation animation;//动画
    private boolean isRemoveArrow;//移除箭头



    public LoadingCircle(Context context) {
        this(context,null);
    }

    public LoadingCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        isRemoveArrow = false;
        if (isInEditMode()){
            strokeWidth = 4;
            arrowWidth = 6;
            circleRadius = 60;
            padding = 10;
        }else {
            strokeWidth = DensityUtil.getInstance().getRateHeight(2);
            arrowWidth = DensityUtil.getInstance().getRateHeight(3);
            circleRadius = DensityUtil.getInstance().getRateHeight(27);
            padding = DensityUtil.getInstance().getRateHeight(16);
        }

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rectF == null){
            int width = getWidth();
            rectF = new RectF(padding,padding,width-padding,width-padding);
        }

        /**
         * 绘制箭头
         */
        if (!isRemoveArrow){
            paint.setStrokeWidth(arrowWidth);

            canvas.drawLine(rectF.left+rectF.width()/2+paint.getStrokeWidth()/2,rectF.top+rectF.height()/3,
                    rectF.left+rectF.width()/2+paint.getStrokeWidth()/2,rectF.top+rectF.height()*2/3,paint);
            canvas.drawLine(rectF.left+rectF.width()/2+paint.getStrokeWidth()/2,rectF.top+rectF.height()*2/3,
                    rectF.left+rectF.width()/2+paint.getStrokeWidth()/2-rectF.height()/6,rectF.top+rectF.height()/2,paint);
            canvas.drawLine(rectF.left+rectF.width()/2+paint.getStrokeWidth()/2,rectF.top+rectF.height()*2/3,
                    rectF.left+rectF.width()/2+paint.getStrokeWidth()/2+rectF.height()/6,rectF.top+rectF.height()/2,paint);
        }

        /**
         * 绘制圆弧
         */
        paint.setStrokeWidth(strokeWidth);

        canvas.drawArc(rectF,-90,currentAngle,false,paint);

    }

    /**
     * 设置当前圆圈角度
     * @param maxHeight
     * @param offY
     */
    public void setCurrentAngle(int maxHeight,int offY) {
        this.currentAngle = offY*360/maxHeight;
        this.currentAngle = (int) Math.min(currentAngle,360*0.90);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(2*(circleRadius+padding),2*(circleRadius+padding));
    }

    /**
     * 开始刷新
     * 这个时候旋转的圈圈需要重绘一次  保证在90%
     */
    public void startRotate(int maxHeight){
        isRemoveArrow = true;
        setCurrentAngle(maxHeight,maxHeight);

        if (animation == null){
            animation = new RotateAnimation(0,360f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
            animation.setRepeatCount(-1);
            animation.setDuration(1300);
            animation.setInterpolator(new LinearInterpolator());
        }

        this.startAnimation(animation);
    }

    /**
     * 刷新完成
     */
    public void stopRotate(){
        isRemoveArrow = false;
        this.clearAnimation();
    }

}

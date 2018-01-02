package com.xulc.algorithmstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Date：2018/1/2
 * Desc：
 * Created by xuliangchun.
 */

public class BezierCircleIndicator extends View{
    private Paint paint;
    public BezierCircleIndicator(Context context) {
        this(context,null);
    }

    public BezierCircleIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}

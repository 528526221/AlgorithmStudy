package com.xulc.algorithmstudy;

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
 * Desc：
 * Created by xuliangchun.
 */

public class StudyQuadToView extends View{
    private Paint paint;
    private Path path;
    public StudyQuadToView(Context context) {
        this(context,null);
    }

    public StudyQuadToView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(0,200);
        path.quadTo(100,-100,200,0);
        canvas.drawPath(path,paint);
    }
}

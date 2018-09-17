package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.xulc.algorithmstudy.R;

/**
 * Date：2018/9/17
 * Desc：利用Xfermode混合模式实现圆形图片和圆角图片
 * Created by xulc.
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView{
    private Paint mPaint;
    private int roundType;//类型 圆或者圆角
    private float rx;//圆角x-radius
    private float ry;//圆角y-radius
    private Xfermode xfermode;
    public RoundImageView(Context context) {
        this(context,null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        roundType = array.getInt(R.styleable.RoundImageView_RoundType,1);
        rx = array.getDimension(R.styleable.RoundImageView_CornerX,0);
        ry = array.getDimension(R.styleable.RoundImageView_CornerY,0);
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();

        Bitmap bitmap = drawable.getBitmap();

        int width = getWidth();
        int height = getHeight();
        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();

        if (roundType == RoundType.CIRCLE.value){
            //1.处理圆形

            //裁剪圆形图片
            Bitmap bitmapClip = null;
            if (bHeight > bWidth){
                bitmapClip = Bitmap.createBitmap(bitmap,0,(bHeight-bWidth)/2,bWidth,bWidth);
            }else {
                bitmapClip = Bitmap.createBitmap(bitmap,(bWidth-bHeight)/2,0,bHeight,bHeight);
            }
            int min = Math.min(width,height);
            //缩放图片尺寸
            Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmapClip,min,min,true);
            int saveCount = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);
            canvas.drawCircle(width/2f,height/2f,min/2f,mPaint);
            mPaint.setXfermode(xfermode);
            canvas.drawBitmap(bitmapScale,(width-bitmapScale.getWidth())/2f,(height-bitmapScale.getHeight())/2f,mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(saveCount);

        }else {
            //2.处理圆角

            //裁剪合适尺寸
            Bitmap bitmapClip = null;
            if (bWidth*1.0f/bHeight > width*1.0f/height){
                //图片比控件的宽高比要大，那么把图片的横向裁剪一部分
                int dstBWidth = bHeight*width/height;
                bitmapClip = Bitmap.createBitmap(bitmap,(bWidth-dstBWidth)/2,0,dstBWidth,bHeight);
            }else {
                //裁剪图片纵向的一部分
                int dstBHeight = bWidth*height/width;
                bitmapClip = Bitmap.createBitmap(bitmap,0,(bHeight-dstBHeight)/2,bWidth,dstBHeight);
            }
            Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmapClip,width,height,true);
            int saveCount = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);
            canvas.drawRoundRect(new RectF(0,0,width,height),rx,ry,mPaint);
            mPaint.setXfermode(xfermode);
            canvas.drawBitmap(bitmapScale,0,0,mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(saveCount);
        }

    }

    enum RoundType{
        CIRCLE(1),
        CORNER(2);
        private int value;

        RoundType(int value) {
            this.value = value;
        }


    }
}

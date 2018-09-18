package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.xulc.algorithmstudy.R;

import java.lang.ref.WeakReference;

/**
 * Date：2018/9/17
 * Desc：利用Xfermode混合模式实现圆形图片和圆角图片
 * Created by xulc.
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint;
    private int roundType;//类型 圆或者圆角
    private float rx;//圆角x-radius
    private float ry;//圆角y-radius
    private Xfermode xfermode;//过渡模式
    private WeakReference<Bitmap> bitmapClip;//裁剪后的bitmap
    private WeakReference<Bitmap> bitmap;//drawable转化成bitmap
    private RectF rectF;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        roundType = array.getInt(R.styleable.RoundImageView_RoundType, 1);
        rx = array.getDimension(R.styleable.RoundImageView_CornerX, 0);
        ry = array.getDimension(R.styleable.RoundImageView_CornerY, 0);
        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        clearBitmap();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null)
            return;
        if (bitmap == null || bitmap.get() == null) {
            bitmap = new WeakReference<Bitmap>(drawableToBitmap(getDrawable()));
        }
        int width = getWidth();
        int height = getHeight();
        int bWidth = bitmap.get().getWidth();
        int bHeight = bitmap.get().getHeight();

        if (roundType == 1) {
            //1.处理圆形

            //裁剪圆形图片
            if (bitmapClip == null || bitmapClip.get() == null) {
                if (bHeight > bWidth) {
                    bitmapClip = new WeakReference<Bitmap>(Bitmap.createBitmap(bitmap.get(), 0, (bHeight - bWidth) / 2, bWidth, bWidth));
                } else {
                    bitmapClip = new WeakReference<Bitmap>(Bitmap.createBitmap(bitmap.get(), (bWidth - bHeight) / 2, 0, bHeight, bHeight));
                }
            }
            int min = Math.min(width, height);
            //混合模式绘制
            int saveCount = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);
            canvas.drawCircle(width / 2f, height / 2f, min / 2f, mPaint);
            mPaint.setXfermode(xfermode);
            canvas.drawBitmap(bitmapClip.get(), (width - bitmapClip.get().getWidth()) / 2f, (height - bitmapClip.get().getHeight()) / 2f, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(saveCount);
        } else {
            //2.处理圆角

            //裁剪合适尺寸
            if (bitmapClip == null || bitmapClip.get() == null) {
                if (bWidth > width) {
                    //裁剪图片横向的一部分
                    int dstBWidth = bHeight * width / height;
                    bitmapClip = new WeakReference<Bitmap>(Bitmap.createBitmap(bitmap.get(), (bWidth - dstBWidth) / 2, 0, dstBWidth, bHeight));
                } else {
                    //裁剪图片纵向的一部分
                    int dstBHeight = bWidth * height / width;
                    bitmapClip = new WeakReference<Bitmap>(Bitmap.createBitmap(bitmap.get(), 0, (bHeight - dstBHeight) / 2, bWidth, dstBHeight));
                }
            }
            //混合模式绘制
            int saveCount = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);
            if (rectF == null){
                rectF = new RectF(0,0,width,height);
            }
            canvas.drawRoundRect(rectF, rx, ry, mPaint);
            mPaint.setXfermode(xfermode);
            canvas.drawBitmap(bitmapClip.get(), 0, 0, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(saveCount);
        }

    }

    /**
     * 根据drawable获取一个缩放后宽高大于控件的bitmap
     *
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int dWidth = drawable.getIntrinsicWidth();
        int dHeight = drawable.getIntrinsicHeight();
        //控件的宽高
        int width = getWidth();
        int height = getHeight();
        //先把图片的宽高缩放到控件适配，缩放后的图片宽高，一定要大于等于控件的宽高
        float scale = Math.max(width * 1.0f / dWidth, height * 1.0f / dHeight);

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap((int) (dWidth * scale), (int) (dHeight * scale), config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, (int) (dWidth * scale), (int) (dHeight * scale));
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        clearBitmap();
    }

    private void clearBitmap() {
        if (bitmap != null) {
            bitmap.clear();
        }
        if (bitmapClip != null) {
            bitmapClip.clear();
        }
        if (rectF != null){
            rectF = null;
        }
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        clearBitmap();

    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        clearBitmap();
    }


}

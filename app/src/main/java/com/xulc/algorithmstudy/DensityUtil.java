package com.xulc.algorithmstudy;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DensityUtil {
    private static DensityUtil densityUtil;
    /**
     * UI图分辨率
     */
    private static float width = 750.0f;
    private static float height = 1134.0f;

    /**
     * 屏幕的分辨率信息
     */
    private static int screenW;
    private static int screenH;
    private static float density;
    private static float scaledDensity;
    public static DensityUtil getInstance(){
        if (densityUtil == null){
            synchronized (DensityUtil.class){
                if (densityUtil == null){
                    densityUtil = new DensityUtil();
                    if (screenW == 0 || screenH == 0) {
                        DisplayMetrics metrics = MyApplication.getContext().getResources().getDisplayMetrics();
                        screenW = metrics.widthPixels;
                        screenH = metrics.heightPixels;
                        density = metrics.density;
                        scaledDensity = metrics.scaledDensity;
                    }
                }
            }
        }
        return densityUtil;
    }

    public int getScreenW(){
        return screenW;
    }

    public int getScreenH() {
        return screenH;
    }

    /**
     * 计算宽比例
     * @return
     */
    private  float getRateOfWidth(){
        return screenW/width;
    }

    /**
     * 计算高比例
     * @return
     */
    private  float getRateOfHeight(){
        return screenH/height;
    }

    /**
     * 得到计算后的宽
     * @param width 效果图中宽
     * @return
     */
    public  int getRateWidth(int width){
        if (width== RelativeLayout.LayoutParams.WRAP_CONTENT||width== RelativeLayout.LayoutParams.MATCH_PARENT){
            return width;
        }
        return (int) (width*getRateOfWidth());
    }

    /**
     * 得到计算后的高
     * @param height 效果图中高
     * @return
     */
    public  int getRateHeight(int height){
        if (height == RelativeLayout.LayoutParams.WRAP_CONTENT||height== RelativeLayout.LayoutParams.MATCH_PARENT){
            return height;
        }
        return (int) (height*getRateOfHeight());
    }

    /**
     * 重设view的宽高
     * @param view 控件
     * @param width 效果图中宽
     * @param height 效果图中高
     */
    public void setViewParms(View view, int width, int height){
        ViewGroup.LayoutParams params  = view.getLayoutParams();
        params.width = getRateWidth(width);
        params.height =  getRateHeight(height);
        view.setLayoutParams(params);
    }

    /**
     * 重设view的高度
     * @param view
     * @param height
     */
    public void setViewHeight(View view, int height){
        ViewGroup.LayoutParams params  = view.getLayoutParams();
        params.height =  getRateHeight(height);
        view.setLayoutParams(params);
    }

    /**
     * 重设view的宽度
     * @param view
     * @param width
     */
    public  void setViewWidth(View view, int width){
        ViewGroup.LayoutParams params  = view.getLayoutParams();
        params.width =  getRateWidth(width);
        view.setLayoutParams(params);
    }


    /**
     * 重设view的margin值
     * @param view 控件
     * @param left 距离左
     * @param top 距离上
     * @param right 距离右
     * @param bottom 距离下
     */
    public void setViewMargin(View view, int left, int top, int right, int bottom){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof RelativeLayout.LayoutParams){
            ((RelativeLayout.LayoutParams)params).setMargins(getRateWidth(left),getRateHeight(top),getRateWidth(right),getRateHeight(bottom));
        }else if (params instanceof LinearLayout.LayoutParams){
            ((LinearLayout.LayoutParams)params).setMargins(getRateWidth(left),getRateHeight(top),getRateWidth(right),getRateHeight(bottom));
        }else if (params instanceof FrameLayout.LayoutParams){
            ((FrameLayout.LayoutParams)params).setMargins(getRateWidth(left),getRateHeight(top),getRateWidth(right),getRateHeight(bottom));
        }
        view.setLayoutParams(params);

    }

    public void setViewPadding(View view, int left, int top, int right, int bottom) {
        view.setPadding(left,top,right,bottom);
    }

    /**
     * 设计图尺寸px
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setViewPaddingDesire(View view, int left, int top, int right, int bottom) {
        view.setPadding(getRateWidth(left),getRateHeight(top),getRateWidth(right),getRateHeight(bottom));
    }
	  
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f);  
    }
}  

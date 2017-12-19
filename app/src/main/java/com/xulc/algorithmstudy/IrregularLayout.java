package com.xulc.algorithmstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Date：2017/12/19
 * Desc：自定义一个ViewGroup 实现子View自动换行排列
 * Created by xuliangchun.
 */

public class IrregularLayout extends ViewGroup{
    private float itemHorSpace;//子view横向间距
    private float itemVerSpace;//子view纵向间距
    public IrregularLayout(Context context) {
        this(context,null);
    }

    public IrregularLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.IrregularLayout);
        itemHorSpace = a.getDimension(R.styleable.IrregularLayout_ItemHorSpace,10);
        itemVerSpace = a.getDimension(R.styleable.IrregularLayout_ItemVerSpace,20);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int childRowWidth = 0;//当前布置行的child已经占了多少宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int requireHeight = 0;//根据child计算出来的高度

        switch (MeasureSpec.getMode(heightMeasureSpec)){
            case MeasureSpec.AT_MOST:
                for (int i=0;i<getChildCount();i++){
                    View child = getChildAt(i);
                    if (childRowWidth+child.getMeasuredWidth()>width){
                        childRowWidth = 0;
                        requireHeight += child.getMeasuredHeight()+itemVerSpace;//后面再补第一行的高度
                    }
                    childRowWidth += child.getMeasuredWidth()+itemHorSpace;
                }
                break;
        }
        if (getChildCount()>0){
            requireHeight = requireHeight + getChildAt(0).getMeasuredHeight();//这里加一个第一行的高度
            setMeasuredDimension(width,(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) ? height : requireHeight);
        }else {
            setMeasuredDimension(width,0);
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getMeasuredWidth();
        int childRow = 1;//当前正在布置第几行的child
        int childRowWidth = 0;//当前布置行的child已经占了多少宽度
        for (int i=0;i<getChildCount();i++){
            View child = getChildAt(i);
            if (childRowWidth+child.getMeasuredWidth()>width){
                childRowWidth = 0;
                childRow++;
            }
            int childLeft = childRowWidth;
            int childTop = (int) ((childRow-1)*(child.getMeasuredHeight()+itemVerSpace));
            int childRight = childLeft+child.getMeasuredWidth();
            int childBottom = childTop + child.getMeasuredHeight();
            child.layout(childLeft,childTop,childRight,childBottom);
            childRowWidth += child.getMeasuredWidth()+itemHorSpace;
        }
    }
}

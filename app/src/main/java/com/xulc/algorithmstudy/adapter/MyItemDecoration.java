package com.xulc.algorithmstudy.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Date：2018/3/27
 * Desc：
 * Created by xuliangchun.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration{
    private Paint mPaint;
    private int dividerHeight = 10;
    private int leftDivider = 20;

    public MyItemDecoration() {
        this.mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getLayoutManager() instanceof GridLayoutManager){
            outRect.bottom = dividerHeight;
            if (parent.getChildAdapterPosition(view)%2==1){
                outRect.right = leftDivider;
                outRect.left = leftDivider/2;
            }else {
                outRect.left = leftDivider;
                outRect.right = leftDivider/2;
            }

        }else if (parent.getLayoutManager() instanceof LinearLayoutManager){
            outRect.bottom = dividerHeight;
            outRect.left = 20;
            if (parent.getChildAdapterPosition(view)==0){
                outRect.top = dividerHeight;
            }
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getLayoutManager() instanceof GridLayoutManager){
            for (int i=0;i<parent.getChildCount();i++){
                View view = parent.getChildAt(i);

                if (i%2==0){
                    mPaint.setColor(Color.RED);
                    c.drawRect(parent.getPaddingLeft(),view.getTop(),view.getLeft(),view.getBottom()+dividerHeight,mPaint);

                    mPaint.setColor(Color.BLUE);
                    c.drawRect(view.getLeft(),view.getBottom(),view.getRight()+leftDivider/2,view.getBottom()+dividerHeight,mPaint);

                    mPaint.setColor(Color.BLACK);
                    c.drawRect(view.getRight(),view.getTop(),view.getRight()+leftDivider/2,view.getBottom(),mPaint);

                }else {
                    mPaint.setColor(Color.RED);
                    c.drawRect(view.getRight(),view.getTop(),parent.getWidth()-parent.getPaddingRight(),view.getBottom()+dividerHeight,mPaint);

                    mPaint.setColor(Color.BLACK);
                    c.drawRect(view.getLeft()-leftDivider/2,view.getBottom(),view.getRight(),view.getBottom()+dividerHeight,mPaint);

                    mPaint.setColor(Color.BLUE);
                    c.drawRect(view.getLeft()-leftDivider/2,view.getTop(),view.getLeft(),view.getBottom(),mPaint);

                }
            }
        }else if (parent.getLayoutManager() instanceof LinearLayoutManager){
            for(int i=0;i<parent.getChildCount();i++){
                View view = parent.getChildAt(i);
                Log.i("xlc",String.format("view.getLeft()=%s+parent.getPaddingLeft()=%s+i=%s",view.getLeft(),parent.getPaddingLeft(),i));
                if (i%2==0){
                    mPaint.setColor(Color.BLACK);

                    c.drawRect(parent.getPaddingLeft(),view.getBottom(),parent.getWidth()-parent.getPaddingRight(),view.getBottom()+dividerHeight,mPaint);
                }else {
                    mPaint.setColor(Color.RED);

                    c.drawRect(parent.getPaddingLeft(),view.getBottom(),parent.getWidth()-parent.getPaddingRight(),view.getBottom()+dividerHeight,mPaint);

                }
            }
        }

    }


}

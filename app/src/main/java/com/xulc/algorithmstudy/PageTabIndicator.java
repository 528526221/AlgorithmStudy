package com.xulc.algorithmstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/1/2
 * Desc：Tab 指示器（for ViewPager）
 * Created by xuliangchun.
 */

public class PageTabIndicator extends RelativeLayout{
    private Context mContext;
    private int mOffsetX;//偏移量
    private Paint mPaint;
    private int indicatorLineHeight = 10;//指示器横线的高度
    private boolean isFixedTabQuantity = true;//tab数量是否固定数量，固定意味着平分屏幕宽度，否则意味着容器的宽度是自适应的
    private boolean isIndicatorLineFillWidthWithTab = false;//指示器横线是否宽度填充满tab
    private int indicatorLineLeftRightPadding = 20;//在isIndicatorLineFillWidthWithTab为false的情况下，指示器横线的宽度要依据title的实际宽度来确定
    private int twoTabDistance = 0;//两个tab之间的距离，默认无间距
    private int tabCount;//tab数量
    private List<String> titles = new ArrayList<>();
    private int currentPosition;
    private int[] tabWidthArray;//tab宽度数组
    private int[] tabTitleWidthArray;//tab中title文本的宽度数组
    public PageTabIndicator(Context context) {
        this(context,null);
    }

    public PageTabIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(indicatorLineHeight);
        //注意隐含条件  若isIndicatorLineFillWidthWithTab为true，则isFixedTabQuantity 必为true
        if (isIndicatorLineFillWidthWithTab && !isFixedTabQuantity){
            throw new RuntimeException("若isIndicatorLineFillWidthWithTab为true，则isFixedTabQuantity 必为true");
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.translate(mOffsetX,0);
        if (isInEditMode()){
            canvas.drawLine(0,getHeight()-indicatorLineHeight/2,getWidth()/2,getHeight()-indicatorLineHeight/2,mPaint);
        }else {
            if (isFixedTabQuantity && isIndicatorLineFillWidthWithTab){
                canvas.drawLine(0,getHeight()-indicatorLineHeight/2,(getWidth()-(tabCount-1)*twoTabDistance)/tabCount,getHeight()-indicatorLineHeight/2,mPaint);
            }else {
                //自适应的话 stopX应该为文本宽度+左右padding
                canvas.drawLine(0,getHeight()-indicatorLineHeight/2,getChildAt(currentPosition).getWidth()+2*indicatorLineLeftRightPadding,getHeight()-indicatorLineHeight/2,mPaint);
            }
        }


    }

    /**
     * 设置指示器title
     * @param titles
     */
    public void setTitles(final List<String> titles) {
        this.titles = titles;
        this.tabCount = titles.size();
        this.tabWidthArray = new int[titles.size()];
        this.tabTitleWidthArray = new int[titles.size()];

        this.post(new Runnable() {
            @Override
            public void run() {
                removeAllViews();
                for (int i=0;i<titles.size();i++){
                    TextView textView = new TextView(mContext);
                    textView.setText(titles.get(i));
                    //添加文本时要控制添加的位置
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                    if (isFixedTabQuantity){
                        //从第2个开始，才需要距离
                        if (i>=1){
                            layoutParams.leftMargin = (i)*((getWidth()-(tabCount-1)*twoTabDistance)/tabCount)+(i-1)*twoTabDistance;
                        }
                        //为了文本能够横向在每个tab中居中，需要增加偏移量
                        layoutParams.leftMargin += tabWidthArray[i];
                    }
                    addView(textView,layoutParams);

                }
            }
        });

    }

    /**
     * 开始滑动切换ViewPager
     * @param position
     * @param positionOffset
     */
    public void startScroll(final int position, final float positionOffset) {
        if (tabCount<=position) throw new RuntimeException("Tab titles count can not be less than viewPager counts!");
        this.currentPosition = position;
        this.post(new Runnable() {
            @Override
            public void run() {
                //分固定平分还是自适应宽度 计算偏移量的方式不同
                if (isFixedTabQuantity){
                    mOffsetX = (int) ((position+positionOffset)*((getWidth()-(tabCount-1)*twoTabDistance)/tabCount));
                    mOffsetX += (position-1)*twoTabDistance;
                    //如果是在指示器宽度不填充满的情况下，我们还需要增加偏移才能使得指示器居中
                    if (!isIndicatorLineFillWidthWithTab){
                        mOffsetX = mOffsetX + ((getWidth()-(tabCount-1)*twoTabDistance)/tabCount-(getChildAt(currentPosition).getWidth()+2*indicatorLineLeftRightPadding))/2;
                    }
                }else {
                    if (position>0){
                        mOffsetX = 0;
                        for (int i=0;i<position;i++){
                            mOffsetX += getChildAt(i).getWidth()+2*indicatorLineLeftRightPadding;
                            if (i!=position-1){
                                mOffsetX += twoTabDistance;
                            }
                        }
                    }else {
                        mOffsetX = (int) (positionOffset*getChildAt(0).getWidth()+twoTabDistance);
                    }
                }
                invalidate();
            }
        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isFixedTabQuantity){

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getChildCount()>=titles.size()){
            for (int i=0;i<titles.size();i++){
                tabTitleWidthArray[i] = getChildAt(i).getWidth();
                if (isFixedTabQuantity){
                    tabWidthArray[i] = (getWidth()-(tabCount-1)*twoTabDistance)/tabCount;
                }else {
                    tabWidthArray[i] = tabTitleWidthArray[i]+2*indicatorLineLeftRightPadding;
                }
            }
        }



    }
}

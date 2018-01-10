package com.xulc.algorithmstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
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
    private Paint mPaint;
    //下面是自定义属性
    private int indicatorTextColor;//指示器标题文字颜色
    private float indicatorTextSize;//指示器标题文字大小
    private int indicatorLineColor = Color.RED;//指示器横线的颜色
    private float indicatorLineHeight;//指示器横线的高度
    private boolean indicatorAverageScreen;//true则表示tab平分屏幕宽度，否则意味着容器的宽度是自适应的
    private boolean indicatorFillWidthWithTab;//指示器横线是否宽度填充满tab
    private float indicatorLineLeftRightPadding;//在indicatorFillWidthWithTab为false的情况下，指示器横线的宽度要依据title的实际宽度来确定
    private float indicatorTwoTabDistance;//两个tab之间的距离，默认无间距
    private int mOffsetX;//偏移量
    private int tabCount;//tab数量
    private List<String> titles = new ArrayList<>();
    private int currentPosition;
    private Float[] tabWidthArray;//tab宽度数组
    private Float[] tabTitleWidthArray;//tab中title文本的宽度数组
    private String color[] = {"#3343a954","#33b68c32"};
    private Scroller mScroller;

    private int mLastMoveX;//滑动时X的上次值
    private int mDownX;//按下时的x
    private ViewPager mViewPager;//关联的ViewPager
    private int indicatorLayoutWidth;//在布局中提供给指示器的宽度值，用于touch时判断是否需要滑动
    private boolean isNeedDrawTabBackGround = true;


    public PageTabIndicator(Context context) {
        this(context,null);
    }

    public PageTabIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mScroller = new Scroller(context, new LinearInterpolator());

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.PageTabIndicator);
        indicatorTextColor = a.getColor(R.styleable.PageTabIndicator_IndicatorTextColor,Color.RED);
        indicatorTextSize = a.getDimension(R.styleable.PageTabIndicator_IndicatorTextSize,28F);
        indicatorLineColor = a.getColor(R.styleable.PageTabIndicator_IndicatorLineColor,Color.RED);
        indicatorLineHeight = a.getDimension(R.styleable.PageTabIndicator_IndicatorLineHeight,8F);
        indicatorAverageScreen = a.getBoolean(R.styleable.PageTabIndicator_IndicatorAverageScreen,true);
        indicatorFillWidthWithTab = a.getBoolean(R.styleable.PageTabIndicator_IndicatorFillWidthWithTab,true);
        indicatorLineLeftRightPadding = a.getDimension(R.styleable.PageTabIndicator_IndicatorLeftRightPadding,0);
        indicatorTwoTabDistance = a.getDimension(R.styleable.PageTabIndicator_IndicatorTwoTabDistance,0);
        a.recycle();

        mPaint = new Paint();
        mPaint.setColor(indicatorLineColor);
        mPaint.setStrokeWidth(indicatorLineHeight);
        mPaint.setTextSize(indicatorTextSize);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //注意隐含条件  若tab不是等分的话，anemia指示器不可能填充满tab
        if (!indicatorAverageScreen){
            indicatorFillWidthWithTab = false;
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!isInEditMode()&&isNeedDrawTabBackGround){
            //先画出每个tab的背景色
            mPaint.setStrokeWidth(0);

            int start = 0;
            for (int i=0;i<tabWidthArray.length;i++){
                if (i%2==0){
                    mPaint.setColor(Color.parseColor(color[0]));
                }else {
                    mPaint.setColor(Color.parseColor(color[1]));
                }

                canvas.drawRect(new Rect(start,0, (int) (start+tabWidthArray[i]),getHeight()),mPaint);
                start += tabWidthArray[i]+indicatorTwoTabDistance;
            }
            mPaint.setColor(indicatorLineColor);
            mPaint.setStrokeWidth(indicatorLineHeight);
        }

        //画布平移
        canvas.translate(mOffsetX,0);
        if (isInEditMode()){
            //伪绘制
            canvas.drawLine(0,getHeight()-indicatorLineHeight/2,getWidth()/2,getHeight()-indicatorLineHeight/2,mPaint);
        }else {
            if (indicatorFillWidthWithTab){
                canvas.drawLine(0,getHeight()-indicatorLineHeight/2,tabWidthArray[currentPosition],getHeight()-indicatorLineHeight/2,mPaint);
            }else {
                //自适应的话 stopX应该为文本宽度+左右padding
                canvas.drawLine(0,getHeight()-indicatorLineHeight/2,tabTitleWidthArray[currentPosition]+2*indicatorLineLeftRightPadding,getHeight()-indicatorLineHeight/2,mPaint);
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
        this.tabWidthArray = new Float[titles.size()];
        this.tabTitleWidthArray = new Float[titles.size()];
        this.post(new Runnable() {
            @Override
            public void run() {
                removeAllViews();

                calculationTabAndTitleWidth();

                //把title作为子view添加到布局中

                for (int i=0;i<titles.size();i++){
                    final TextView textView = new TextView(mContext);
                    textView.setText(titles.get(i));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, indicatorTextSize);
                    textView.setTextColor(indicatorTextColor);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                    //添加文本时要控制添加的位置
                    if (indicatorAverageScreen){
                        layoutParams.leftMargin = (int) (i*(tabWidthArray[i]+indicatorTwoTabDistance));
                    }else {
                        layoutParams.leftMargin = 0;
                        for (int j=0;j<i;j++){
                            layoutParams.leftMargin += tabWidthArray[j];
                            if (j!=tabWidthArray.length-1){
                                layoutParams.leftMargin += indicatorTwoTabDistance;
                            }
                        }
                    }
                    //为了文本能够横向在每个tab中居中，需要增加tab宽度减去文本宽度的一半的偏移量
                    layoutParams.leftMargin += (tabWidthArray[i]-tabTitleWidthArray[i])/2;
                    addView(textView,layoutParams);

                }
            }
        });
    }

    /**
     * 计算并存储tab的宽度和title的宽度
     */
    private void calculationTabAndTitleWidth() {
        Log.d("xlc","calculationTabAndTitleWidth");
        //把指示器的每个tab的width和每个title的width存储到数组中，便于使用
        for (int i=0;i<titles.size();i++){
            tabTitleWidthArray[i] = mPaint.measureText(titles.get(i));
            if (indicatorAverageScreen){
                tabWidthArray[i] = (getWidth()-(tabCount-1)* indicatorTwoTabDistance)/tabCount;
            }else {
                tabWidthArray[i] = tabTitleWidthArray[i]+2*indicatorLineLeftRightPadding;
            }
        }
    }

    /**
     * 开始滑动切换ViewPager
     * @param position
     * @param positionOffset
     */
    public void startIndicatorOffset(final int position, final float positionOffset) {
        if (tabCount<=position) throw new RuntimeException("Tab titles count can not be less than viewPager counts!");
        this.currentPosition = position;
        this.post(new Runnable() {
            @Override
            public void run() {
                //分固定平分还是自适应宽度 计算偏移量的方式不同
                if (indicatorAverageScreen){
                    mOffsetX = (int) ((position+positionOffset)*(tabWidthArray[currentPosition]+indicatorTwoTabDistance));
                    //如果是在指示器宽度不填充满的情况下，我们还需要增加偏移才能使得指示器居中
                    if (!indicatorFillWidthWithTab){
                        mOffsetX = (int) (mOffsetX + (tabWidthArray[currentPosition]-(tabTitleWidthArray[currentPosition]+2*indicatorLineLeftRightPadding))/2);
                    }
                }else {
                    mOffsetX = 0;
                    for (int i=0;i<position;i++){
                        mOffsetX += tabTitleWidthArray[i]+2*indicatorLineLeftRightPadding+indicatorTwoTabDistance;
                    }
                    mOffsetX += positionOffset*(tabTitleWidthArray[position]+2*indicatorLineLeftRightPadding+indicatorTwoTabDistance);
                }
                invalidate();
            }
        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        indicatorLayoutWidth = width;
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (!indicatorAverageScreen && tabWidthArray!=null){
            //非均分屏幕宽度的情况下，宽度需要自己计算
            width = 0;
            for (int i=0;i<tabWidthArray.length;i++){
                if (tabWidthArray[0]==null){
                    //此时tab宽度值还没有保存，先计算保存
                    calculationTabAndTitleWidth();
                }
                width += tabWidthArray[i];
                if (i!=tabWidthArray.length-1){
                    width += indicatorTwoTabDistance;
                }
            }
        }
        setMeasuredDimension(width,height);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastMoveX = mDownX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                //如果还没有屏幕宽
                if (getWidth()>indicatorLayoutWidth){
                    startScroll(mLastMoveX-x,null);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(mDownX-x)<10){
                    //响应点击区域  相对view的坐标加上已经滚动的位移
                    responseOnClick(mDownX+getScrollX());
                }
                break;
        }
        mLastMoveX = x;
        return true;
    }

    /**
     * 滚动容器
     * @param dx
     * @param scroller 如果这个参数不为null 就使用scroller缓慢滚动
     * getWidth()是内容实际宽度
     * indicatorLayoutWidth是布局宽度
     */
    private void startScroll(int dx,Scroller scroller) {
        //这里注意，真正能滑动的最大距离不是控件的宽度，而是控件宽减去提供给它的宽度
        if (getScrollX()+dx>getWidth()-indicatorLayoutWidth){
            if (scroller!=null){
                scroller.startScroll(getScrollX(),0,getWidth()-indicatorLayoutWidth-getScrollX(),0);
            }else {
                scrollTo(getWidth()-indicatorLayoutWidth,0);
            }
        }else if (getScrollX()+dx<0){
            if (scroller!=null){
                scroller.startScroll(getScrollX(),0,-getScrollX(),0);
            }else {
                scrollTo(0,0);
            }
        }else {
            if (scroller!=null){
                scroller.startScroll(getScrollX(),0,dx,0);
            }else {
                scrollBy(dx,0);
            }
        }
    }



    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 设置关联的ViewPager
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager){
        this.mViewPager = viewPager;
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                startIndicatorOffset(position,positionOffset);
                Log.d("xlc","onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("xlc","onPageSelected");
                //滚动当前选中的tab到屏幕中央区域
                //计算当前tab的中心点位置x  多做一步操作，将点击的tab滚动到屏幕中央
                //如果还没有屏幕宽 那当然就不需滚动了
                if (getWidth()>indicatorLayoutWidth){
                    int dx = 0;
                    for (int i=0;i<tabWidthArray.length;i++){
                        if (i<position){
                            dx += tabWidthArray[i]+indicatorTwoTabDistance;
                        }else if (i == position){
                            dx += tabWidthArray[i]/2;
                            break;
                        }
                    }
                    //虚拟计算点与理论上屏幕中央点的差值 利用scroller缓慢滑动
//                    dx = dx-(getScrollX()+indicatorLayoutWidth/2);
                     startScroll(dx-(getScrollX()+indicatorLayoutWidth/2),mScroller);

//                    if (getScrollX()+dx>getWidth()-indicatorLayoutWidth){
////                    scrollTo(getWidth()-indicatorLayoutWidth,0);
//                        mScroller.startScroll(getScrollX(),0,getWidth()-indicatorLayoutWidth-getScrollX(),0);
//
//                    }else if (getScrollX()+dx<0){
////                    scrollTo(0,0);
//                        mScroller.startScroll(getScrollX(),0,-getScrollX(),0);
//
//                    }else {
////                    scrollBy(dx,0);
//                        mScroller.startScroll(getScrollX(),0,dx,0);
//                    }
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 根据点击区域确定点击的是哪个tab
     * @param mDownX
     */
    private void responseOnClick(int mDownX) {
        int clickPosition = -1;
        if (indicatorAverageScreen){
            //处理tab等宽
            clickPosition = (int) (mDownX/(tabWidthArray[0]+indicatorTwoTabDistance));
        }else {
            //处理tab自适应宽度时候的点击
            int width = 0;
            for (int i=0;i<tabWidthArray.length;i++){
                width += tabWidthArray[i];
                if (width>mDownX){
                    clickPosition = i;
                    break;
                }
                width += indicatorTwoTabDistance;
            }
        }

        if (mViewPager!=null && clickPosition!=-1)
            mViewPager.setCurrentItem(clickPosition);
    }

    /**
     * 设置指示器横线是否宽度填充满tab
     * @param indicatorFillWidthWithTab
     */
    public void setIndicatorFillWidthWithTab(boolean indicatorFillWidthWithTab) {
        this.indicatorFillWidthWithTab = indicatorFillWidthWithTab;
    }

    public boolean isIndicatorFillWidthWithTab() {
        return indicatorFillWidthWithTab;
    }

    public boolean isIndicatorAverageScreen() {
        return indicatorAverageScreen;
    }

    public void setIndicatorAverageScreen(boolean indicatorAverageScreen) {
        this.indicatorAverageScreen = indicatorAverageScreen;
    }
}

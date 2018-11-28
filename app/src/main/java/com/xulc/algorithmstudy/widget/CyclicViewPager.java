package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;



/**
 * Date：2018/1/30
 * Desc：改造ViewPager使其支持任意个数的View循环
 * Created by xuliangchun.
 */

public class CyclicViewPager<T> extends ViewPager {
    private List<View> viewList = new ArrayList<>();//视图View的集合
    private int pageIndex;
    private CyclicPageChangeListener cyclicPageChangeListener;//页面监听
    private Handler handler;//自动滚动Handler
    private boolean isAutoScroll = false;//自动轮播
    private final int MSG_AUTO_SCROLL = 0;//滚动的Msg
    private Long autoScrollInterval = 3000L;//轮播默认间隔时长
    private SlowScroller mSlowScroller;
    private boolean singleViewCyclic = true;//单张是否需要循环，默认是
    public CyclicViewPager(Context context) {
        this(context,null);
    }

    public CyclicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSlowScroller = new SlowScroller(context,new LinearInterpolator());
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this,mSlowScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (cyclicPageChangeListener != null){
                    cyclicPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                //如果在这里调用setCurrentItem方法会强制取消当前正在进行的动画并跳转
                pageIndex = position;
                if (cyclicPageChangeListener != null){
                    //应该要返回实际的数据源position
                    if (position == 0){
                        cyclicPageChangeListener.onPageSelected(viewList.size()-3);
                    }else if (position == viewList.size()-1){
                        cyclicPageChangeListener.onPageSelected(0);
                    }else {
                        cyclicPageChangeListener.onPageSelected(position-1);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (cyclicPageChangeListener != null){
                    cyclicPageChangeListener.onPageScrollStateChanged(state);
                }
                //之所以处理过程放在该方法而没有放在onPageSelected 就是解决滑动到第一页以及最后一页时出现的不友好问题
                //若viewpager滑动未停止，直接返回
                if (state != ViewPager.SCROLL_STATE_IDLE)
                    return;
                if (pageIndex == 0){
                    //到了第1张 就把页面设置成倒数第2张
                    pageIndex = viewList.size()-2;
                    setCurrentItem(pageIndex,false);
                }else if (pageIndex == viewList.size()-1){
                    //到了倒数第1张，就把页面设置成第2张
                    pageIndex = 1;
                    setCurrentItem(pageIndex,false);
                }


            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (isAutoScroll){
                    handler.removeMessages(MSG_AUTO_SCROLL);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isAutoScroll){
                    sendScrollMessage();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置监听
     * @param cyclicPageChangeListener
     */
    public void setCyclicPageChangeListener(CyclicPageChangeListener cyclicPageChangeListener) {
        this.cyclicPageChangeListener = cyclicPageChangeListener;
    }

    /**
     * 设置数据源
     * @param tList 泛型数据集合
     * @param listener
     */
    public void setCyclicModels(List<T> tList, OnCreateViewListener<T> listener){
        if (tList.size() == 0){
            return;
        }
        LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
        this.viewList.clear();
        for (int i=0;i<tList.size();i++){
            this.viewList.add(listener.createChildView(tList,i,mLayoutInflater));
        }
        if (tList.size() == 1 && !singleViewCyclic){
            ViewPagerAdapter adapter = new ViewPagerAdapter();
            this.setAdapter(adapter);
            this.setCurrentItem(0);
        }else {
            //为了循环的话 在第0张前面增加一个最后一张，在最后一张后面增加一个第1张
            //目前发现的第三方少于2张不能正常显示，这样添加的目的是为了创建新的child view
            this.viewList.add(0,listener.createChildView(tList,tList.size()-1,mLayoutInflater));
            this.viewList.add(listener.createChildView(tList,0,mLayoutInflater));
            ViewPagerAdapter adapter = new ViewPagerAdapter();
            this.setAdapter(adapter);
            this.setCurrentItem(1);
        }

    }

    /**
     * 为了满足测试的BT要求 单张不必要循环
     * @param singleViewCyclic
     * @param tList
     * @param listener
     */
    public void setCyclicModels(boolean singleViewCyclic,List<T> tList, OnCreateViewListener<T> listener){
        this.singleViewCyclic = singleViewCyclic;
        setCyclicModels(tList,listener);
    }


    /**
     * 通过该接口把child view交给控制层去实现
     * @param <T>
     */
    public interface OnCreateViewListener<T>{
        View createChildView(List<T> tList, int position,LayoutInflater mLayoutInflater) ;
    }

    /**
     * 该接口代替系统本身的OnPageChangeListener监听
     */
    public interface CyclicPageChangeListener{
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);

    }

    /**
     * 开始自动轮播
     */
    public void startAutoScroll() {
        isAutoScroll = true;
        if (handler == null){
            this.handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case MSG_AUTO_SCROLL:
                            if (pageIndex>=viewList.size()-1){
                                setCurrentItem(0);
                            }else {
                                setCurrentItem(pageIndex+1);
                            }
                            sendScrollMessage();
                            break;
                    }
                }
            };
        }
        sendScrollMessage();
    }


    /**
     * 停止轮播
     */
    private void stopAutoScroll() {
        if (handler != null)
            handler.removeMessages(MSG_AUTO_SCROLL);
        handler = null;
    }

    /**
     * 发送轮播消息
     */
    private void sendScrollMessage() {
        if (handler != null){
            handler.removeMessages(MSG_AUTO_SCROLL);
            handler.sendEmptyMessageDelayed(MSG_AUTO_SCROLL, autoScrollInterval);
        }
    }

    /**
     *设置自动轮播间隔
     * @param autoScrollInterval
     */
    public void setAutoScrollInterval(Long autoScrollInterval) {
        this.autoScrollInterval = autoScrollInterval;
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = viewList.get(position);
            ViewParent parent = view.getParent();
            if (parent!=null){
                ((ViewGroup)parent).removeView(view);
            }
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }

    /**
     * 调节下切换界面时的速度 0.5秒吧
     */
    private class SlowScroller  extends Scroller {

        public SlowScroller(Context context) {
            super(context);
        }

        public SlowScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public SlowScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 300);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy,300);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoScroll();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isAutoScroll){
            startAutoScroll();
        }
    }
}

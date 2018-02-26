package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Date：2018/1/17
 * Desc：目前只知道默认的down-move-up都会先走父容器的dispatchTouchEvent以及onInterceptTouchEvent
 * 但是像listview这种滑动控件里面可能做了什么操作使得父容器针对move和up操作不会拦截
 * 想要完全保持拦截的话，空实现requestDisallowInterceptTouchEvent方法就好了
 * down事件没有人消费的话 那么move 和 up都不会传递了
 * Created by xuliangchun.
 */

public class TouchDispatchLinearLayout extends LinearLayout{

    private static final String TAG = "xlc";

    public TouchDispatchLinearLayout(Context context) {
        super(context);
    }

    public TouchDispatchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchDispatchLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "ViewGroup dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "ViewGroup dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "ViewGroup dispatchTouchEvent ACTION_UP");
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "ViewGroup onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "ViewGroup onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "ViewGroup onInterceptTouchEvent ACTION_UP");
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "ViewGroup onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "ViewGroup onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "ViewGroup onTouchEvent ACTION_UP");
                break;
        }

        return super.onTouchEvent(event);
    }
}

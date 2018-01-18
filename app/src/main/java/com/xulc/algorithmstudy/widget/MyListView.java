package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Date：2018/1/18
 * Desc：
 * Created by xuliangchun.
 */

public class MyListView extends ListView{
    private static final String TAG = "xlc";

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "MyListView dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "MyListView dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "MyListView dispatchTouchEvent ACTION_UP");
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "MyListView onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "MyListView onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "MyListView onInterceptTouchEvent ACTION_UP");
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "MyListView onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "MyListView onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "MyListView onTouchEvent ACTION_UP");
                break;
        }

        return super.onTouchEvent(event);
    }
}

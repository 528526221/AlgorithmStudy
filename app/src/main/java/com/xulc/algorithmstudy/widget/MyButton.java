package com.xulc.algorithmstudy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Date：2018/1/18
 * Desc：
 * Created by xuliangchun.
 */
public class MyButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "xlc";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "View      dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "View      dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "View      dispatchTouchEvent ACTION_UP");
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "View      onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "View      onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "View      onTouchEvent ACTION_UP");
                break;
        }

        return true;
    }
}

package com.xulc.algorithmstudy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;

import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.widget.MyListView;

/**
 * Date：2018/1/17
 * Desc：事件分发
 * Created by xuliangchun.
 */

public class StudyTouchDispatchActivity extends BaseActivity{
    private static final String TAG = "xlc";
    private MyListView myListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_touch_dispatch);
        myListView = (MyListView) findViewById(R.id.myListView);
        //要显示的数据
        String[] strs = new String[20];
        for (int i=0;i<strs.length;i++){
            strs[i] = "数据"+i;
        }
        //创建ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_expandable_list_item_1,strs);
        myListView.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "StudyTouchDispatchActivity onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "StudyTouchDispatchActivity onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "StudyTouchDispatchActivity onTouchEvent ACTION_UP");
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下的动作
                Log.i(TAG, "StudyTouchDispatchActivity dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: //滑动的动作
                Log.i(TAG, "StudyTouchDispatchActivity dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP: //离开的动作
                Log.i(TAG, "StudyTouchDispatchActivity dispatchTouchEvent ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}

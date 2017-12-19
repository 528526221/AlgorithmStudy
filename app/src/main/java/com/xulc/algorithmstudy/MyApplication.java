package com.xulc.algorithmstudy;

import android.app.Application;
import android.content.Context;

/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}

package com.xulc.algorithmstudy;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

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
        CrashReport.initCrashReport(this, "828bf724a7", true);

    }

    public static Context getContext() {
        return mContext;
    }
}

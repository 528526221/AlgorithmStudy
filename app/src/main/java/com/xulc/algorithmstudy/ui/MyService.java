package com.xulc.algorithmstudy.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xulc.algorithmstudy.IMyInterface;


/**
 * Date：2018/3/14
 * Desc：
 * Created by xuliangchun.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("xlc","绑定");
        return binder;
    }

    private IBinder binder = new IMyInterface.Stub(){

        @Override
        public String getInfor(String s) throws RemoteException {
            Log.i("xulc",s);
            return "你好，"+s;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("xulc", "onCreate");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("xlc","解绑");

        return super.onUnbind(intent);
    }
}

package com.xulc.algorithmstudy.ui;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Date：2018/3/14
 * Desc：
 * Created by xuliangchun.
 */

public class YourService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new YourBinder();
    }



    class YourBinder extends Binder{
        public YourService getService(){
            return YourService.this;
        }
    }

    public String getMessage(String s){
        return "你好"+s;
    }

}

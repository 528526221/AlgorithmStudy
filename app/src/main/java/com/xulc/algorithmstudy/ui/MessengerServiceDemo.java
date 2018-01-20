package com.xulc.algorithmstudy.ui;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Date：2018/1/18
 * Desc：使用Messenger进行进程间通信
 * Created by xuliangchun.
 */

public class MessengerServiceDemo extends Service {
    static final int MSG_SAY_HELLO = 1;
    final Messenger messenger = new Messenger(new ServiceHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }


    class ServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(),msg.getData().getString("msg"),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}

package com.xulc.algorithmstudy.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xulc.algorithmstudy.IMyInterface;
import com.xulc.algorithmstudy.R;

/**
 * Date：2018/3/14
 * Desc：
 * Created by xuliangchun.
 */

public class AIDLActivity extends BaseActivity{
    private IMyInterface myInterface;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myInterface = IMyInterface.Stub.asInterface(service);
            try {
                String s = myInterface.getInfor("中国");
                Log.i("xulc", "从Service得到的字符串：" + s);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("xulc", "连接失败！！");

        }
    };

    private ServiceConnection serviceConnection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            YourService yourService = ((YourService.YourBinder) service).getService();
            String s = yourService.getMessage("朋友");
            Log.i("xulc", "从Service得到的字符串：" + s);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        bindService(new Intent(this,MyService.class),serviceConnection, Context.BIND_AUTO_CREATE);
//        bindService(new Intent(this,YourService.class),serviceConnection1, Context.BIND_AUTO_CREATE);

    }
}

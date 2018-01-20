package com.xulc.algorithmstudy.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xulc.algorithmstudy.R;

/**
 * Date：2018/1/18
 * Desc：使用Messenger进行进程间通信
 1.服务端实现一个Handler，由其接受来自客户端的每个调用的回调
 2.使用实现的Handler创建Messenger对象
 3.通过Messenger得到一个IBinder对象，并将其通过onBind()返回给客户端
 4.客户端使用 IBinder 将 Messenger（引用服务的 Handler）实例化，然后使用后者将 Message 对象发送给服务
 5.服务端在其 Handler 中（具体地讲，是在 handleMessage() 方法中）接收每个 Message
 * Created by xuliangchun.
 */

public class ActivityMessenger extends BaseActivity {
    static final int MSG_SAY_HELLO = 1;

    private TextView tvBindStatus;
    private EditText etMsg;
    private ServiceConnection mConnection;
    private Messenger messenger;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        tvBindStatus = (TextView) findViewById(R.id.tvBindStatus);
        etMsg = (EditText) findViewById(R.id.etMsg);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                messenger = new Messenger(service);
                tvBindStatus.setText("已经和服务绑定");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                messenger = null;
                tvBindStatus.setText("服务绑定断开");

            }
        };


    }

    public void startBind(View view) {
        //绑定服务端的服务，此处的action是service在Manifests文件里面声明的
        Intent intent = new Intent();
        intent.setAction("com.xulc.algorithmstudy");
        //不要忘记了包名，不写会报错
        intent.setPackage("com.xulc.algorithmstudy");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    public void sendMsg(View view) {
        if (messenger!=null){
            Message message = Message.obtain(null,MSG_SAY_HELLO,0,0);
            Bundle bundle = new Bundle();
            bundle.putString("msg",etMsg.getText().toString());
            message.setData(bundle);
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this,"服务没有绑定呢！",Toast.LENGTH_SHORT).show();
        }
    }
}

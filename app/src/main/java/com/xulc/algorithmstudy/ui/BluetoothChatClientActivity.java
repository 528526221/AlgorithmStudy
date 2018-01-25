package com.xulc.algorithmstudy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.adapter.BluetoothChatAdapter;
import com.xulc.algorithmstudy.model.ChatModel;
import com.xulc.algorithmstudy.util.BleConnectChatManager;

/**
 * Date：2018/1/24
 * Desc：蓝牙聊天客户端
 * Created by xuliangchun.
 */

public class BluetoothChatClientActivity extends BaseActivity implements View.OnClickListener, BleConnectChatManager.OnBleConnectListener {
    private Button btnStart;
    private ListView lvChat;
    private TextView tvCurrentStatus;
    private EditText etMsg;
    private TextView tvSend;
    private String deviceAddress;
    private BluetoothChatAdapter adapter;
    private int currentStatus = BleConnectChatManager.STATUS_DISCONNECT;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_chat_client);
        btnStart = (Button) findViewById(R.id.btnStart);
        lvChat = (ListView) findViewById(R.id.lvChat);
        tvCurrentStatus = (TextView) findViewById(R.id.tvCurrentStatus);
        etMsg = (EditText) findViewById(R.id.etMsg);
        tvSend = (TextView) findViewById(R.id.tvSend);
        btnStart.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        adapter = new BluetoothChatAdapter(this);
        lvChat.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        if (v == btnStart){
            if (currentStatus == BleConnectChatManager.STATUS_CONNECTED){
                BleConnectChatManager.getInstance().stopConnect();
            }else {
                if (TextUtils.isEmpty(deviceAddress)){
                    startActivityForResult(new Intent(this,BluetoothDeviceActivity.class),1);
                }else {
                    BleConnectChatManager.getInstance().createClient(deviceAddress,this);
                }
            }
        }else if (v == tvSend){
            if (TextUtils.isEmpty(etMsg.getText())){
                return;
            }
            BleConnectChatManager.getInstance().sendMessage(etMsg.getText().toString());
            etMsg.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            deviceAddress = data.getStringExtra("device_address");
            if (TextUtils.isEmpty(deviceAddress)){
                return;
            }
            BleConnectChatManager.getInstance().createClient(deviceAddress,this);
        }
    }

    @Override
    public void onAcceptMessage(byte[] bytes) {
        adapter.addData(new ChatModel(new String(bytes),false));
        lvChat.setSelection(adapter.getCount()-1);

    }

    @Override
    public void onSendMessage(byte[] bytes) {
        adapter.addData(new ChatModel(new String(bytes),true));
        lvChat.setSelection(adapter.getCount()-1);

    }

    @Override
    public void onConnectStatusChange(int status) {
        this.currentStatus = status;
        switch (currentStatus){
            case BleConnectChatManager.STATUS_DISCONNECT:
                tvCurrentStatus.setText("未连接");
                btnStart.setText("开始连接");
                break;
            case BleConnectChatManager.STATUS_CONNECTING:
                tvCurrentStatus.setText("正在连接...");
                btnStart.setText("正在连接...");

                break;
            case BleConnectChatManager.STATUS_CONNECT_FAILED:
                tvCurrentStatus.setText("连接失败");
                btnStart.setText("重新连接");
                break;
            case BleConnectChatManager.STATUS_CONNECTED:
                tvCurrentStatus.setText("已连接");
                btnStart.setText("断开连接");

                break;

        }
    }
}

package com.xulc.algorithmstudy.ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.xulc.algorithmstudy.R;

/**
 * Date：2018/1/24
 * Desc：学习蓝牙
 * Created by xuliangchun.
 */

public class StudyBluetoothActivity extends BaseActivity implements BaseActivity.PermissionCallBackListener {
    private static final int OPEN_BLUETOOTH = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean isReady;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_bluetooth);
        //1.获取BluetoothAdapter
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //2.检测蓝牙是否开启
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, OPEN_BLUETOOTH);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, this);
            } else {
                onGranted();
            }
        }
    }

    /**
     * 打开蓝牙聊天客户端界面
     * @param view
     */
    public void openBluetoothClientActivity(View view) {
        if (!isReady){
            return;
        }
        startActivity(new Intent(this,BluetoothChatClientActivity.class));
    }

    /**
     * 打开蓝牙聊天服务端界面
     * @param view
     */
    public void openBluetoothServerActivity(View view) {
        if (!isReady){
            return;
        }
        startActivity(new Intent(this,BluetoothChatServerActivity.class));
    }

    @Override
    public void onGranted() {
        isReady = true;
    }

    @Override
    public void onDenied() {
        isReady = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OPEN_BLUETOOTH) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, this);
            } else {
                onGranted();
            }
        } else {
            Toast.makeText(this, "蓝牙开启被拒绝", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

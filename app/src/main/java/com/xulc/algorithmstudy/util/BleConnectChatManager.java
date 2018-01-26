package com.xulc.algorithmstudy.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.xulc.algorithmstudy.MyApplication;

import java.io.IOException;
import java.util.UUID;

/**
 * Date：2018/1/25
 * Desc：蓝牙客户机和服务机连接管理
 * Created by xuliangchun.
 */

public class BleConnectChatManager {
    private static BleConnectChatManager manager;
    private final UUID BLE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final int MAX_BUFFER_SIZE = 1024;

    public static final int STATUS_DISCONNECT = 0;
    public static final int STATUS_WAIT_CONNECT = 1;
    public static final int STATUS_CONNECTING = 2;
    public static final int STATUS_CONNECT_FAILED = 3;
    public static final int STATUS_CONNECTED = 4;

    private final int MSG_WHAT_SEND_MESSAGE = 1;
    private final int MSG_WHAT_ACCEPT_MESSAGE = 2;
    private final int MSG_WHAT_STATUS_CHANGE = 3;


    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice remoteDevice;
    private OnBleConnectListener connectListener;
    private AcceptThread acceptThread;
    private ConnectThread connectThread;

    private boolean isCancel;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_WHAT_ACCEPT_MESSAGE:
                    connectListener.onAcceptMessage(msg.getData().getByteArray("data"));
                    break;
                case MSG_WHAT_SEND_MESSAGE:
                    connectListener.onSendMessage(msg.getData().getByteArray("data"));
                    break;
                case MSG_WHAT_STATUS_CHANGE:
                    connectListener.onConnectStatusChange(msg.getData().getInt("data",STATUS_DISCONNECT));
                    break;
            }
        }
    };


    public static BleConnectChatManager getInstance() {
        if (manager == null) {
            synchronized (BleConnectChatManager.class) {
                if (manager == null) {
                    manager = new BleConnectChatManager();
                }
            }
        }
        return manager;
    }



    private void sendHandlerStatus(int what, int status) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putInt("data",status);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    private void sendHandlerMessage(int what, byte[] bytes) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putByteArray("data",bytes);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * 建立服务机
     * @param listener
     */
    public void createServer(OnBleConnectListener listener) {
        stopConnect();
        this.connectListener = listener;
        acceptThread = new AcceptThread();
        acceptThread.start();
    }


    /**
     * 建立客户机
     * @param remoteAddress
     * @param listener
     */
    public void createClient(String remoteAddress, OnBleConnectListener listener) {
        stopConnect();

        this.connectListener = listener;
        remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(remoteAddress);
        connectThread = new ConnectThread();
        connectThread.start();


    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message) {
        if (bluetoothSocket != null) {
            if (connectThread!=null){
                connectThread.sendMessage(message);
            }else if (acceptThread!=null){
                acceptThread.sendMessage(message);
            }
        } else {
            Toast.makeText(MyApplication.getContext(), "未建立连接无法发送消息", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 断开连接
     */
    public void stopConnect() {
        isCancel = true;
        handler.removeMessages(MSG_WHAT_STATUS_CHANGE);
        handler.removeMessages(MSG_WHAT_ACCEPT_MESSAGE);
        handler.removeMessages(MSG_WHAT_SEND_MESSAGE);
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bluetoothSocket = null;
        connectThread = null;
        acceptThread = null;
        sendHandlerStatus(MSG_WHAT_STATUS_CHANGE,STATUS_DISCONNECT);
    }

    public interface OnBleConnectListener {
        void onAcceptMessage(byte[] bytes);

        void onSendMessage(byte[] bytes);

        void onConnectStatusChange(int status);
    }

    /**
     * 客户机线程
     */
    private class ConnectThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                bluetoothSocket = remoteDevice.createRfcommSocketToServiceRecord(BLE_UUID);
                sendHandlerStatus(MSG_WHAT_STATUS_CHANGE,STATUS_CONNECTING);

                bluetoothSocket.connect();
                sendHandlerStatus(MSG_WHAT_STATUS_CHANGE,STATUS_CONNECTED);

                isCancel = false;
                byte[] buffer = new byte[MAX_BUFFER_SIZE];
                int byteLength;
                while (true) {
                    if (isCancel){
                        break;
                    }
                    byteLength = bluetoothSocket.getInputStream().read(buffer);
                    if (byteLength != -1) {
                        byte[] data = new byte[byteLength];
                        System.arraycopy(buffer,0,data,0,byteLength);
                        sendHandlerMessage(MSG_WHAT_ACCEPT_MESSAGE,data);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                stopConnect();
                sendHandlerStatus(MSG_WHAT_STATUS_CHANGE,STATUS_CONNECT_FAILED);
            }
        }


        void sendMessage(String message){
            try {
                sendHandlerMessage(MSG_WHAT_SEND_MESSAGE,message.getBytes());

                bluetoothSocket.getOutputStream().write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 服务机线程
     */
    private class AcceptThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                BluetoothServerSocket serverSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("xulc_server", BLE_UUID);
                sendHandlerStatus(MSG_WHAT_STATUS_CHANGE,STATUS_WAIT_CONNECT);
                bluetoothSocket = serverSocket.accept();
                sendHandlerStatus(MSG_WHAT_STATUS_CHANGE,STATUS_CONNECTED);

                isCancel = false;
                byte[] buffer = new byte[MAX_BUFFER_SIZE];
                int byteLength;//本地读取到的字节长度
                while (true) {
                    if (isCancel){
                        break;
                    }
                    byteLength = bluetoothSocket.getInputStream().read(buffer);
                    if (byteLength != -1) {
                        byte[] data = new byte[byteLength];
                        System.arraycopy(buffer,0,data,0,byteLength);
                        sendHandlerMessage(MSG_WHAT_ACCEPT_MESSAGE,data);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                stopConnect();
                sendHandlerStatus(MSG_WHAT_STATUS_CHANGE,STATUS_DISCONNECT);

            }
        }

        void sendMessage(String message){
            try {
                sendHandlerMessage(MSG_WHAT_SEND_MESSAGE,message.getBytes());
                bluetoothSocket.getOutputStream().write(message.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

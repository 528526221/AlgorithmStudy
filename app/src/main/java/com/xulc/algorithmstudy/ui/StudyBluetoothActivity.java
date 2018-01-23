package com.xulc.algorithmstudy.ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.adapter.BluetoothDeviceAdapter;
import com.xulc.algorithmstudy.model.StudyBluetoothModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Date：2018/1/21
 * Desc：学习蓝牙
 * Created by xuliangchun.
 */

public class StudyBluetoothActivity extends BaseActivity implements BaseActivity.PermissionCallBackListener {
    private static final int OPEN_BLUETOOTH = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private ListView lvBluetooth;
    private BluetoothDeviceAdapter adapter;
    private List<StudyBluetoothModel> bluetoothDeviceList;
    private BluetoothClient mClient;


    private final BluetoothBondListener mBluetoothBondListener = new BluetoothBondListener() {
        @Override
        public void onBondStateChanged(String mac, int bondState) {
            // bondState = Constants.BOND_NONE, BOND_BONDING, BOND_BONDED
            switch (bondState){
                case Constants.BOND_NONE:
                    Log.i("xlc","BOND_NONE"+mac);
                    break;
                case Constants.BOND_BONDING:
                    Log.i("xlc","BOND_BONDING"+mac);
                    break;
                case Constants.BOND_BONDED:
                    Log.i("xlc","BOND_BONDED"+mac);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_bluetooth);

        lvBluetooth = (ListView) findViewById(R.id.lvBluetooth);
        adapter = new BluetoothDeviceAdapter(this);
        lvBluetooth.setAdapter(adapter);
        lvBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).isBonded()){

                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        adapter.getItem(position).getSearchResult().device.createBond();
                    }

                }
            }
        });
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

    @Override
    public void onGranted() {
        bluetoothDeviceList = new ArrayList<>();
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : bondedDevices){
            StudyBluetoothModel result = new StudyBluetoothModel(new SearchResult(device),true);
            bluetoothDeviceList.add(result);
        }
        adapter.updateData(bluetoothDeviceList);

        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        mClient = new BluetoothClient(getApplicationContext());
        mClient.registerBluetoothBondListener(mBluetoothBondListener);

        mClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {
                Log.i("xlc","onSearchStarted");
            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                Beacon beacon = new Beacon(device.scanRecord);
                BluetoothLog.v(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));
                for (StudyBluetoothModel model : bluetoothDeviceList){
                    if (model.getSearchResult().getAddress().equals(device.getAddress())){
                        return;
                    }
                }
                bluetoothDeviceList.add(new StudyBluetoothModel(device,false));
                adapter.updateData(bluetoothDeviceList);
            }

            @Override
            public void onSearchStopped() {
                Log.i("xlc","onSearchStopped");

            }

            @Override
            public void onSearchCanceled() {
                Log.i("xlc","onSearchCanceled");

            }
        });


    }

    @Override
    public void onDenied() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClient!=null){
            mClient.stopSearch();
            mClient.unregisterBluetoothBondListener(mBluetoothBondListener);
        }
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

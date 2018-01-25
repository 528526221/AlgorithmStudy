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
 * Desc：蓝牙设备列表
 * Created by xuliangchun.
 */

public class BluetoothDeviceActivity extends BaseActivity implements BaseActivity.PermissionCallBackListener {
    private static final int OPEN_BLUETOOTH = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private ListView lvBluetooth;
    private BluetoothDeviceAdapter adapter;
    private List<StudyBluetoothModel> bluetoothDeviceList;
    private BluetoothClient mClient;
    private boolean isReady;


    private final BluetoothBondListener mBluetoothBondListener = new BluetoothBondListener() {
        @Override
        public void onBondStateChanged(String mac, int bondState) {
            // bondState = Constants.BOND_NONE, BOND_BONDING, BOND_BONDED
            for (StudyBluetoothModel model : bluetoothDeviceList){
                if (model.getSearchResult().getAddress().equals(mac)){
                    model.setBondState(bondState);
                    break;
                }
            }
            adapter.updateData(bluetoothDeviceList);


        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device);

        lvBluetooth = (ListView) findViewById(R.id.lvBluetooth);
        adapter = new BluetoothDeviceAdapter(this);
        lvBluetooth.setAdapter(adapter);
        lvBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stopScan(null);
                int state = adapter.getItem(position).getBondState();
                if (state == Constants.BOND_BONDING){
                    Toast.makeText(BluetoothDeviceActivity.this,"正在绑定呢~~~",Toast.LENGTH_SHORT).show();
                }else if (state == Constants.BOND_BONDED){
//                    try {
//                        Method method = BluetoothDevice.class.getDeclaredMethod("removeBond");
//                        method.invoke(adapter.getItem(position).getSearchResult().device);
//                    } catch (NoSuchMethodException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
                    Intent intent = new Intent();
                    intent.putExtra("device_address",adapter.getItem(position).getSearchResult().getAddress());
                    setResult(RESULT_OK,intent);
                    finish();
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

        mClient = new BluetoothClient(getApplicationContext());
        mClient.registerBluetoothBondListener(mBluetoothBondListener);
        isReady = true;



    }

    @Override
    public void onDenied() {
        isReady = false;
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

    /**
     * 停止扫描
     * @param view
     */
    public void stopScan(View view) {
        if (mClient != null){
            mClient.stopSearch();
        }
    }

    /**
     * 开始扫描
     * @param view
     */
    public void startScan(View view){
        if (!isReady){
            Toast.makeText(this,"还木有准备好呢~~~",Toast.LENGTH_SHORT).show();
            return;
        }
        bluetoothDeviceList = new ArrayList<>();
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : bondedDevices){
            StudyBluetoothModel result = new StudyBluetoothModel(new SearchResult(device),Constants.BOND_BONDED);
            bluetoothDeviceList.add(result);
        }
        adapter.updateData(bluetoothDeviceList);

        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
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
                bluetoothDeviceList.add(new StudyBluetoothModel(device,Constants.BOND_NONE));
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
}

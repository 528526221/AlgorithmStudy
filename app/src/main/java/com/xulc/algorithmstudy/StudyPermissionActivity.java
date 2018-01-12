package com.xulc.algorithmstudy;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

/**
 * Date：2018/1/11
 * Desc：
 * Created by xuliangchun.
 */

public class StudyPermissionActivity extends BaseActivity implements BaseActivity.PermissionCallBackListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_permission);
    }

    /**
     * 定位
     * @param view
     */
    public void startLocation(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            requestRuntimePermission(new String[]{ Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION},this);

        }
    }


    @Override
    public void onGranted() {
        Toast.makeText(this,"可以开始定位了",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDenied() {
        Toast.makeText(this,"由于你拒绝了权限，所以无法定位",Toast.LENGTH_LONG).show();
    }
}

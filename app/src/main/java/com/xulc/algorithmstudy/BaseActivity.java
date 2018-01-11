package com.xulc.algorithmstudy;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

/**
 * Date：2018/1/11
 * Desc：
 * Created by xuliangchun.
 */

public class BaseActivity extends AppCompatActivity{
    private PermissionCallBackListener listener;
    public interface PermissionCallBackListener{
        void onGranted();
        void onDenied();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestRuntimePermission(String permission,PermissionCallBackListener listener){
        this.listener = listener;

        switch (checkSelfPermission(permission)){
            case PackageManager.PERMISSION_GRANTED:
                listener.onGranted();
                break;
            case PackageManager.PERMISSION_DENIED:
                if (shouldShowRequestPermissionRationale(permission)){
                    //用户拒绝过
                }
                break;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}

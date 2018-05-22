package com.xulc.algorithmstudy.ui;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Date：2018/1/11
 * Desc：添加权限申请的基类
 * Created by xuliangchun.
 */

public class BaseActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;//申请权限的code
    private static final int OPEN_APP_DETAIL_CODE = 1;//打开应用详情的code
    private PermissionCallBackListener listener;
    private String[] needPermissions = null;


    /**
     * 权限回调接口
     */
    public interface PermissionCallBackListener {
        void onGranted();
        void onDenied();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestRuntimePermission(String[] permissions, PermissionCallBackListener listener) {
        this.listener = listener;
        this.needPermissions = permissions;
        if (checkSelfPermissions(needPermissions) == PackageManager.PERMISSION_GRANTED) {
            listener.onGranted();
        } else {
            requestPermissions(needPermissions, REQUEST_LOCATION_PERMISSION_CODE);
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean shouldShowRequestPermissionRationales(String[] permissions) {
        //有一项权限不需要向用户说明的话，那么不必向用户说明了
        for (String permission : permissions) {
            if (!shouldShowRequestPermissionRationale(permission)) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public int checkSelfPermissions(String[] permissions) {
        //判断权限组 有一项权限被拒绝了，那么结果就是被拒绝
        int grantResult = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                grantResult = PackageManager.PERMISSION_DENIED;
                break;
            }
        }
        return grantResult;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            int grantResult = PackageManager.PERMISSION_GRANTED;

            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    grantResult = PackageManager.PERMISSION_DENIED;
                    break;
                }
            }
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                listener.onGranted();
            } else {

                if (shouldShowRequestPermissionRationales(permissions)) {
                    new AlertDialog.Builder(this)
                            .setTitle("通知")
                            .setMessage("大哥 我需要这个权限 帮我开开好吧")
                            .setNegativeButton("滚", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    listener.onDenied();
                                }
                            })
                            .setPositiveButton("好吧好吧", new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(needPermissions, REQUEST_LOCATION_PERMISSION_CODE);
                                }
                            })
                            .create()
                            .show();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("通知")
                            .setMessage("应用权限被拒绝，去打开权限")
                            .setNegativeButton("就不打开", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    listener.onDenied();
                                }
                            })
                            .setPositiveButton("去打开", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, OPEN_APP_DETAIL_CODE);

//                                    Intent intent = new Intent();
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
//                                    ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
//                                    intent.setComponent(comp);
//                                    startActivity(intent);

                                }
                            })
                            .create()
                            .show();
                }

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == OPEN_APP_DETAIL_CODE && needPermissions != null) {
                requestRuntimePermission(needPermissions, listener);
            }
        }
    }

    protected void showLoading(){
        final ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 400;
        lp.topMargin = 800;
        textView.setLayoutParams(lp);
        textView.setText("加载中/n请稍候...");
        textView.setBackgroundColor(Color.RED);
        decorView.addView(textView);
    }

    protected void hideLoading(){

    }
}

package com.xulc.algorithmstudy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

/**
 * Date：2018/1/11
 * Desc：
 * Created by xuliangchun.
 */

public class PermissionUtil {
    private static PermissionUtil util;
    public static PermissionUtil getUtil(){
        if (util==null){
            synchronized (PermissionUtil.class){
                if (util == null){
                    util = new PermissionUtil();
                }
            }
        }
        return util;
    }

    public interface PermissionCallBackListener{
        void onGranted();
        void onDenied();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestRuntimePermission(Context mContext,String permission){
        if (mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED){

        }

    }

    private void startAppSettings(Context mContext) {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        mContext.startActivity(intent);
    }


}

package com.xulc.myzxing.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;
import com.xulc.myzxing.zxing.view.ViewfinderView;

/**
 * Date：2018/3/21
 * Desc：
 * Created by xuliangchun.
 */

public interface IActivityZxing {
    Handler getHandler();
    void handleDecode(Result result ,Bitmap barcode);
    ViewfinderView getViewfinderView();
    void drawViewfinder();
    void startActivity(Intent intent);
    void setResult(int resultCode, Intent data);
    void finish();
}

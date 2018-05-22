package com.xulc.algorithmstudy.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.model.TestReflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;

/**
 * Date：2017/12/13
 * Desc：主界面
 * Created by xuliangchun.
 */

public class GuideActivity extends BaseActivity implements BaseActivity.PermissionCallBackListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!pm.isIgnoringBatteryOptimizations(packageName)){
                Intent intent = new Intent();
                intent.setAction(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:com.xulc.algorithmstudy"));

                startActivity(intent);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},this);
        }

        TestReflect testReflect = new TestReflect("xuliangchun",26);
        try {
            Field field = TestReflect.class.getDeclaredField("age");
            field.setAccessible(true);
            int age = (int) field.get(testReflect);
            Log.i("xlc",age+"");

            Method method = TestReflect.class.getDeclaredMethod("getName");
            method.setAccessible(true);
            String name = (String) method.invoke(testReflect);
            Log.i("xlc",name+"");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            Class cls = Class.forName("android.content.res.AssetManager");
            AssetManager assetManager = (AssetManager) cls.newInstance();
            Method method = cls.getDeclaredMethod("addAssetPath",String.class);
            method.invoke(assetManager,"");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Field field;
//        GuideActivity.class.getClass()


    }


    public void openMain(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void openLearn(View view) {
        startActivity(new Intent(this,LearnActivity.class));

    }

    public void openViewPager(View view) {
        startActivity(new Intent(this,ViewPagerActivity.class));
    }

    public void openIrregular(View view) {
        startActivity(new Intent(this,TestIrregularActivity.class));

    }

    public void openBezierHeaderView(View view) {
        startActivity(new Intent(this,BezierHeaderActivity.class));
    }

    public void openTabIndicator(View view) {
        startActivity(new Intent(this,StudyTabIndicatorActivity.class));

    }

    public void studyPermission(View view) {
        startActivity(new Intent(this,StudyPermissionActivity.class));
    }

    @Override
    public void onGranted() {
        Toast.makeText(this,"感谢我可以工作了",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDenied() {
        Toast.makeText(this,"非常失望 我可能运行不了",Toast.LENGTH_LONG).show();

    }

    public void openTestBattery(View view) {
        startActivity(new Intent(this,BatteryTestActivity.class));
    }

    public void studyTouchDispatch(View view) {
        startActivity(new Intent(this,StudyTouchDispatchActivity.class));

    }

    public void studyAIDL(View view) {
        startActivity(new Intent(this,AIDLActivity.class));
    }

    public void studyBluetooth(View view) {
        startActivity(new Intent(this,StudyBluetoothActivity.class));
    }

    public void studyCircleViewPager(View view) {
        startActivity(new Intent(this,StudyCyclicViewPagerActivity.class));
    }


    public void studyZxing(View view) {
        startActivity(new Intent(this,QrCodeScanActivity.class));
    }

    public void studyRecyclerView(View view) {
        startActivity(new Intent(this,RecyclerViewActivity.class));
    }

    public void studyWebView(View view) {
        startActivity(new Intent(this,StudyWebViewActivity.class));
    }

    public void testBuglyTinker(View view) {
        startActivity(new Intent(this,TestBuglyTinkerActivity.class));
    }

    public void studyAddShoppingAnim(View view) {
        startActivity(new Intent(this,StudyAddShoppingAnimActivity.class));
//        showLoading();
    }
}

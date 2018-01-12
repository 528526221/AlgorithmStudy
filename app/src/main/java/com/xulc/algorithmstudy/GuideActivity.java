package com.xulc.algorithmstudy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import static android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS;

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
            if (pm.isIgnoringBatteryOptimizations(packageName)){
                Intent intent = new Intent();
                intent.setAction(ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                startActivity(intent);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},this);
        }
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
}

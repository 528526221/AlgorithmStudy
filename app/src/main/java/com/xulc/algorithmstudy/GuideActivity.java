package com.xulc.algorithmstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS;

/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public class GuideActivity extends AppCompatActivity {
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
    }

    public void openMain(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void openListView(View view) {
        startActivity(new Intent(this,ListViewActivity.class));
    }

    public void openTestBga(View view) {
        startActivity(new Intent(this,TestBgaRefreshActivity.class));
    }

    public void openStudy(View view) {
        startActivity(new Intent(this,StudyActivity.class));
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
}

package com.xulc.algorithmstudy.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xulc.algorithmstudy.R;

import static android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS;
import static android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;

/**
 * Date：2018/1/16
 * Desc：白名单测试
 * Created by xuliangchun.
 */

public class BatteryTestActivity extends BaseActivity{
    private RelativeLayout rlTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_test);
        rlTest = (RelativeLayout) findViewById(R.id.rlTest);
    }

    public void openWhiteList(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            intent.setAction(ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            startActivity(intent);
        }
    }

    public void addToWhiteList(View view) {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm.isIgnoringBatteryOptimizations(getPackageName())){
                Toast.makeText(this,"应用已经在白名单中",Toast.LENGTH_LONG).show();

            }else {
                Intent intent = new Intent();
                intent.setAction(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:"+getPackageName()));
                startActivity(intent);

            }

        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void testTransition(View view) {
        Button button = ((Button)rlTest.getChildAt(0));
        if (button.getText().toString().equals("请求")){
            button.setText("请求加入白名单");
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rlTest.getLayoutParams();
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            layoutParams.leftMargin = 0;
            layoutParams.topMargin = 0;
            rlTest.setLayoutParams(layoutParams);
            //AutoTransition 很强啊
            AutoTransition transition = new AutoTransition();
            transition.setDuration(300);
            TransitionManager.beginDelayedTransition(rlTest,transition);

        }else {
            button.setText("请求");
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rlTest.getLayoutParams();
            layoutParams.width = 200;
            layoutParams.leftMargin = 200;
            layoutParams.topMargin = 50;
            rlTest.setLayoutParams(layoutParams);
            AutoTransition transition = new AutoTransition();
            transition.setDuration(300);
            TransitionManager.beginDelayedTransition(rlTest,transition);

        }

    }
}

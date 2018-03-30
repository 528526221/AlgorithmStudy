package com.xulc.algorithmstudy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xulc.algorithmstudy.BuildConfig;
import com.xulc.algorithmstudy.R;

/**
 * Date：2018/3/30
 * Desc：
 * Created by xuliangchun.
 */

public class TestBuglyTinkerActivity extends BaseActivity{
    private TextView tvVersion,tvMsg;
    private Button btnCheck;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_buglytinker);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        btnCheck = (Button) findViewById(R.id.btnCheck);

        tvVersion.setText(String.format("当前版本：v%s-%s,版本号：%s", BuildConfig.VERSION_NAME,BuildConfig.DEBUG ? "debug" : "release",BuildConfig.VERSION_CODE));

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = null;
                msg = "it already been fixed!";
                tvMsg.setText(String.format("%s：%d",msg, msg.length()));

            }
        });

    }
}

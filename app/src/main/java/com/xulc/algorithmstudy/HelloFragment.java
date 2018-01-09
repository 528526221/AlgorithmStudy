package com.xulc.algorithmstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Date：2018/1/2
 * Desc：
 * Created by xuliangchun.
 */

public class HelloFragment extends Fragment{
    private TextView tvTip;
    private String tip;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hello,container,false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTip = view.findViewById(R.id.tvTip);
        tvTip.setText(tip);
    }

    public static HelloFragment getInstance(String tip){
        HelloFragment fragment = new HelloFragment();
        fragment.tip = tip;
        return fragment;
    }
}

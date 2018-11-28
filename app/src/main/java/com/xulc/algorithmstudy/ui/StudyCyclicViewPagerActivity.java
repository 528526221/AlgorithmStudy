package com.xulc.algorithmstudy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.widget.CyclicViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/1/30
 * Desc：实现一个循环的ViewPager
 * Created by xuliangchun.
 */

public class StudyCyclicViewPagerActivity extends BaseActivity{
    private CyclicViewPager viewPager;
    private ArrayList images;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_circle_view_pager);
        viewPager = (CyclicViewPager) findViewById(R.id.viewPager);
        images = new ArrayList();
        images.add("http://img03.sogoucdn.com/app/a/100520024/f4d733442018581fb462be0f0119d0fe");
        images.add("http://img03.sogoucdn.com/app/a/100520024/e43aaec993f1f38b944e248293580f5e");
        images.add("http://img01.sogoucdn.com/app/a/100520024/d2d9770daa7a471c00565234f5d754c0");
        viewPager.setCyclicModels(images, new CyclicViewPager.OnCreateViewListener<String>() {

            @Override
            public View createChildView(List<String> strings, int position, LayoutInflater mLayoutInflater) {
                View view = mLayoutInflater.inflate(R.layout.item_view_pager,null);
                ImageView ivTest = view.findViewById(R.id.ivTest);
                TextView tvTest = view.findViewById(R.id.tvTest);
                tvTest.setText(String.format("第%d张图片",position));
                Glide.with(StudyCyclicViewPagerActivity.this).load(strings.get(position)).into(ivTest);
                return view;
            }


        });
        viewPager.setCyclicPageChangeListener(new CyclicViewPager.CyclicPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("xlc","position"+ position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.startAutoScroll();
    }
}

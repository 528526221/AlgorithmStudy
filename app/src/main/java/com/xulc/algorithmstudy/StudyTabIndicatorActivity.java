package com.xulc.algorithmstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/1/2
 * Desc：
 * Created by xuliangchun.
 */

public class StudyTabIndicatorActivity extends AppCompatActivity{
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private PageTabIndicator tabIndicator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_tab_indicator);
        HelloFragment fragment = new HelloFragment();
        HelloFragment fragment1 = new HelloFragment();
        HelloFragment fragment2 = new HelloFragment();
        fragments = new ArrayList<>();
        fragments.add(fragment);
        fragments.add(fragment1);
        fragments.add(fragment2);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        tabIndicator = (PageTabIndicator) findViewById(R.id.tabIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    private class FragmentAdapter extends FragmentPagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}

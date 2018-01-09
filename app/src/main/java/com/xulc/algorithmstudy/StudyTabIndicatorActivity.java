package com.xulc.algorithmstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date：2018/1/2
 * Desc：
 * Created by xuliangchun.
 */

public class StudyTabIndicatorActivity extends AppCompatActivity{
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private PageTabIndicator tabIndicator;
    private String[] titles = {"爱国","爱党","爱人民"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_tab_indicator);


        for (String s: titles){
            HelloFragment fragment = HelloFragment.getInstance(s);
            fragments.add(fragment);
        }

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        tabIndicator = (PageTabIndicator) findViewById(R.id.tabIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);




        tabIndicator.setTitles(Arrays.asList(titles));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabIndicator.startScroll(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        DumpHeapHelper.getInstance(this);//模拟内存泄漏
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

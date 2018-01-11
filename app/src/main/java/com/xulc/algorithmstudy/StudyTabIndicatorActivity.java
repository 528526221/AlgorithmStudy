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
 * Desc：测试tab指示器
 * Created by xuliangchun.
 */

public class StudyTabIndicatorActivity extends AppCompatActivity{
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private PageTabIndicator tabIndicator;
//    private String[] titles = {"爱国","爱党","爱我中华","爱情"};
    private String[] titles = {"爱国是","爱党","爱我中华","爱情爱我","社会","中国爱我中华","浙江","杭州","西溪花园","迎创"};

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
        tabIndicator.setViewPager(viewPager);

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

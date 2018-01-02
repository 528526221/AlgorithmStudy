package com.xulc.algorithmstudy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Date：2017/12/18
 * Desc：
 * Created by xuliangchun.
 */

public class ViewPagerActivity extends AppCompatActivity{
    private LoopViewPager viewPager;
    private ViewPagerAdp adp;
    private RelativeLayout rlContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager = (LoopViewPager) findViewById(R.id.viewPager);
        rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
        adp = new ViewPagerAdp(this);
        viewPager.setAdapter(adp);
        viewPager.setPageTransformer(true,new ScalePageTransformer());
        viewPager.setOffscreenPageLimit(3);
        rlContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
        viewPager.setOnPageChangeListener(new LoopViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("xlc","position="+position+";positionOffset="+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAutoScrollInterval(4000);
        viewPager.startAutoScroll();

    }





    private class ViewPagerAdp extends PagerAdapter{
        private List<View> views;

        public ViewPagerAdp(Context mContext) {
            this.views = new ArrayList<>();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            for (int i=0;i<10;i++){
                View view = inflater.inflate(R.layout.item_view_pager,null);
                ImageView ivTest = view.findViewById(R.id.ivTest);
                TextView tvTest = view.findViewById(R.id.tvTest);
                if (i%2==0){
                    ivTest.setImageResource(R.mipmap.pic5);
                }else if (i%3==0){
                    ivTest.setImageResource(R.mipmap.pic3);
                }else {
                    ivTest.setImageResource(R.mipmap.pic1);
                }
                tvTest.setText("pic"+i);
                views.add(view);
            }
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPos = (getCount() + position%getCount()) % getCount();
            View view = views.get(realPos);
            ViewParent parent = view.getParent();
            if (parent!=null){
                ((ViewGroup)parent).removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(views.get(position));
        }
    }
}

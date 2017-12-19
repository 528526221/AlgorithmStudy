package com.xulc.algorithmstudy;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public class TestBgaRefreshActivity extends AppCompatActivity {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerview;
    private RecyclerViewAdapter adapter;
    private List<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bga_refresh);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(this,strings);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        refreshLayout.setRefreshHeader(new CircleHeaderView(this));
        refreshLayout.setRefreshFooter(new LoadFooterView(this));
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int start = strings.size();
                        int end = strings.size()+20;
                        for (int i=start;i<end;i++){
                            strings.add("TEST"+i);
                        }
                        adapter.notifyDataSetChanged();
                        if (strings.size()>60){
                            refreshLayout.finishLoadmoreWithNoMoreData();
                        }else {
                            refreshLayout.finishLoadmore();
                        }
                    }
                },2000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.resetNoMoreData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        strings.clear();
                        for (int i=0;i<20;i++){
                            strings.add("TEST"+i);
                        }

                        adapter.notifyDataSetChanged();

                        refreshLayout.finishRefresh();


                    }
                },2000);
            }
        });
    }
}

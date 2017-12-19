package com.xulc.algorithmstudy;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Date：2017/12/14
 * Desc：
 * Created by xuliangchun.
 */

public class StudyActivity extends AppCompatActivity {
    private PullRefreshLayout pullRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.pullRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this,strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        pullRefreshLayout.setRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void refreshFinished() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        strings.clear();
                        for (int i=0;i<5;i++){
                            strings.add("TEST"+i);
                        }

                        adapter.notifyDataSetChanged();

                        pullRefreshLayout.refreshFinished();

                    }
                },2000);
                Log.d("xlc","height:"+pullRefreshLayout.getHeight());
            }

            @Override
            public void loadMoreFinished() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int start = strings.size();
                        int end = strings.size()+20;
                        for (int i=start;i<end;i++){
                            strings.add("TEST"+i);
                        }
                        adapter.notifyDataSetChanged();
                        pullRefreshLayout.loadMoreFinished();
                    }
                },2000);
                Log.d("xlc","height:"+pullRefreshLayout.getHeight());

            }
        });

        for (int i=0;i<15;i++){
            strings.add("TEST"+i);
        }

        adapter.notifyDataSetChanged();

    }
}

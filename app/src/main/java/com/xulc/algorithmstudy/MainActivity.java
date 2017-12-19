package com.xulc.algorithmstudy;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLoadLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLoadLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout.setColorSchemeResources(R.color.red,R.color.pray);
        swipeRefreshLayout.setRefreshLoadListener(new SwipeRefreshLoadLayout.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        strings.clear();
                        for (int i=0;i<20;i++){
                            strings.add("TEST"+i);
                        }

                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.refreshFinish();

                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int start = strings.size();
                        int end = strings.size()+20;
                        for (int i=start;i<end;i++){
                            strings.add("TEST"+i);
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.loadFinish(true);
                        Toast.makeText(MainActivity.this,"加载完成",Toast.LENGTH_SHORT).show();

                    }
                },2000);
            }
        });
        adapter = new RecyclerViewAdapter(this,strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.startRefresh();


    }

}

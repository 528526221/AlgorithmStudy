package com.xulc.algorithmstudy;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public class ListViewActivity extends AppCompatActivity {
    private SwipeRefreshLoadLayout swipeRefreshLayout;
    private ListView lvList;
    private ListViewAdapter adapter;
    private List<String> strings = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        swipeRefreshLayout = (SwipeRefreshLoadLayout) findViewById(R.id.swipeRefreshLayout);
        lvList = (ListView) findViewById(R.id.lvList);
        adapter =  new ListViewAdapter(this,strings);
        lvList.setAdapter(adapter);
        swipeRefreshLayout.setRefreshLoadListener(new SwipeRefreshLoadLayout.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        strings.clear();
                        for (int i=0;i<10;i++){
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
                        if (adapter.getCount()>80){
                            swipeRefreshLayout.loadFinishShowNoMore();
                        }else {
                            swipeRefreshLayout.loadFinish(true);
                        }
                    }
                },2000);
            }
        });

        swipeRefreshLayout.startRefresh();
        swipeRefreshLayout.setFootView(new DefaultFootView(this));

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position<adapter.getCount())
                    Toast.makeText(ListViewActivity.this,adapter.getItem(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

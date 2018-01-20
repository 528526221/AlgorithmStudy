package com.xulc.algorithmstudy.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.xulc.algorithmstudy.widget.BezierHeaderView;
import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.adapter.RecyclerViewAdapter;
import com.xulc.algorithmstudy.widget.StudyFooterView;
import com.xulc.algorithmstudy.widget.StudyRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2017/12/20
 * Desc：
 * Created by xuliangchun.
 */

public class BezierHeaderActivity extends AppCompatActivity{
    private StudyRefreshView studyRefreshView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<String> strings = new ArrayList<>();
    private BezierHeaderView headerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_header);
        studyRefreshView = (StudyRefreshView) findViewById(R.id.studyRefreshView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this,strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
//        studyRefreshView.setCanLoadMore(false);
        studyRefreshView.setCanRefresh(false);
        Button b=new Button(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        for (int i=0;i<40;i++){
            strings.add("TEST"+i);
        }

        adapter.notifyDataSetChanged();
        headerView = new BezierHeaderView(this);
        studyRefreshView.setCustomHeaderView(headerView);
        studyRefreshView.setCustomFooterView(new StudyFooterView(this));
        studyRefreshView.setRefreshLoadListener(new StudyRefreshView.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        strings.clear();
                        for (int i=0;i<15;i++){
                            strings.add("TEST"+i);
                        }

                        adapter.notifyDataSetChanged();

                        headerView.stopRefresh(studyRefreshView);

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
                        if (adapter.getItemCount()>60){
                            studyRefreshView.stopLoadMore(true);

                        }else {
                            studyRefreshView.stopLoadMore(false);

                        }
                    }
                },2000);
            }
        });
    }
}

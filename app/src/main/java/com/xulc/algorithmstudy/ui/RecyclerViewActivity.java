package com.xulc.algorithmstudy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.adapter.MyItemDecoration;
import com.xulc.algorithmstudy.adapter.RecyclerviewLinearAdapter;

/**
 * Date：2018/3/27
 * Desc：
 * Created by xuliangchun.
 */

public class RecyclerViewActivity extends BaseActivity{
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView.setAdapter(new RecyclerviewLinearAdapter(this));
        recyclerView.addItemDecoration(new MyItemDecoration());
    }



}

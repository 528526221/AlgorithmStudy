package com.xulc.algorithmstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xulc.algorithmstudy.R;

/**
 * Date：2018/3/27
 * Desc：
 * Created by xuliangchun.
 */

public class RecyclerviewLinearAdapter extends RecyclerView.Adapter<RecyclerviewLinearAdapter.Holder>{

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public RecyclerviewLinearAdapter(Context mContext) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_linear_recyclerview,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tvTip.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    static class Holder extends RecyclerView.ViewHolder{
        private TextView tvTip;
        public Holder(View itemView) {
            super(itemView);
            tvTip = itemView.findViewById(R.id.tvTip);
        }
    }
}

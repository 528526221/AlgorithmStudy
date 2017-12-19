package com.xulc.algorithmstudy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Date：2017/12/11
 * Desc：
 * Created by xuliangchun.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TextViewHolder> {
    private Context mContext;
    private List<String> strings;

    public RecyclerViewAdapter(Context mContext,List<String> strings) {
        this.mContext = mContext;
        this.strings = strings;
    }




    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_text,parent,false));
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, final int position) {
        holder.tvText.setText(strings.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,strings.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{
        private TextView tvText;
        public TextViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);

        }
    }
}

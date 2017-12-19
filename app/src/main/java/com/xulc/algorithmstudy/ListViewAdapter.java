package com.xulc.algorithmstudy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> strings;

    public ListViewAdapter(Context mContext, List<String> strings) {
        this.mContext = mContext;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public String getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_text,parent,false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvText.setText(strings.get(position));
        return convertView;
    }



    private static class Holder{
        TextView tvText;

        public Holder(View itemView) {
            tvText = itemView.findViewById(R.id.tvText);
        }

    }
}

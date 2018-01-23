package com.xulc.algorithmstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.model.StudyBluetoothModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/1/22
 * Desc：
 * Created by xuliangchun.
 */

public class BluetoothDeviceAdapter extends BaseAdapter{
    private Context mContext;
    private List<StudyBluetoothModel> scanResults;

    public BluetoothDeviceAdapter(Context mContext) {
        this.mContext = mContext;
        this.scanResults = new ArrayList<>();
    }

    public void updateData(List<StudyBluetoothModel> scanResults){
        this.scanResults = scanResults;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return scanResults.size();
    }

    @Override
    public StudyBluetoothModel getItem(int position) {
        return scanResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bluetooth_device,parent,false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvDeviceName.setText(scanResults.get(position).getName());
        holder.tvBindStatus.setText(scanResults.get(position).isBonded()?"已绑定":"新设备");
        return convertView;
    }

    static class Holder{
        TextView tvDeviceName;
        TextView tvBindStatus;
        public Holder(View itemView) {
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            tvBindStatus = itemView.findViewById(R.id.tvBindStatus);
        }
    }
}

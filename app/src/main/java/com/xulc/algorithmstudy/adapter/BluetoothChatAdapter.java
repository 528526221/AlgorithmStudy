package com.xulc.algorithmstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/1/25
 * Desc：
 * Created by xuliangchun.
 */

public class BluetoothChatAdapter extends BaseAdapter{
    private final int TYPE_CLIENT = 0;
    private final int TYPE_SERVER = 1;
    private List<ChatModel> chatModels;
    private Context mContext;

    public BluetoothChatAdapter(Context mContext) {
        this.mContext = mContext;
        this.chatModels = new ArrayList<>();
    }

    public void addData(ChatModel model){
        this.chatModels.add(model);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (chatModels.get(position).isFromMe()){
            return TYPE_CLIENT;
        }else {
            return TYPE_SERVER;
        }
    }

    @Override
    public int getCount() {
        return chatModels.size();
    }

    @Override
    public Object getItem(int position) {
        return chatModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderClient holderClient;
        HolderServer holderServer;
        switch (getItemViewType(position)){
            case TYPE_CLIENT:
                if (convertView == null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_chat_client,parent,false);
                    holderClient = new HolderClient();
                    holderClient.tvMessage = convertView.findViewById(R.id.tvMessage);
                    convertView.setTag(holderClient);
                }else {
                    holderClient = (HolderClient) convertView.getTag();
                }
                holderClient.tvMessage.setText(chatModels.get(position).getMessage());

                break;
            case TYPE_SERVER:
                if (convertView == null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_chat_server,parent,false);
                    holderServer = new HolderServer();
                    holderServer.tvMessage = convertView.findViewById(R.id.tvMessage);
                    convertView.setTag(holderServer);
                }else {
                    holderServer = (HolderServer) convertView.getTag();
                }
                holderServer.tvMessage.setText(chatModels.get(position).getMessage());

                break;
        }
        return convertView;
    }

    private class HolderClient {
        TextView tvMessage;
    }

    private class HolderServer {
        TextView tvMessage;
    }
}

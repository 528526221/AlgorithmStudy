package com.xulc.algorithmstudy.util;

import android.content.Context;

/**
 * Date：2018/1/5
 * Desc：
 * Created by xuliangchun.
 */

public class DumpHeapHelper {
    private static DumpHeapHelper helper;
    private Context mContext;
    public static DumpHeapHelper getInstance(Context context){
        if (helper == null){
            helper = new DumpHeapHelper();
            helper.mContext = context;
        }
        return helper;
    }
}

package com.xulc.algorithmstudy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Date：2018/1/2
 * Desc：Tab 指示器
 * Created by xuliangchun.
 */

public class PageTabIndicator extends View{
    private int mOffsetX;//偏移量
    public PageTabIndicator(Context context) {
        this(context,null);
    }

    public PageTabIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


}

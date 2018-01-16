package com.xulc.algorithmstudy.widget;

/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public interface IFootCallBack {
    void startLoading();
    void loadFinish(boolean isSuccess);
    void loadFinishShowNoMore();
    void reset();
}

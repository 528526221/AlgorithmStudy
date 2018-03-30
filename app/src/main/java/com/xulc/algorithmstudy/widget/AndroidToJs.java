package com.xulc.algorithmstudy.widget;

import android.webkit.JavascriptInterface;

/**
 * Date：2018/3/29
 * Desc：
 * Created by xuliangchun.
 */

public class AndroidToJs {
    private WebClickListener listener;

    public AndroidToJs(WebClickListener listener) {
        this.listener = listener;
    }

    public interface WebClickListener {
        void loadAgain();
    }


    @JavascriptInterface
    void loadAgain(){
        listener.loadAgain();
    }
}

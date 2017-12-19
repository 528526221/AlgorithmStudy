package com.xulc.algorithmstudy;

import android.view.View;

/**
 * Date：2017/12/18
 * Desc：
 * Created by xuliangchun.
 */

public class ScalePageTransformer implements LoopViewPager.PageTransformer {

    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.95f;

    /**
     * position取值特点：
     * 假设页面从0～1，则：
     * 第一个页面position变化为[0,-1]
     * 第二个页面position变化为[1,0]
     *
     * @param page
     * @param position
     */
    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1 + position : 1 - position;

        //一个公式
        float scaleValue = MIN_SCALE + tempScale * (MAX_SCALE - MIN_SCALE);
        page.setScaleX(scaleValue);
        page.setScaleY(scaleValue);


    }
}


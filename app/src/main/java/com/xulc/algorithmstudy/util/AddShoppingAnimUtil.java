package com.xulc.algorithmstudy.util;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xulc.algorithmstudy.R;


/**
 * Date：2018/5/22
 * Desc：加购动画
 * Created by xuliangchun.
 */

public class AddShoppingAnimUtil {
    private static ValueAnimator animator;
    public static void startAnim(Activity activity, View startView, View endView, Long duration, final AddShoppingAnimListener listener){
        final int[] startLocation = new int[2];
        int[] endLocation = new int[2];
        startView.getLocationInWindow(startLocation);
        endView.getLocationInWindow(endLocation);

        //对起点终点的坐标进行修正，一个控件本身的宽高，一个动画view的宽高
        startLocation[0] += (startView.getWidth()/2-dip2px(activity,5));
        startLocation[1] += (startView.getHeight()/2-dip2px(activity,5));
        endLocation[0] += (endView.getWidth()/2-dip2px(activity,5));
        endLocation[1] += (endView.getHeight()/2-dip2px(activity,5));
        //界面的顶层view
        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        //动画view
        final ImageView view = new ImageView(activity);
        view.setImageResource(R.drawable.shape_add_shopping);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //起点、终点、控制点
        final PointF startP = new PointF(startLocation[0],startLocation[1]);
        final PointF endP = new PointF(endLocation[0],endLocation[1]);
        final PointF controlP = new PointF(endLocation[0],startLocation[1]);
        if (Math.abs(startP.x-endP.x) < 50){
            //起点和终点x坐标接近时，避免直线掉落，修改控制点
            int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
            if (startP.x<=screenWidth/2){
                controlP.x = screenWidth;
            }else {
                controlP.x = 0;
            }
            controlP.y = (startP.y + endP.y)/2;
        }

        animator = ValueAnimator.ofObject(new PointFTypeEvaluator(controlP), startP, endP);
        //也可以不使用估值器去做
//        animator = ValueAnimator.ofFloat(0,1);
        animator.setDuration(duration);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                decorView.addView(view);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener!=null){
                    listener.onFinishAnim();
                }
                decorView.removeView(view);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                decorView.removeView(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                view.setTranslationX(pointF.x);
                view.setTranslationY(pointF.y);

            }
        });
        animator.start();
        //也可以不使用估值器去做
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float t = (float) animation.getAnimatedValue();
//                view.setTranslationX((1 - t) * (1 - t) * startP.x + 2 * t * (1 - t) * controlP.x + t * t * endP.x);
//                view.setTranslationY((1 - t) * (1 - t) * startP.y + 2 * t * (1 - t) * controlP.y + t * t * endP.y);
//
//            }
//        });
//        animator.start();

    }

    /**
     * 停止动画，在页面销毁时必须调用
     */
    public static void stopAnim(){
        if (animator != null){
            animator.cancel();
            animator = null;
        }
    }

    //自定义估值器返回二阶点坐标
    private static class PointFTypeEvaluator implements TypeEvaluator<PointF> {
        PointF control;
        PointF mPointF = new PointF();

        public PointFTypeEvaluator(PointF control) {
            this.control = control;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return getBezierPoint(startValue, endValue, control, fraction);
        }
        private PointF getBezierPoint(PointF start, PointF end, PointF control, float t) {
            //二阶贝塞尔曲线公式
            mPointF.x = (1 - t) * (1 - t) * start.x + 2 * t * (1 - t) * control.x + t * t * end.x;
            mPointF.y = (1 - t) * (1 - t) * start.y + 2 * t * (1 - t) * control.y + t * t * end.y;
            return mPointF;
        }
    }
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface AddShoppingAnimListener{
        void onFinishAnim();
    }
}

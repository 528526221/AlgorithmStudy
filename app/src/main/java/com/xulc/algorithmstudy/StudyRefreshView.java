package com.xulc.algorithmstudy;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Date：2017/12/14
 * Desc：参照CSDN博客编写，做了部分改动
 * 地址：http://blog.csdn.net/u013647382/article/details/58092102?utm_source=tuicool&utm_medium=referral
 * Created by xuliangchun.
 */

public class StudyRefreshView extends ViewGroup {
    private View mHeader;
    private TextView mHeaderText;
    private ProgressBar mHeaderProgressBar;
    private ImageView mHeaderArrow;
    private View mFooter;
    private TextView mFooterText;
    private ProgressBar mFooterProgressBar;
    private Status mStatus;
    private int headViewHeight;
    private int footViewHeight;

    private OnRefreshLoadListener refreshLoadListener;//刷新加载的监听
    private IHeaderCallBack headerCallBack;//自定义header的回调
    private IFooterCallBack footerCallBack;//自定义footer的回调
    private Scroller mScroller;

    private int mCurrentY;//滑动时y的当前值
    private int mLastMoveY;//滑动时y的上次值
    private int mDistanceY;//滑动两次y的距离
    private boolean loadNoMoreData;//上拉没有更多数据


    public StudyRefreshView(Context context) {
        this(context, null);
    }

    public StudyRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context, new LinearInterpolator());
    }


    /**
     * StudyRefreshView的状态
     */
    private enum Status {
        NORMAL,//正常状态
        REFRESHING,//刷新中
        LOADING,//加载中
    }


    /**
     * 设置刷新加载的监听
     *
     * @param refreshLoadListener
     */
    public void setRefreshLoadListener(OnRefreshLoadListener refreshLoadListener) {
        this.refreshLoadListener = refreshLoadListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addHeader();
        addFooter();
    }

    /**
     * 添加默认的header
     */
    private void addHeader() {
        mHeader = LayoutInflater.from(getContext()).inflate(R.layout.pull_header, null, false);
        LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mHeader, params);
        mHeaderText = findViewById(R.id.header_text);
        mHeaderProgressBar = findViewById(R.id.header_progressbar);
        mHeaderArrow = findViewById(R.id.header_arrow);
    }

    /**
     * 添加默认的footer
     */
    private void addFooter() {
        mFooter = LayoutInflater.from(getContext()).inflate(R.layout.pull_footer, null, false);
        LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mFooter, params);
        mFooterText = findViewById(R.id.footer_text);
        mFooterProgressBar = findViewById(R.id.footer_progressbar);
    }

    /**
     * 设置自定义header
     *
     * @param customHeaderView
     */
    public void setCustomHeaderView(View customHeaderView) {
        if (customHeaderView instanceof IHeaderCallBack) {
            if (mHeader != null) {
                removeView(mHeader);
                mHeader = customHeaderView;
                headerCallBack = (IHeaderCallBack) mHeader;
                addView(mHeader, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            }
        } else {
            throw new RuntimeException("The custom header view must implementes IHeaderCallBack");
        }

    }

    /**
     * 设置自定义footer
     *
     * @param customFooterView
     */
    public void setCustomFooterView(View customFooterView) {
        if (customFooterView instanceof IFooterCallBack) {
            if (mFooter != null) {
                removeView(mFooter);
                mFooter = customFooterView;
                footerCallBack = (IFooterCallBack) mFooter;
                addView(mFooter, 2, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            }
        } else {
            throw new RuntimeException("The custom footer view must implementes IFooterCallBack");
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //因为该容器肯定是固定宽高或者match_parent,所以只需要测量子控件的宽高就可以了
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mContentLayoutHeight = 0;//当前内容Y位置(也即目前content的高度)
        for (int i = 0; i < getChildCount(); i++) {
            //遍历所有子控件 放置好位置
            View child = getChildAt(i);
            if (child == mHeader) {
                headViewHeight = mHeader.getMeasuredHeight();
                child.layout(0, -child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else if (child == mFooter) {
                footViewHeight = mFooter.getMeasuredHeight();
                //有一种特殊情况： 当前内容未填满一屏，希望footer出现在最底部
                if (mContentLayoutHeight < getHeight()) {
                    child.layout(0, getHeight() - child.getMeasuredHeight(), child.getMeasuredWidth(), getHeight());
                } else {
                    child.layout(0, mContentLayoutHeight, child.getMeasuredWidth(), mContentLayoutHeight + child.getMeasuredHeight());
                }
            } else {
                child.layout(0, mContentLayoutHeight, child.getMeasuredWidth(), mContentLayoutHeight + child.getMeasuredHeight());
                mContentLayoutHeight += child.getMeasuredHeight();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        dealWithTouchEvent(event);

        return super.onTouchEvent(event);

    }

    /**
     * 自身处理touch事件
     *
     * @param event
     */
    private void dealWithTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                if (getScrollY() <= -headViewHeight && mDistanceY <= 0) {
                    scrollBy(0, mDistanceY / 8);//提高阻尼
                } else if (getScrollY() >= footViewHeight && mDistanceY >= 0) {
                    scrollBy(0, mDistanceY / 8);
                } else {
                    scrollBy(0, mDistanceY / 3);

                }
                updateHeader();
                updateFooter();
                break;

        }
    }

    /**
     * 松开手指时需要判断是否可以刷新或者加载或者重置
     */
    private void doWhenActionUp() {
        if (getScrollY() <= -headViewHeight) {
            // 下拉刷新，并且到达有效长度
            startRefresh();
        } else if (getScrollY() >= footViewHeight) {
            // 上拉加载更多，达到有效长度
            startLoadMore();
        } else {
            //重置状态
            resetHeaderNormal();
            resetFooterNormal();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        /**
         * 在这里遇到一个问题：在处理ACTION_MOVE事件时，有时候子控件接手了，
         * 后面该分发事件直接就把事件传递给了子控件，并不会征询onInterceptTouchEvent的意思
         * 表现出来的现象就是刚好滑到头（底）的那一刻，header（footer）无法拉出来，因为根本
         * 就没有拦截到ACTION_MOVE事件，除非ACTION_UP开始了新的周期。
         * 不知道怎么解决？所以在这个地方加一个让自身也处理touch事件
         */


        /**
         * 刷新或加载状态下，不用额外处理
         */
        if (mStatus == Status.REFRESHING || mStatus == Status.LOADING) {
            return super.dispatchTouchEvent(ev);
        }

        int y = (int) ev.getY();
        boolean isNeedIntercept;//是否需要拦截传递给内容view的touch事件

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentY = y;
                mDistanceY = mLastMoveY - mCurrentY;
                if (mDistanceY < 0) {
                    //下滑 判断是否需要拦截（判断第一个child是否滑倒最上面）
                    View child = getChildAt(0);
                    isNeedIntercept = getRefreshIntercept(child);
                    if (isNeedIntercept) {
                        dealWithTouchEvent(ev);
                    }
                } else if (mDistanceY > 0) {
                    //上滑 判断是否需要拦截（判断最后一个child是否滑动最底部）
                    View child = getChildAt(0);
                    isNeedIntercept = getLoadMoreIntercept(child);
                    if (isNeedIntercept) {
                        dealWithTouchEvent(ev);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                doWhenActionUp();
                break;
        }
        mLastMoveY = y;


        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /**
         * 刷新或者加载中不要拦截事件，交由子控件自由发挥
         */
        if (mStatus == Status.REFRESHING || mStatus == Status.LOADING) {
            return false;
        }
        boolean isNeedIntercept = false;//是否需要拦截传递给内容view的touch事件

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mDistanceY < 0) {
                    //下滑 判断是否需要拦截（判断第一个child是否滑倒最上面）
                    View child = getChildAt(0);
                    isNeedIntercept = getRefreshIntercept(child);
                } else if (mDistanceY > 0) {
                    //上滑 判断是否需要拦截（判断最后一个child是否滑动最底部）
                    View child = getChildAt(0);
                    isNeedIntercept = getLoadMoreIntercept(child);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isNeedIntercept = false;
                break;
        }
        return isNeedIntercept;

    }


    /*汇总判断 刷新和加载是否拦截*/
    private boolean getRefreshIntercept(View child) {
        boolean intercept = false;

        if (child instanceof AdapterView) {
            intercept = adapterViewRefreshIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = scrollViewRefreshIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = recyclerViewRefreshIntercept(child);
        }
        return intercept;
    }


    private boolean getLoadMoreIntercept(View child) {
        boolean intercept = false;

        if (child instanceof AdapterView) {
            intercept = adapterViewLoadMoreIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = scrollViewLoadMoreIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = recyclerViewLoadMoreIntercept(child);
        }
        return intercept;
    }

    // 判断AdapterView下拉刷新是否拦截
    private boolean adapterViewRefreshIntercept(View child) {
        boolean intercept = true;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getFirstVisiblePosition() != 0
                || adapterChild.getChildAt(0).getTop() != 0) {
            intercept = false;
        }
        return intercept;
    }

    // 判断AdapterView加载更多是否拦截
    private boolean adapterViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getLastVisiblePosition() == adapterChild.getCount() - 1 &&
                (adapterChild.getChildAt(adapterChild.getChildCount() - 1).getBottom() >= getMeasuredHeight())) {
            intercept = true;
        }
        return intercept;
    }

    // 判断ScrollView刷新是否拦截
    private boolean scrollViewRefreshIntercept(View child) {
        boolean intercept = false;
        if (child.getScrollY() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    // 判断ScrollView加载更多是否拦截
    private boolean scrollViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) child;
        View scrollChild = scrollView.getChildAt(0);

        if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
            intercept = true;
        }
        return intercept;
    }

    // 判断RecyclerView刷新是否拦截
    // RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部

    private boolean recyclerViewRefreshIntercept(View child) {
        return !child.canScrollVertically(-1);
    }

    // 判断RecyclerView加载更多是否拦截
    private boolean recyclerViewLoadMoreIntercept(View child) {
        return !child.canScrollVertically(1);
    }

    /**
     * 更新状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        mStatus = status;
    }

    /**
     * 更新header信息
     */
    public void updateHeader() {
        int scrollY = Math.abs(getScrollY());
        if (headerCallBack != null) {
            headerCallBack.onHeaderMove(headViewHeight, scrollY);
        } else {
            if (scrollY >= headViewHeight) {
                mHeaderText.setText("松开刷新");
                if (mHeaderArrow.getTag() == null) {
                    ObjectAnimator icon_anim = ObjectAnimator.ofFloat(mHeaderArrow, "rotation", 0F, -180F);//设置Y
                    icon_anim.setDuration(250);
                    icon_anim.start();
                    mHeaderArrow.setTag("1");
                }

            } else {
                mHeaderText.setText("下拉刷新");
                if (mHeaderArrow.getTag() != null && mHeaderArrow.getTag().toString().equals("1")) {
                    ObjectAnimator icon_anim = ObjectAnimator.ofFloat(mHeaderArrow, "rotation", -180F, 0F);//设置Y
                    icon_anim.setDuration(250);
                    icon_anim.start();
                    mHeaderArrow.setTag(null);
                }
            }
        }
    }

    /**
     * 更新footer信息
     */
    public void updateFooter() {
        if (footerCallBack != null && !loadNoMoreData) {
            footerCallBack.onFooterMove(footViewHeight, Math.abs(getScrollY()));
        } else {
            if (getScrollY() >= footViewHeight) {
                mFooterText.setText("松开加载更多");
            } else {
                mFooterText.setText("上拉加载更多");
            }
        }
    }


    /**
     * 重置header位置
     */
    private void resetHeaderNormal() {
        startSlowScroll(getScrollY(), -getScrollY());
        updateStatus(Status.NORMAL);

        if (headerCallBack != null) {
            headerCallBack.onStateNormal();
        } else {
            mHeaderText.setText("下拉刷新");
            mHeaderArrow.setVisibility(VISIBLE);
            mHeaderProgressBar.setVisibility(GONE);
            mHeaderArrow.setRotation(0f);

        }
    }

    /**
     * 重置footer位置
     */
    private void resetFooterNormal() {
        startSlowScroll(getScrollY(), -getScrollY());
        updateStatus(Status.NORMAL);
        if (footerCallBack != null && !loadNoMoreData) {
            footerCallBack.onStateNormal();
        } else {
            mFooterText.setText("上拉加载更多");
            mFooterProgressBar.setVisibility(GONE);
        }
    }

    /**
     * 开始刷新
     */
    private void startRefresh() {
//        scrollTo(0, -headViewHeight);
//        scrollBy(0,-(getScrollY()+headViewHeight));
        Log.d("xlc", "正在刷新");
        updateStatus(Status.REFRESHING);
        loadNoMoreData = false;//重置

        startSlowScroll(getScrollY(), -(getScrollY() + headViewHeight));
        if (headerCallBack != null) {
            headerCallBack.onStateRefresh(headViewHeight);
        } else {
            mHeaderProgressBar.setVisibility(VISIBLE);
            mHeaderArrow.setVisibility(GONE);
            mHeaderText.setText("正在刷新");
        }
        if (refreshLoadListener != null)
            refreshLoadListener.onRefresh();

    }

    /**
     * 开始加载更多
     */
    private void startLoadMore() {
//        scrollTo(0, footViewHeight);
//        scrollBy(0,footViewHeight-getScrollY());
        if (loadNoMoreData) {
            stopLoadMore(loadNoMoreData);
        } else {
            updateStatus(Status.LOADING);
            startSlowScroll(getScrollY(), footViewHeight - getScrollY());
            if (footerCallBack != null) {
                footerCallBack.onStateLoading(footViewHeight);
            } else {
                mFooterText.setText("正在加载");
                mFooterProgressBar.setVisibility(VISIBLE);
            }
            if (refreshLoadListener != null)
                refreshLoadListener.onLoadMore();
        }


    }

    /**
     * 刷新完成，提供给外部调用的方法
     */
    public void stopRefresh() {
//        scrollTo(0, 0);
//        scrollBy(0,-getScrollY());
        updateStatus(Status.NORMAL);
        startSlowScroll(getScrollY(), -getScrollY());
        if (headerCallBack != null) {
            headerCallBack.onStateFinish();
        } else {
            resetHeaderNormal();
        }
    }

    /**
     * 加载完成，提供给外部调用的方法
     *
     * @param loadNoMoreData 没有更多数据
     */
    public void stopLoadMore(boolean loadNoMoreData) {
//        scrollTo(0, 0);
//        scrollBy(0,-getScrollY());
        updateStatus(Status.NORMAL);

        this.loadNoMoreData = loadNoMoreData;
        startSlowScroll(getScrollY(), -getScrollY());

        if (footerCallBack != null) {
            footerCallBack.onStateFinish(loadNoMoreData);
        } else {
            resetFooterNormal();
        }


    }


    /**
     * 为了使我们scroll时不至于太突兀，通过Scroller来使其缓慢移动
     * 需要重写computeScroll方法生效
     *
     * @param startY  起始Y
     * @param offsetY 偏移Y
     */
    private void startSlowScroll(int startY, int offsetY) {
        mScroller.startScroll(0, startY, 0, offsetY);//默认250ms
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            if (headerCallBack != null)
                headerCallBack.onHeaderMove(headViewHeight, Math.abs(getScrollY()));
        }
    }

    /**
     * 刷新加载的接口
     */
    public interface OnRefreshLoadListener {
        void onRefresh();

        void onLoadMore();
    }


    public interface IHeaderCallBack {
        void onStateRefresh(int headerHeight);//正在刷新

        void onStateNormal();//初始状态

        void onStateFinish();//刷新完成

        void onHeaderMove(int headerHeight, int offsetY);//下拉header
    }

    public interface IFooterCallBack {
        void onStateLoading(int footerHeight);//正在加载

        void onStateNormal();//初始状态

        void onStateFinish(boolean loadNoMoreData);//加载完成

        void onFooterMove(int footerHeight, int offsetY);//上拉footer
    }


}

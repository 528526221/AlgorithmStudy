package com.xulc.algorithmstudy;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Date：2017/12/13
 * Desc：继承谷歌官方的SwipeRefreshLayout，增加滑动到底部自动加载
 * Created by xuliangchun.
 */

public class SwipeRefreshLoadLayout extends SwipeRefreshLayout {
    //是否正在加载中
    private boolean isLoadingMore = false;
    //可以上拉加载
    private boolean canLoadMore = true;
    private OnRefreshLoadListener listener;
    private View childView;//容器的ContentView
    private IFootCallBack iFootCallBack;


    public interface OnRefreshLoadListener {
        void onRefresh();
        void onLoadMore();
    }


    public SwipeRefreshLoadLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRefreshLoadListener(OnRefreshLoadListener listener) {
        this.listener = listener;
        initListener();
        initSomeThing();
    }
    public void startRefresh(){
        this.setRefreshing(true);
        refreshData();
    }

    private void initListener() {
        this.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
               refreshData();
            }
        });
    }

    private void initSomeThing() {
        //判断子控件类型
        int childCount = getChildCount();
        Log.d("xlc:", "childCount="+childCount);

        for (int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if (view instanceof RecyclerView || view instanceof ListView){
                childView = view;
                break;
            }
        }

        if (childView instanceof RecyclerView) {
            //处理子控件为RecyclerView时的情况
            RecyclerView recyclerView = (RecyclerView) childView;
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!canLoadMore || isLoadingMore){
                        Log.d("xlc:", "i am loading please not drag else i can not load"+canLoadMore+isLoadingMore);
                        return;
                    }

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //最后可见的位置
                    int lastVisibleItemPosition = getRecyclerViewLastVisibleItemPosition(layoutManager);
                    //可见的数量
                    int visibleItemCount = layoutManager.getChildCount();
                    //数据源总数量
                    int totalItemCount = layoutManager.getItemCount();
//                        Log.d("xlc:", "visibleItemCount="+visibleItemCount+"&totalItemCount="+totalItemCount);
                    if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1) {
                        Log.d("xlc:", "i can load");
                        loadMoreData();
                    }
                }
            });
        }else if (childView instanceof ListView){
            //处理子控件为ListView时的情况
            ListView listView = (ListView) childView;
            listView.setFooterDividersEnabled(false);
            listView.setOverScrollMode(OVER_SCROLL_NEVER);
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (!canLoadMore || isLoadingMore){
                        Log.d("xlc:", "i am loading please not drag else i can not load"+canLoadMore+isLoadingMore);
                        return;
                    }
                    if (scrollState == SCROLL_STATE_IDLE && view.getLastVisiblePosition() == view.getCount()-1){
                        Log.d("xlc:", "i can load");
                        loadMoreData();
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }
    }


    /**
     * 获取RecyclerView中最后一个可见的item的position
     *
     * @param layoutManager
     * @return
     */
    private int getRecyclerViewLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = 0;
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager
                    = (StaggeredGridLayoutManager) layoutManager;
            int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
            lastVisibleItemPosition = findMax(lastPositions);
        }
        return lastVisibleItemPosition;
    }

    /**
     * 找出最大的位置
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 下拉刷新数据
     */
    private void refreshData() {
        canLoadMore = true;
        if (listener!=null)
            listener.onRefresh();
    }

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        isLoadingMore = true;
        if (iFootCallBack!=null){
            iFootCallBack.startLoading();
        }
        if (listener!=null)
            listener.onLoadMore();
    }

    /**
     * 刷新完成
     */
    public void refreshFinish(){
        this.setRefreshing(false);
        if (iFootCallBack!=null)
            iFootCallBack.reset();
    }

    /**
     * 加载完成
     */
    public void loadFinish(boolean isSuccess){
        isLoadingMore = false;
        if (iFootCallBack!=null){
            iFootCallBack.loadFinish(isSuccess);
        }
    }

    /**
     * 加载完成无更多数据
     */
    public void loadFinishShowNoMore(){
        isLoadingMore = false;
        canLoadMore = false;
        if (iFootCallBack!=null){
            iFootCallBack.loadFinishShowNoMore();
        }
    }

    /**
     * 设置脚布局
     * @param footView
     */
    public void setFootView(View footView){
        if (footView instanceof IFootCallBack){
            this.iFootCallBack = (IFootCallBack) footView;
            if (childView instanceof ListView){
                ((ListView)childView).addFooterView(footView);
            }
        }else {
            throw new RuntimeException("FootView must be implements IFootCallBack");
        }
    }

}

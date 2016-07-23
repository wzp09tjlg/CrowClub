package com.sina.crowclub.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sina.crowclub.R;

/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.
 *
 * @author mrsimple
 */
public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;

    /**
     * listview实例
     */
    private ListView mListView;

    /**
     * 空数据视图
     */
//    private ViewGroup mEmptyLayout;

    /**
     * ListView的加载中footer
     */
    private XListViewFooter mListViewFooter;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    private boolean isMore = true;

    private AbsListView.OnScrollListener scrollListener;

    private AdapterView.OnItemClickListener itemClickListener;

    private int mListLayoutId;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    private boolean canTouch = true;

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayout);
        mListLayoutId = type.getResourceId(R.styleable.RefreshLayout_list_view_layout, R.layout.listview_layout);
        type.recycle();

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        init(context);
        setOnRefreshListener(this);

        mListView.setOnScrollListener(this);
        mListViewFooter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void init(Context context) {
        mListViewFooter = new XListViewFooter(context);
        mListView = (ListView) LayoutInflater.from(context).inflate(mListLayoutId, null);
        mListView.addFooterView(mListViewFooter);
        addView(mListView);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        mListView.setOnItemClickListener(itemClickListener);
    }

    public void setAdapter(ListAdapter adapter) {
        setAdapter(adapter, true);
    }

    public void setAdapter(ListAdapter adapter, boolean isMore) {
        this.isMore = isMore;

        // 保证在setAdapter之前添加顶部/底部视图
        mListView.setAdapter(adapter);
    }

    public ListView getListView() {
        return mListView;
    }

    public ListAdapter getAdapter() {
        return mListView.getAdapter();
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        super.setOnLongClickListener(l);
    }

    public void setPullRefresh(boolean isRefresh) {
        // 设置是否刷新
        setRefreshing(isRefresh);

        if (isLoading) {
            // 取消正在加载更多
            isLoading = false;
            if (isMore) {
                if (!mListViewFooter.isShown()) {
                    mListViewFooter.show();
                }
                mListViewFooter.setState(XListViewFooter.STATE_NORMAL);
            } else {
                mListViewFooter.hide();
            }
        }
    }

    public boolean isPullRefresh() {
        return isRefreshing();
    }

    public void setHeaderModeColor(int color){
        setProgressBackgroundColorSchemeColor(color);
    }

    public void setFooterModeColor(int color){
        mListViewFooter.setModeColor(color);
    }

    public void setFooterBgResource(@DrawableRes int color){
        mListViewFooter.setBgResource(color);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
    }

    public void setPullMore(boolean isMore) {
        if (isPullRefresh()) {
            setRefreshing(false);
        }

        this.isMore = isMore;
        isLoading = false;
        mListViewFooter.setState(XListViewFooter.STATE_NORMAL);
        if (isMore) {
            if (!mListViewFooter.isShown()) {
                mListViewFooter.show();
            }
        } else {
            mListViewFooter.hide();
        }
    }

    public boolean isMore() {
        return isMore;
    }

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        return super.showContextMenuForChild(originalView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!canTouch)
            return true;
        return super.onTouchEvent(ev);
    }

    //    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
//                if (canLoad()) {
//                    loadData();
//                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(!canTouch) {
            return true;
        }

        ensureTarget();
        boolean handle = super.onInterceptTouchEvent(ev);
//        LogUtil.d("parent==>onInterceptTouchEvent:" + handle + "canChildScrollDown:" + canChildScrollDown());

        boolean isVerticalSwip = false;
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mInitialDownX = getMotionEventX(ev, mActivePointerId);
                mInitialDownY = getMotionEventY(ev, mActivePointerId);
                if (mInitialDownX == -1 || mInitialDownY == -1) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isVerticalSwip = isVerticalSwip(ev);//解决和viewflow的手势冲突
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return handle && isVerticalSwip;
    }


    private boolean isVerticalSwip(MotionEvent ev) {
        final float x = getMotionEventX(ev, mActivePointerId);
        final float y = getMotionEventY(ev, mActivePointerId);
        if (y == -1 || x == -1) {
            return false;
        }
        final float xDiff = Math.abs(x - mInitialDownX);
        final float yDiff = Math.abs(y - mInitialDownY);
        return yDiff > xDiff;

    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private float getMotionEventX(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getX(ev, index);
    }

    public boolean canChildScrollDown() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < (absListView.getChildCount() - 1) || absListView.getChildAt(absListView.getChildCount() - 1)
                        .getBottom() > absListView.getPaddingBottom());
            } else {
                return ViewCompat.canScrollVertically(mTarget, 1) /*|| mTarget.getScrollY() > 0*/;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, 1);
        }
    }

    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
//                Log.d(TAG, child.getClass().getName() + "," + child.getClass().getSimpleName());
                if (!child.getClass().getName().equals(ProgressClassName)) {
                    mTarget = (ListView) child;
                    break;
                }
            }
        }
    }

    private ListView mTarget;
    private static final String ProgressClassName = "android.support.v4.widget.CircleImageView";
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = INVALID_POINTER;

    private float mInitialDownY;
    private float mInitialDownX;

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isMore && isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (mListView != null && mListView.getAdapter() != null) {
            int lastPositon = mListView.getLastVisiblePosition();
            int position = mListView.getAdapter().getCount() - 1;

//            if (lastPositon == position) {
//                int count = mListView.getChildCount();
//                View view = mListView.getChildAt(count - 1);
//                int botom = view.getBottom();
//                int height = mListView.getHeight();
//
//                return botom <= height;
//
//            }


            return lastPositon == position;
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    @Override
    public void setProgressViewOffset(boolean scale, int start, int end) {
        super.setProgressViewOffset(scale, start, end);
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);

            if (isRefreshing()) {
                setRefreshing(false);
            }

            //
            mOnLoadListener.onLoadMore();
        }
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListViewFooter.setState(XListViewFooter.STATE_LOADING);
        } else {
            mListViewFooter.setState(XListViewFooter.STATE_NORMAL);
            mListView.removeFooterView(mListViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        LogUtil.d("onScrollStateChanged");
        if (canLoad() && scrollState == SCROLL_STATE_IDLE) {
            loadData();
        }

        if (scrollListener != null) {
            scrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    // 滚动监听
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        // 滚动时到了最底部也可以加载更多
//        if (canLoad()) {
//            loadData();
//        }
        if (scrollListener != null) {
            scrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onRefresh() {
        // 如果当前正在加载更多，取消掉加载更多
        if (isMore && isLoading) {
            isLoading = false;
            mListViewFooter.setState(XListViewFooter.STATE_NORMAL);
        }

        if (mOnLoadListener != null) {
            mOnLoadListener.onRefresh();
        }
    }

    public void addHeaderView(View headerView) {
        if (mListView != null) {
            mListView.addHeaderView(headerView);
        }
    }


    public boolean hasHeaderView() {
        return mListView.getHeaderViewsCount() > 0;
    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }

    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public interface OnLoadListener {

        void onLoadMore();

        void onRefresh();


    }
}
package com.sina.crowclub.view.widget.SelfDragListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.sina.crowclub.view.adapter.AlbumAdapter;
import com.sina.crowclub.view.widget.RefreshList.XListViewFooter;

/**
 * Created by wu on 2016/8/9.
 * 拖拽排序listview实现动作
 * 1.获取down事件，在window上绘制一个拖拽view的映像
 * 2.监听move事件，不断刷新在window上的绘制view映像的位置
 *   并且针对当前的position和初始拖拽的position做动画,在动画完成之后交换数据。
 * 3.监听up事件，销毁在window上的view。并触发动画完成的接口。
 */
public class SelfDragListView extends ListView implements AbsListView.OnScrollListener {
    private Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    private int offSetX,offSetY;//初始点击的位置
    private int startDragItemPosition;//初始点击的Item的位置
    private int currentDragItemPostion;//当前拖动的item的weizhi
    private int dropPosition;//放手的时候item的位置
    private int mLowerBound,mUpperBound;//滑动上下边界
    private int mScaledTouchSlop; //系统能识别的滑动距离
    private ImageView mDragView;//拖动在window层上的imageView 的引用
    private boolean isMoving = false;
    private String LastAnimationID;//移动时候最后个动画的ID
    private Vibrator mVibrator;

    private boolean isDragSortAble = false;//是否打开拖拽排序开关

    private boolean isLoading =false;//是否在加载中
    private boolean isMore = false;//是否有更多的数据
    private XListViewFooter mListViewFooter;
    private OnItemDragSort mOnItemDragSort;
    private OnLoadListener mOnLoadListener;

    public SelfDragListView(Context context){
        super(context);
        init(context);
    }

    public SelfDragListView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mScaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        mListViewFooter = new XListViewFooter(mContext);
        mListViewFooter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        addFooterView(mListViewFooter);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        if(isDragSortAble){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    final int x = (int)ev.getX();
                    final int y = (int)ev.getY();
                    startDragItemPosition = pointToPosition(x,y);
                    if(startDragItemPosition == AdapterView.INVALID_POSITION)
                        break;
                    final ViewGroup dragView = (ViewGroup) getChildAt(startDragItemPosition
                            - getFirstVisiblePosition());
                    offSetX = x - dragView.getLeft();
                    offSetY = y - dragView.getTop();
                    currentDragItemPostion = startDragItemPosition;

                    dragView.setOnLongClickListener(new OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            final int height = getHeight();
                            mUpperBound = Math.min(y - mScaledTouchSlop, height / 3);
                            mLowerBound = Math.max(y + mScaledTouchSlop, height * 2 / 3);
                            if(currentDragItemPostion != AbsListView.INVALID_POSITION){
                                mVibrator.vibrate(50);//设置震动时间
                                dragView.destroyDrawingCache();
                                dragView.setDrawingCacheEnabled(true);
                                Bitmap bitmap = Bitmap.createBitmap(dragView.getDrawingCache());
                                startDrag(bitmap, (int)ev.getRawX(), (int)ev.getRawY());
                                dragView.setDrawingCacheEnabled(false);
                                hideDropItem();
                                isMoving = false;
                                return true;
                            }
                            return false;
                        }
                    });
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isDragSortAble && currentDragItemPostion != ListView.INVALID_POSITION
                && mDragView != null ){
            int x = (int)ev.getX();
            int y = (int)ev.getY();
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    onDrag(x, y ,(int) ev.getRawX() , (int) ev.getRawY());
                    if (!isMoving){
                    OnMove(x, y);
                    }
                    if (pointToPosition(x, y) != AdapterView.INVALID_POSITION){
                        break;
                    }

                    if (y >= getHeight() / 3) {
                        mUpperBound = getHeight() / 3;
                    }
                    if (y <= getHeight() * 2 / 3) {
                        mLowerBound = getHeight() * 2 / 3;
                    }
                    int speed = 0;
                    if (y > mLowerBound) {
                        if (getLastVisiblePosition() < getCount() - 1) {
                            speed = y > (getHeight() + mLowerBound) / 2 ? 16 : 4;
                        } else {
                            speed = 1;
                        }
                    } else if (y < mUpperBound) {
                        speed = y < mUpperBound / 2 ? -16 : -4;
                        if (getFirstVisiblePosition() == 0
                                && getChildAt(0).getTop() >= getPaddingTop()) {
                            speed = 0;
                        }
                    }
                    if (speed != 0) {
                        smoothScrollBy(speed, 30);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    stopDrag();
                    onDrop(x, y);
                    if(mOnItemDragSort != null){
                        mOnItemDragSort.onItemDragSort(startDragItemPosition,dropPosition);
                    }
                    requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void hideDropItem() {
       // ((AlbumAdapter) getAdapter()).setEndOperShow(false);
    }

    private void startDrag(Bitmap bitmap, int rawX, int rawY){
        stopDrag();

        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mLayoutParams.x = 0;
        mLayoutParams.y = rawY - offSetY;
        mLayoutParams.width = bitmap.getWidth();
        mLayoutParams.height =  bitmap.getHeight();
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mLayoutParams.windowAnimations = 0;

        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(bitmap);
        imageView.setBackgroundColor(Color.GRAY);
        imageView.setPadding(0, 0, 0, 0);
        mWindowManager.addView(imageView, mLayoutParams);
        mDragView = imageView;
    }

    private void onDrag(int x,int y ,int rawX, int rawY){
        int tempPostion = pointToPosition(x, y);
        if (tempPostion == AdapterView.INVALID_POSITION)
            return;

        if (mDragView != null) {
            mLayoutParams.x = 0;
            if(tempPostion != 0)
            mLayoutParams.y = rawY - offSetY;
            else
            mLayoutParams.y = rawY;

            mWindowManager.updateViewLayout(mDragView, mLayoutParams);
        }

        int tempPosition = pointToPosition(0, y);
        if(tempPosition != INVALID_POSITION){
            currentDragItemPostion = tempPosition;
        }

        int scrollY = 0;
        if(y < mUpperBound){
            scrollY = 8;
        }else if(y > mLowerBound){
            scrollY = -8;
        }

        if(scrollY != 0){
            if((currentDragItemPostion - getFirstVisiblePosition()) > 0){
                int top = getChildAt(currentDragItemPostion - getFirstVisiblePosition()).getTop();
                setSelectionFromTop(currentDragItemPostion, top + scrollY);
            }
        }
    }

    public void OnMove(int x, int y) {
        int dPosition = pointToPosition(x, y);
        if(dPosition == AbsListView.INVALID_POSITION)
            return;
        if (dPosition == startDragItemPosition){
                return;
            }
            dropPosition = dPosition;
            if (currentDragItemPostion != startDragItemPosition){
                currentDragItemPostion = startDragItemPosition;
            }
            int movecount = 0;
            if ((currentDragItemPostion != dropPosition)){
                movecount = dropPosition - currentDragItemPostion;
            }else{
                return;
            }

            int movecount_abs = Math.abs(movecount);

            if (dPosition != currentDragItemPostion) {
                ViewGroup dragGroup = (ViewGroup) getChildAt(currentDragItemPostion - getFirstVisiblePosition());
                dragGroup.setVisibility(View.INVISIBLE);
                int holdPosition;
                float to_x = 0;
                float to_y = 0;
                for (int i = 0; i < movecount_abs; i++) {
                    if (movecount > 0) {
                        holdPosition = currentDragItemPostion + i + 1;
                            to_x = 0;
                            to_y = -1;
                    }else{
                        holdPosition = currentDragItemPostion - i - 1;
                            to_x = 0;
                            to_y = 1;
                    }

                    final ViewGroup moveViewGroup = (ViewGroup) getChildAt(holdPosition - getFirstVisiblePosition());
                    Animation moveAnimation = getMoveAnimation(to_x, to_y);
                    moveViewGroup.startAnimation(moveAnimation);
                    if (holdPosition == dropPosition) {
                        LastAnimationID = moveAnimation.toString();
                    }
                    moveAnimation.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            isMoving = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (animation.toString().equalsIgnoreCase(LastAnimationID)) {
                                AlbumAdapter albumAdapter = (AlbumAdapter) getAdapter();
                                albumAdapter.exchange(startDragItemPosition,dropPosition);
                                startDragItemPosition = dropPosition;
                                currentDragItemPostion = dropPosition;
                                isMoving = false;
                            }
                            moveViewGroup.setAnimation(null);
                        }
                    });
                }
            }
    }

    public Animation getMoveAnimation(float toXValue, float toYValue) {
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF,toXValue,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, toYValue);
        mTranslateAnimation.setFillAfter(true);
        mTranslateAnimation.setDuration(300L);
        return mTranslateAnimation;
    }

    private void stopDrag(){
        if(mDragView != null){
            mWindowManager.removeView(mDragView);
            mDragView.setImageDrawable(null);
            mDragView = null;
        }
    }

    private void onDrop(int x, int y) {
        int tempPostion = pointToPosition(x, y);
        if (tempPostion != AdapterView.INVALID_POSITION) {
            dropPosition = tempPostion;
            AlbumAdapter albumAdapter = (AlbumAdapter)getAdapter();
            albumAdapter.setEndOperShow(true);
            albumAdapter.notifyDataSetChanged();
        } else if(y < getTop()){
            dropPosition = 0;
            AlbumAdapter albumAdapter = (AlbumAdapter)getAdapter();
            albumAdapter.setEndOperShow(true);
            albumAdapter.setHide(false);
            albumAdapter.notifyDataSetChanged();
        }
    }

    /*************************************************************/
    /***                        滑动加载更多                    ***/
    /*************************************************************/
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                if (canLoad()) {
                    loadData();
                }

                if(getFirstVisiblePosition() == 0){
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private boolean canLoad() {
        return isMore && isBottom() && !isLoading;
    }

    private boolean isBottom() {
        if (getAdapter() != null) {
            int lastPositon = getLastVisiblePosition();
            int position = getAdapter().getCount() - 1;
            return lastPositon == position;
        }
        return false;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            if(!isLoading) {
                setLoading(true);
                mOnLoadListener.onLoadListener();
            }
        }
    }

    public void setPullMore(boolean isMore) {
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

    /** 属性方法 **/
    public void setDragSortAble(boolean dragSortAble) {
        isDragSortAble = dragSortAble;
    }

    public void setMore(boolean more) {
         isMore = more;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListViewFooter.setState(XListViewFooter.STATE_LOADING);
        } else {
            mListViewFooter.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    public void setOnItemDragSort(OnItemDragSort mOnItemDragSort) {
        this.mOnItemDragSort = mOnItemDragSort;
    }

    public void setmOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    public interface OnItemDragSort{
        void onItemDragSort(int start,int end);
    }

    public interface OnLoadListener{
        void onLoadListener();
    }
}

package com.sina.crowclub.view.widget.DragList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by wu on 2016/8/8.
 */
public class DragListView extends ListView {
    private static final String TAG = DragListView.class.getSimpleName();
    /** View */
    private View dragImageView = null;
    /** Data */
    private Context mContext;
    private float windowX,windowY;
    private int startPosition;
    private int dragPosition;
    private int itemWidth,itemHeight;
    private int itemTotalCount;
    private float win_view_x,win_view_y;
    private ViewGroup dragItemView;

    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowParams = null;

    private boolean isMoving = false;

    private int dropPosition;

    private int mHorizontalSpacing = 0;
    private int mVerticalSpacing = 0;
    private String LastAnimationID;
    private int holdPosition;
    private int nColumns = 1;

    /**************************************/
    public DragListView(Context context){
        super(context);
        init(context);
    }

    public DragListView(Context context,AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    private void init(Context context){
        mContext = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            windowX = ev.getX();
            windowY = ev.getY();
            setOnItemClickListener(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean bool = true;
        if (dragImageView != null && dragPosition != AdapterView.INVALID_POSITION) {
            // 移动时候的对应x,y位置
            bool = super.onTouchEvent(ev);
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    windowX = (int) ev.getX();
                    windowY = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    onDrag(x, y ,(int) ev.getRawX() , (int) ev.getRawY());  //这里不断执行 不断的画设置拖拽Item的位置
                    if (!isMoving){
                        OnMove(x, y);
                    }
                    if (pointToPosition(x, y) != AdapterView.INVALID_POSITION){
                        break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    stopDrag();
                    onDrop(x, y);
                    requestDisallowInterceptTouchEvent(false);
                    break;

                default:
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setOnItemClickListener(final MotionEvent ev)
    {
        setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                int x = (int) ev.getX();// 长安事件的X位置
                int y = (int) ev.getY();// 长安事件的y位置

                startPosition = position;// 第一次点击的postion
                dragPosition = position;
                ViewGroup dragViewGroup = (ViewGroup) getChildAt(dragPosition - getFirstVisiblePosition());
                itemHeight = dragViewGroup.getHeight();
                itemWidth = dragViewGroup.getWidth();
                itemTotalCount = DragListView.this.getCount();
                // 如果特殊的这个不等于拖动的那个,并且不等于-1
                if (dragPosition != AdapterView.INVALID_POSITION) {
                    // 释放的资源使用的绘图缓存。如果你调用buildDrawingCache()手动没有调用setDrawingCacheEnabled(真正的),你应该清理缓存使用这种方法。
                    win_view_x = windowX - dragViewGroup.getLeft();//VIEW相对自己的X，半斤
                    win_view_y = windowY - dragViewGroup.getTop();//VIEW相对自己的y，半斤
                    dragItemView = dragViewGroup;
                    dragViewGroup.destroyDrawingCache();
                    dragViewGroup.setDrawingCacheEnabled(true);
                    Bitmap dragBitmap = Bitmap.createBitmap(dragViewGroup.getDrawingCache());
                    startDrag(dragBitmap, (int)ev.getRawX(),  (int)ev.getRawY()); // 在Windowmanager上生成一个假的图片，类似点击的Item被拖动
                    dragViewGroup.setDrawingCacheEnabled(false); //其实应该在使用之后需要关闭的 ？？
                    hideDropItem();   //隐藏被拖动的item，值adapter中设置相关控制
                    dragViewGroup.setVisibility(View.INVISIBLE);
                    isMoving = false;
                    requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                return false;
            }
        });
    }

    public void startDrag(Bitmap dragBitmap, int x, int y) {
        stopDrag();
        windowParams = new WindowManager.LayoutParams();// 获取WINDOW界面的
        windowParams.gravity = Gravity.TOP | Gravity.LEFT;
        //得到preview左上角相对于屏幕的坐标
        windowParams.x = (int)(x - win_view_x);
        windowParams.y = (int)(y  - win_view_y);
        //设置拖拽item的宽和高
        windowParams.width =  ( dragBitmap.getWidth());// 放大dragScale倍，可以设置拖动后的倍数
        windowParams.height = ( dragBitmap.getHeight());// 放大dragScale倍，可以设置拖动后的倍数
        this.windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        this.windowParams.format = PixelFormat.TRANSLUCENT;
        this.windowParams.windowAnimations = 0;
        ImageView iv = new ImageView(getContext());
        iv.setImageBitmap(dragBitmap);
        windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);// "window"
        windowManager.addView(iv, windowParams);
        dragImageView = iv;
    }

    private void stopDrag() {
        if (dragImageView != null) {
            windowManager.removeView(dragImageView);
            dragImageView = null;
        }
    }

    private void hideDropItem() {
//        ( getAdapter()).setShowDropItem(false);
    }

    private void onDrag(int x, int y , int rawx , int rawy) {
        if (dragImageView != null) {
            windowParams.x = (int)(rawx - win_view_x);
            windowParams.y = (int)(rawy - win_view_y);
            windowManager.updateViewLayout(dragImageView, windowParams);
        }
    }

    /** 移动的时候触发*/
    public void OnMove(int x, int y) {
        // 拖动的VIEW下方的POSTION
        int dPosition = pointToPosition(x, y);
        // 判断下方的POSTION是否是最开始2个不能拖动的

            if (dPosition == dragPosition){  //如果是拖动之后又回到原来的item的位置 就不做任何操作，返回空
                return;
            }
            dropPosition = dPosition;        // 这里有三个拖拽位置 start位置  drag位置  和 drop位置 start 和  drag 位置应该是一样的，drop位置是不一样位置
            if (dragPosition != startPosition){
                dragPosition = startPosition;
            }
            int movecount;
            //拖动的=开始拖的，并且 拖动的 不等于放下的
            if ((dragPosition == startPosition) && (dragPosition != dropPosition)){
                //移需要移动的动ITEM数量
                movecount = dropPosition - dragPosition;  //终点位置item的position  -  起点位置item的position  结果大于0 表示后移 小于0 表示前移
            }else{
                //移需要移动的动ITEM数量为0
                movecount = 0;
            }
            if(movecount == 0){
                return;
            }

            int movecount_abs = Math.abs(movecount);

            if (dPosition != dragPosition) {
                //dragGroup设置为不可见
                ViewGroup dragGroup = (ViewGroup) getChildAt(dragPosition);
                dragGroup.setVisibility(View.INVISIBLE);
                float to_x = 1;// 当前下方positon
                float to_y;// 当前下方右边positon
                //x_vlaue移动的距离百分比（相对于自己长度的百分比）
                float x_vlaue = ((float) mHorizontalSpacing / (float) itemWidth) + 1.0f;
                //y_vlaue移动的距离百分比（相对于自己宽度的百分比）
                float y_vlaue = ((float) mVerticalSpacing / (float) itemHeight) + 1.0f;
                for (int i = 0; i < movecount_abs; i++) {
                    to_x = x_vlaue;
                    to_y = y_vlaue;
                    //像左
                    if (movecount > 0) { //拖动的Item向后移，其他的item前移
                        // 判断是不是同一行的
                       /* holdPosition = dragPosition + i + 1;
                        if (dragPosition / nColumns == holdPosition / nColumns) {
                            to_x = - x_vlaue;
                            to_y = 0;
                        } else if (holdPosition % nColumns == 0) { //固定的个数
                            to_x = (nColumns - 1) * x_vlaue;
                            to_y = - y_vlaue;
                        } else {
                            to_x = - x_vlaue;
                            to_y = 0;
                        } */
                        to_x = 0;
                        to_y = -movecount;
                    }else{ // 拖动的item前移，其他的item后移
                        //向右,下移到上，右移到左
                       /* holdPosition = dragPosition - i - 1;
                        if (dragPosition / nColumns == holdPosition / nColumns) {
                            to_x = x_vlaue;
                            to_y = 0;
                        } else if((holdPosition + 1) % nColumns == 0){
                            to_x = -1 * (nColumns - 1) * x_vlaue;
                            to_y = y_vlaue;
                        }else{
                            to_x = x_vlaue;
                            to_y = 0;
                        } */
                        to_x = 0;
                        to_y = movecount;
                    }
                    ViewGroup moveViewGroup = (ViewGroup) getChildAt(holdPosition);
                    Animation moveAnimation = getMoveAnimation(to_x, to_y);
                    moveViewGroup.startAnimation(moveAnimation);
                    //如果是最后一个移动的，那么设置他的最后个动画ID为LastAnimationID
                    if (holdPosition == dropPosition) {
                        LastAnimationID = moveAnimation.toString();
                    }
                    moveAnimation.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub
                            isMoving = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // TODO Auto-generated method stub
                            // 如果为最后个动画结束，那执行下面的方法
                            if (animation.toString().equalsIgnoreCase(LastAnimationID)) {
                                //DragAdapter mDragAdapter = (DragAdapter) getAdapter();
                                //mDragAdapter.exchange(startPosition,dropPosition);  //最终的数据的考换，然后刷新一下
                                startPosition = dropPosition;
                                dragPosition = dropPosition;
                                isMoving = false;   //这里的参数表示一个动画已经完成，虽然被拖拽的那个item还在，但是接下来要做的动画是当前点和接下来的被替换点
                            }
                        }
                    });
                }
            }
    }

    private void onDrop(int x, int y) {
        // 根据拖动到的x,y坐标获取拖动位置下方的ITEM对应的POSTION
        int tempPostion = pointToPosition(x, y);
        if (tempPostion != AdapterView.INVALID_POSITION) {
            dropPosition = tempPostion;
            //DragAdapter mDragAdapter = (DragAdapter) getAdapter();
            //显示刚拖动的ITEM
            //mDragAdapter.setShowDropItem(true);
            //刷新适配器，让对应的ITEM显示
            //mDragAdapter.notifyDataSetChanged();
        }else{
            //这里要做的是 将移除的item 从userGrid中移除。然后添加到otherGrid中
            //这里得做一个回调，将数据进行处理
            View view =  getChildAt(dragPosition - getFirstVisiblePosition());  //被拖拽的item的位置
            //mRemoveUserItem.doRemoveUserItem(this,view,dragPosition);
        }
    }

    public Animation getMoveAnimation(float toXValue, float toYValue) {
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF,toXValue,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, toYValue);// 当前位置移动到指定位置
        mTranslateAnimation.setFillAfter(true);// 设置一个动画效果执行完毕后，View对象保留在终止的位置。  所以在移动之后不会再变化，再移动 再做变化
        mTranslateAnimation.setDuration(300L);
        return mTranslateAnimation;
    }
}

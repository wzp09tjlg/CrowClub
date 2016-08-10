package com.sina.crowclub.view.widget.SelfList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.widget.DragList.adapter.DragAdapter;

/**
 * Created by wu on 2016/8/9.
 */
public class SelfListView extends ListView {
   /** Data */
   private Context mContext;
    private int startPosition; //准备开始拖动时的位置
    private int dragPosition; //准备拖动时的位置
    private int dropPosition; //准备释放时的位置
    private int dragPoint;// 在当前数据项中的位置
    private int dragOffset;// 当前视图和屏幕的距离(这里只使用了y方向上)

    /** WindowManager管理器 */
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowParams = null;

    /** 拖动的时候对应ITEM的VIEW */
    private View dragImageView = null;

    private boolean isMoving;

    /** 点击时候对应整个界面的X位置 */
    public int windowX;
    /** 点击时候对应整个界面的Y位置 */
    public int windowY;
    /** 屏幕上的X */
    private int win_view_x;  //相对window 左边上角的X位置
    /** 屏幕上的Y*/
    private int win_view_y;  //相对window 左上角的Y位置

    /** item高 */
    private int itemHeight;
    /** item宽 */
    private int itemWidth;

    /** 临时变量，用作交换动画时的item的位置 */
    private int holdPosition;

    /** 移动时候最后个动画的ID */
    private String LastAnimationID;

   /****************************************/
    public SelfListView(Context context) {
        super(context);
        init(context);
    }

    public SelfListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public SelfListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mContext = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            windowX = (int) ev.getX();
            windowY = (int) ev.getY();

            int x = (int) ev.getX();// 获取相对与ListView的x坐标
            int y = (int) ev.getY();// 获取相应与ListView的y坐标

            dragPosition = pointToPosition(x, y);
            startPosition = dragPosition;

            // 无效不进行处理
            if (dragPosition == AdapterView.INVALID_POSITION) {
                return super.onInterceptTouchEvent(ev);
            }
            // 获取当前位置的视图(可见状态)
            ViewGroup itemView = (ViewGroup) getChildAt(dragPosition
                    - getFirstVisiblePosition());
            itemHeight = itemView.getHeight();
            itemWidth = itemView.getWidth();

            // 获取到的dragPoint其实就是在你点击指定item项中的高度.
            dragPoint = y - itemView.getTop();
            // 这个值是固定的:其实就是ListView这个控件与屏幕最顶部的距离（一般为标题栏+状态栏）.
            dragOffset = (int) (ev.getRawY() - y);

            // 获取可拖拽的图标
            View dragger = itemView.findViewById(R.id.img_album_drag);

            if (dragger != null && x < dragger.getLeft() + 20) {//img的宽度加 20。便于操作
                win_view_x = windowX - itemView.getLeft();//VIEW相对自己的X，半斤
                win_view_y = windowY - itemView.getTop();//VIEW相对自己的y，半斤
                itemView.destroyDrawingCache();
                itemView.setBackgroundColor(Color.GRAY);
                itemView.setDrawingCacheEnabled(true);// 开启cache.
                Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());// 根据cache创建一个新的bitmap对象.
                itemView.setDrawingCacheEnabled(false);
//                hideDropItem();   //隐藏被拖动的item，值adapter中设置相关控制
//                itemView.setVisibility(View.INVISIBLE);
                isMoving = false;
                startDrag(bm, (int)ev.getRawX(),  (int)ev.getRawY());
            }
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (dragImageView != null && dragPosition != AdapterView.INVALID_POSITION) {
            // 移动时候的对应x,y位置
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    windowX = (int) ev.getX();
                    windowY = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    onDrag(x, y ,(int) ev.getRawX() , (int) ev.getRawY());  //这里不断执行 不断的画设置拖拽Item的位置
                     /*if (!isMoving){
                        OnMove(x, y);
                    }
                    if (pointToPosition(x, y) != AdapterView.INVALID_POSITION){
                        break;
                    } */
                    break;
                case MotionEvent.ACTION_UP:
                   /* stopDrag();
                    onDrop(x, y);
                    requestDisallowInterceptTouchEvent(false); */
                    break;

                default:
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void startDrag(Bitmap dragBitmap, int x, int y) {
        stopDrag();
        windowParams = new WindowManager.LayoutParams();// 获取WINDOW界面的
        //Gravity.TOP|Gravity.LEFT;这个必须加
        windowParams.gravity = Gravity.TOP | Gravity.LEFT;
        //得到preview左上角相对于屏幕的坐标
        windowParams.x = x - win_view_x;
        windowParams.y = y  - win_view_y;
        //设置拖拽item的宽和高
        windowParams.width = (int) (dragBitmap.getWidth());// 放大dragScale倍，可以设置拖动后的倍数
        windowParams.height = (int) (dragBitmap.getHeight());// 放大dragScale倍，可以设置拖动后的倍数
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


    /** 在拖动的情况 */
    private void onDrag(int x, int y , int rawx , int rawy) {
        if (dragImageView != null) {
            windowParams.x = rawx - win_view_x;
            windowParams.y = rawy - win_view_y;
            windowManager.updateViewLayout(dragImageView, windowParams);
        }
    }

    /** 移动的时候触发*/
    public void OnMove(int x, int y) {
        // 拖动的VIEW下方的POSTION
        LogUtil.e("x:" + x + "  y:" + y);
        int dPosition = pointToPosition(x, y);
        LogUtil.e("dPosition:" + dPosition );
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

        LogUtil.e("dPosition :" + dPosition
                + "   dragPosition:" + dragPosition);

            if (dPosition != dragPosition) {
                //dragGroup设置为不可见
                ViewGroup dragGroup = (ViewGroup) getChildAt(dragPosition);
                dragGroup.setVisibility(View.INVISIBLE);
                float to_x = 0;// 当前下方positon
                float to_y;// 当前下方右边positon
                for (int i = 0; i < movecount_abs; i++) {
                    to_x = 0;
                    to_y = itemHeight;
                    //像左
                    if (movecount > 0) { //拖动的item下移 其他上移
                        // 判断是不是同一行的
                        holdPosition = dragPosition + i + 1;
                        to_y = itemHeight;
                    }else{ // //拖动的item上移 其他下移
                        holdPosition = dragPosition - i - 1;
                        to_y = -1 * itemHeight;
                    }
                    LogUtil.e("holdPosition:" + holdPosition);
                    ViewGroup moveViewGroup = (ViewGroup) getChildAt(holdPosition);
                    if(moveViewGroup == null){
                        LogUtil.e("moveViewGroup: is null"  );
                    }else{
                        LogUtil.e("moveViewGroup: is not null " );
                    }
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
                                DragAdapter mDragAdapter = (DragAdapter) getAdapter();
                                mDragAdapter.exchange(startPosition,dropPosition);  //最终的数据的考换，然后刷新一下
                                startPosition = dropPosition;
                                dragPosition = dropPosition;
                                isMoving = false;   //这里的参数表示一个动画已经完成，虽然被拖拽的那个item还在，但是接下来要做的动画是当前点和接下来的被替换点
                            }
                        }
                    });
                }
            }
    }

    /** 获取移动动画 */
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

    /** 停止拖动 ，释放并初始化 */
    private void stopDrag() {
        if (dragImageView != null) {
            windowManager.removeView(dragImageView);
            dragImageView = null;
        }
    }


    /** 在松手下放的情况 */
    private void onDrop(int x, int y) {
        // 根据拖动到的x,y坐标获取拖动位置下方的ITEM对应的POSTION
        int tempPostion = pointToPosition(x, y);
        if (tempPostion != AdapterView.INVALID_POSITION) {
            dropPosition = tempPostion;
//            DragAdapter mDragAdapter = (DragAdapter) getAdapter();
            //显示刚拖动的ITEM
//            mDragAdapter.setShowDropItem(true);
            //刷新适配器，让对应的ITEM显示
//            mDragAdapter.notifyDataSetChanged();
        }else{
            //这里要做的是 将移除的item 从userGrid中移除。然后添加到otherGrid中
            //这里得做一个回调，将数据进行处理
            View view =  getChildAt(dragPosition - getFirstVisiblePosition());  //被拖拽的item的位置
            //mRemoveUserItem.doRemoveUserItem(this,view,dragPosition);
        }
    }

    /** 隐藏 放下 的ITEM*/
    private void hideDropItem() {
//        ((DragAdapter) getAdapter()).setShowDropItem(false);
    }
}


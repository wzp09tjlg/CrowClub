package com.sina.crowclub.view.widget.DragListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.sina.crowclub.R;
import com.sina.crowclub.view.widget.DragList.adapter.DragAdapter;

public class DragListView extends ListView {

    private WindowManager windowManager;// windows窗口控制类
    private WindowManager.LayoutParams windowParams;// 用于控制拖拽项的显示的参数

    private int scaledTouchSlop;// 判断滑动的一个距离,scroll的时候会用到(24)

    private ImageView dragImageView;// 被拖拽的项(item)，其实就是一个ImageView
    private int dragSrcPosition;// 手指拖动项原始在列表中的位置
    private int dragPosition;// 手指点击准备拖动的时候,当前拖动项在列表中的位置.

    private int dragPoint;// 在当前数据项中的位置
    private int dragOffset;// 当前视图和屏幕的距离(这里只使用了y方向上)

    private int upScrollBounce;// 拖动的时候，开始向上滚动的边界
    private int downScrollBounce;// 拖动的时候，开始向下滚动的边界

    private final static int step = 1;// ListView 滑动步伐.

    private int current_Step;// 当前步伐.

    private int temChangId;// 临时交换id

    private boolean isLock;// 是否上锁.

    /**
     * 屏幕上的X
     */
    private int win_view_x;  //相对window 左边上角的X位置
    /**
     * 屏幕上的Y
     */
    private int win_view_y;  //相对window 左上角的Y位置

    /**
     * 点击时候对应整个界面的X位置
     */
    public int windowX;
    /**
     * 点击时候对应整个界面的Y位置
     */
    public int windowY;

    public boolean isMoving = false;

    /**
     * 长按时候对应postion
     */
    //public int dragPosition;
    /**
     * Up后对应的ITEM的Position
     */
    private int dropPosition;
    /**
     * 开始拖动的ITEM的Position
     */
    private int startPosition;

    /** 临时变量，用作交换动画时的item的位置 */
    private int holdPosition;

    /** 移动时候最后个动画的ID */
    private String LastAnimationID;

    public void setLock(boolean isLock) {
        this.isLock = isLock;
    }

    public DragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /***
     * touch事件拦截 在这里我进行相应拦截，
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 按下
        if (ev.getAction() == MotionEvent.ACTION_DOWN && !isLock) {
            int x = (int) ev.getX();// 获取相对与ListView的x坐标
            int y = (int) ev.getY();// 获取相应与ListView的y坐标
            temChangId = dragSrcPosition = dragPosition = pointToPosition(x, y);
            // 无效不进行处理
            if (dragPosition == AdapterView.INVALID_POSITION) {
                return super.onInterceptTouchEvent(ev);
            }

            // 获取当前位置的视图(可见状态)
            ViewGroup itemView = (ViewGroup) getChildAt(dragPosition
                    - getFirstVisiblePosition());

            // 获取到的dragPoint其实就是在你点击指定item项中的高度.
            dragPoint = y - itemView.getTop();
            // 这个值是固定的:其实就是ListView这个控件与屏幕最顶部的距离（一般为标题栏+状态栏）.
            dragOffset = (int) (ev.getRawY() - y);

            // 获取可拖拽的图标
            View dragger = itemView.findViewById(R.id.img_album_drag);

            // x > dragger.getLeft() - 20这句话为了更好的触摸（-20可以省略）
            if (dragger != null && x < dragger.getLeft() + 20) {

                upScrollBounce = getHeight() / 3;// 取得向上滚动的边际，大概为该控件的1/3
                downScrollBounce = getHeight() * 2 / 3;// 取得向下滚动的边际，大概为该控件的2/3
                itemView.setBackgroundColor(Color.BLUE);
                itemView.setDrawingCacheEnabled(true);// 开启cache.
                Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());// 根据cache创建一个新的bitmap对象.
                startDrag(bm, y);// 初始化影像
            }

            windowX = (int) ev.getX();
            windowY = (int) ev.getY();

            win_view_x = windowX - dragger.getLeft();//VIEW相对自己的X，半斤
            win_view_y = windowY - dragger.getTop();//VIEW相对自己的y，半斤
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 触摸事件处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // item的view不为空，且获取的dragPosition有效
        if (dragImageView != null && dragPosition != INVALID_POSITION
                && !isLock) {
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    int upY = (int) ev.getY();
                    stopDrag();
                    onDrop(upY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int) ev.getY();
                    //onDrag(moveY);

                    onDrag(x, y, (int) ev.getRawX(), (int) ev.getRawY());  //这里不断执行 不断的画设置拖拽Item的位置
                    if (!isMoving) {
                        OnMove(x, y);
                    }
                    if (pointToPosition(x, y) != AdapterView.INVALID_POSITION) {
                        break;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
                default:
                    break;
            }
            return true;// 取消ListView滑动.
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 准备拖动，初始化拖动项的图像
     *
     * @param bm
     * @param y
     */
    private void startDrag(Bitmap bm, int y) {
        // stopDrag();
        /***
         * 初始化window.
         */
        windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP;
        windowParams.x = 0;
        windowParams.y = y - dragPoint + dragOffset;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE// 不需获取焦点
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE// 不需接受触摸事件
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON// 保持设备常开，并保持亮度不变。
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;// 窗口占满整个屏幕，忽略周围的装饰边框（例如状态栏）。此窗口需考虑到装饰边框的内容。

        // windowParams.format = PixelFormat.TRANSLUCENT;// 默认为不透明，这里设成透明效果.
        windowParams.windowAnimations = 0;// 窗口所使用的动画设置

        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bm);
        windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(imageView, windowParams);
        dragImageView = imageView;

    }

    /**
     * 拖动执行，在Move方法中执行
     *
     * @param y
     */
    public void onDrag(int y) {
        int drag_top = y - dragPoint;// 拖拽view的top值不能＜0，否则则出界.
        if (dragImageView != null && drag_top >= 0) {
            windowParams.alpha = 0.5f;
            windowParams.y = y - dragPoint + dragOffset;
            windowManager.updateViewLayout(dragImageView, windowParams);// 时时移动.
        }
        // 为了避免滑动到分割线的时候，返回-1的问题
        int tempPosition = pointToPosition(0, y);
        if (tempPosition != INVALID_POSITION) {
            dragPosition = tempPosition;
        }

        onChange(y);// 时时交换

        //doScroller(y);// listview移动.
    }

    /**
     * 在拖动的情况
     */
    private void onDrag(int x, int y, int rawx, int rawy) {
        if (dragImageView != null) {
            windowParams.x = rawx - win_view_x;
            windowParams.y = rawy - win_view_y;
            windowManager.updateViewLayout(dragImageView, windowParams);
        }
    }

    /**
     * 移动的时候触发
     */
    public void OnMove(int x, int y) {
        // 拖动的VIEW下方的POSTION
        int dPosition = pointToPosition(x, y);
        // 判断下方的POSTION是否是最开始2个不能拖动的
        if (dPosition == dragPosition) {  //如果是拖动之后又回到原来的item的位置 就不做任何操作，返回空
            return;
        }
        dropPosition = dPosition;        // 这里有三个拖拽位置 start位置  drag位置  和 drop位置 start 和  drag 位置应该是一样的，drop位置是不一样位置
        if (dragPosition != startPosition) {
            dragPosition = startPosition;
        }
        int movecount;
        //拖动的=开始拖的，并且 拖动的 不等于放下的
        if ((dragPosition == startPosition) && (dragPosition != dropPosition)) {
            //移需要移动的动ITEM数量
            movecount = dropPosition - dragPosition;  //终点位置item的position  -  起点位置item的position  结果大于0 表示后移 小于0 表示前移
        } else {
            //移需要移动的动ITEM数量为0
            movecount = 0;
        }
        if (movecount == 0) {
            return;
        }

        if (dPosition != dragPosition) {
            //dragGroup设置为不可见
            ViewGroup dragGroup = (ViewGroup) getChildAt(dragPosition);
            dragGroup.setVisibility(View.INVISIBLE);
            float to_x = 0;//
            float to_y = movecount;//


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
                        DragAdapter mDragAdapter = (DragAdapter) getAdapter();
                        mDragAdapter.exchange(startPosition, dropPosition);  //最终的数据的考换，然后刷新一下
                        startPosition = dropPosition;
                        dragPosition = dropPosition;
                        isMoving = false;   //这里的参数表示一个动画已经完成，虽然被拖拽的那个item还在，但是接下来要做的动画是当前点和接下来的被替换点
                    }
                }
            });
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

    /***
     * ListView的移动.
     * 要明白移动原理：当我移动到下端的时候，ListView向上滑动，当我移动到上端的时候，ListView要向下滑动。正好和实际的相反.
     *
     */

    public void doScroller(int y) {
        // Log.e("jj", "y=" + y);
        // Log.e("jj", "upScrollBounce=" + upScrollBounce);
        // ListView需要下滑
        if (y < upScrollBounce) {
            current_Step = step + (upScrollBounce - y) / 10;// 时时步伐
        }// ListView需要上滑
        else if (y > downScrollBounce) {
            current_Step = -(step + (y - downScrollBounce)) / 10;// 时时步伐
        } else {
            current_Step = 0;
        }

        // 获取你拖拽滑动到位置及显示item相应的view上（注：可显示部分）（position）
        View view = getChildAt(dragPosition - getFirstVisiblePosition());
        // 真正滚动的方法setSelectionFromTop()
        setSelectionFromTop(dragPosition, view.getTop() + current_Step);

    }

    /**
     * 停止拖动，删除影像
     */
    public void stopDrag() {
        if (dragImageView != null) {
            windowManager.removeView(dragImageView);
            dragImageView = null;
        }
    }

    /***
     * 拖动时时change
     */
    private void onChange(int y) {
        // 数据交换
        if (dragPosition < getAdapter().getCount()) {
            DragListAdapter adapter = (DragListAdapter) getAdapter();
            //adapter.isHidden = false;
            if (dragPosition != temChangId) {
                adapter.update(temChangId, dragPosition);
                temChangId = dragPosition;// 将点击最初所在位置position付给临时的，用于判断是否换位.
            }
        }

        // 为了避免滑动到分割线的时候，返回-1的问题
        int tempPosition = pointToPosition(0, y);
        if (tempPosition != INVALID_POSITION) {
            dragPosition = tempPosition;
        }

        // 超出边界处理(如果向上超过第二项Top的话，那么就放置在第一个位置)
        if (y < getChildAt(0).getTop()) {
            // 超出上边界
            dragPosition = 0;
            // 如果拖动超过最后一项的最下边那么就防止在最下边
        } else if (y > getChildAt(getChildCount() - 1).getBottom()) {
            // 超出下边界
            dragPosition = getAdapter().getCount() - 1;
        }

    }

    /**
     * 拖动放下的时候
     *
     * @param y
     */
    public void onDrop(int y) {
        // 数据交换
        if (dragPosition < getAdapter().getCount()) {
            DragListAdapter adapter = (DragListAdapter) getAdapter();
            //adapter.isHidden = false;
            adapter.notifyDataSetChanged();// 刷新.
        }
    }

}


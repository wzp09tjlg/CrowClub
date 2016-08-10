package com.sina.crowclub.view.widget.SelfDragListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by wu on 2016/8/9.
 */
public class SelfDragListView extends ListView {
    //初始点击的位置
    private int startDragX,startDragY;
    //相对window的位置
    private int windowX,windowY;
    //初始点击的Item的位置
    private int startDragItemPosition;

    //滑动上下边界
    private int mLowerBound,mUpperBound;

    public SelfDragListView(Context context){
        super(context);
        init(context);
    }

    public SelfDragListView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }

    private void init(Context context){

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                 int x = (int)ev.getX();
                final int y = (int)ev.getY();
                startDragItemPosition = pointToPosition(x,y);
                if(startDragItemPosition == AdapterView.INVALID_POSITION)
                    break;
                final ViewGroup dragView = (ViewGroup) getChildAt(startDragItemPosition - getFirstVisiblePosition());
                startDragX = x - dragView.getLeft();
                startDragY = y - dragView.getTop();
                windowX = ((int) ev.getRawX()) - x;
                windowY = ((int) ev.getRawY()) - y;

                dragView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final int height = getHeight();
                        mUpperBound = Math.min(y - mScaledTouchSlop, height / 3);
                        mLowerBound = Math.max(y + mScaledTouchSlop, height * 2 / 3);
                        mDragCurrentPostion = mDragStartPosition = itemNum;

                        item.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
                        startDragging(bitmap, x, y);
                        return true;
                    }
                });
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}

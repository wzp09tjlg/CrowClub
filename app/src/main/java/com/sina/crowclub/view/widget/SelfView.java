package com.sina.crowclub.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sina.crowclub.utils.LogUtil;

/**
 * Created by wu on 2016/8/9.
 */
public class SelfView extends View {

    public SelfView(Context context){
        super(context);
    }

    public SelfView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtil.e("ACTION_DOWN x:" + x + "  y:" + y);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e("ACTION_MOVE x:" + x + "  y:" + y);
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e("ACTION_UP x:" + x + "  y:" + y);
                break;
        }
        return super.onTouchEvent(event);
    }


}

package com.sina.crowclub.view.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by wu on 2016/7/15.
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
    private final String TAG = BaseFragmentActivity.class.getSimpleName();

    /** 提供的一个实例view的方法 */
    protected <T extends View> T $(int id){
        return (T)super.findViewById(id);
    }

    protected <T extends View> T $(View view,int id){
        return (T)view.findViewById(id);
    }

}

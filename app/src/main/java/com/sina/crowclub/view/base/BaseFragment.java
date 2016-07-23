package com.sina.crowclub.view.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by wu on 2016/7/23.
 */
public class BaseFragment extends Fragment {

    //基类添加的实例化方法
    protected <T extends View> T $(View view,int id){
        return (T)view.findViewById(id);
    }
}

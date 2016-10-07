package com.sina.crowclub.view.fragment.listener;

import android.view.View;

/**
 * Created by wu on 2016/7/25.
 * 创建一个比较常用的事件监听类 
 */
public interface OnListItemClickListener<T> {
    void onListItemClickListener(View view,int position,T t);
}

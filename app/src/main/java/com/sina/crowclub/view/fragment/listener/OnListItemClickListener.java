package com.sina.crowclub.view.fragment.listener;

import android.view.View;

/**
 * Created by wu on 2016/7/25.
 */
public interface OnListItemClickListener<T> {
    void onListItemClickListener(View view,int position,T t);
}

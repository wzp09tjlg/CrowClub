package com.sina.crowclub.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TabHost;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/11/14.
 * 简单的控件TabHost的使用
 */
public class TabsActivity extends BaseFragmentActivity {
    /** View */
    private TabHost tabHost;
    /**************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initViews();
}

    private void initViews(){
        tabHost = $(R.id.tabs);

        initData();
    }

    private void initData(){

    }
}

package com.sina.crowclub.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/12/6.
 * 这个页是针对安卓中的是四种启动模式测试，
 * 简单的基础啊 看过了就忘，还是得多测试
 */
public class LaunchModeActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    /** View */
    private Button btnStandard;
    private Button btnSingleTop;
    private Button btnSingleTask;
    private Button btnSingleInstance;

    /** Data */
    private Context mContext;

    /********************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        initViews();
    }

    private void initViews(){
        btnStandard = $(R.id.btn_standard);
        btnSingleTop = $(R.id.btn_singleTop);
        btnSingleTask = $(R.id.btn_singleTask);
        btnSingleInstance = $(R.id.btn_singleInstance);
        initData();
    }

    private void initData(){
        mContext = this;

        btnStandard.setOnClickListener(this);
        btnSingleTop.setOnClickListener(this);
        btnSingleTask.setOnClickListener(this);
        btnSingleInstance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_standard:
                break;
            case R.id.btn_singleTop:
                break;
            case R.id.btn_singleTask:
                break;
            case R.id.btn_singleInstance:
                break;
        }
    }
}

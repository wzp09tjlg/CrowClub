package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.R;
import com.sina.crowclub.view.activity.launchMode.SingleInstanceAActivity;
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
    protected void onCreate(Bundle savedInstanceState) {
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
                //standard 模式的启动 都会创建activity实例，所以会存在多个对象
                //都会走onCreate方法
                break;
            case R.id.btn_singleTop:
                //singleTop的模式启动 当activity在栈顶时  会走newIntent方法 其他的activity会走onCreate方法
                //在顶部的activity会执行newIntent方法，不在顶部的activity会执行onCreate方法
                break;
            case R.id.btn_singleTask:
                //singleTask的模式启动 该模式下的activity会走newIntent方法，在其顶上的activity会被销毁掉
                //会执行newIntent方法
                break;
            case R.id.btn_singleInstance:
                //singleInstance 模式启动的是单独一个栈，和应用的栈是两个栈。可以在运行时查看运行栈的信息 adb shell dumpsys activity
                Intent intent = new Intent(this,SingleInstanceAActivity.class);
                startActivity(intent);
                break;
        }
    }
}

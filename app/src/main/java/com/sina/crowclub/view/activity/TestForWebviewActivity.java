package com.sina.crowclub.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.fragment.TestOtherFragment;
import com.sina.crowclub.view.fragment.WebViewFragment;

/**
 * Created by wu on 2016/12/1.
 * 1.这里测试下 添加fragment  测试webview的生命周期 因为什么再走“reload”
 * 2.如果对fragment的对象进行管控，查看是否还回出现添加在fragment中的webview生命周期异常
 *
 */
public class TestForWebviewActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    /** View */
    private Button mBtnChange;
    private Button mBtnShow;
    /** Data */
    private Context mContext ;
    private WebViewFragment webViewFragment;
    private TestOtherFragment otherFragment;

    private  FragmentManager fm = null;
    private FragmentTransaction transaction = null;
    /****************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_for_webview);
        initViews();
    }

    private void initViews(){
        mBtnChange = $(R.id.btn_change);
        mBtnShow = $(R.id.btn_show);

        initData();
    }

    private void initData(){
        webViewFragment = new WebViewFragment();
        otherFragment = new TestOtherFragment();

         fm = getSupportFragmentManager();
         transaction = fm.beginTransaction();
//        transaction.add(otherFragment,"otherFragment");
//        transaction.add(webViewFragment,"webViewFragment");
        transaction.add(R.id.layout_contain,otherFragment);
        transaction.add(R.id.layout_contain,webViewFragment);
        transaction.commit();

        mBtnChange.setOnClickListener(this);
        mBtnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){//当前来看 是没有走生命周期的。当然使用fragmentAdapter 也是不会走生命周期的
            case R.id.btn_change:
                fm = getSupportFragmentManager();
                transaction = fm.beginTransaction();
                transaction.hide(webViewFragment);
                transaction.commit();
                 break;
            case R.id.btn_show:
                fm = getSupportFragmentManager();
                transaction = fm.beginTransaction();
                transaction.show(webViewFragment);
                transaction.commit();
                break;
        }
    }

}

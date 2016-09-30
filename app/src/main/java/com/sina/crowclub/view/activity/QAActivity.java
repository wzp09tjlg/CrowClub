package com.sina.crowclub.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/9/29.
 */
public class QAActivity extends BaseFragmentActivity {
    /** View */
    /** Data */
    /*************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        initViews();
    }

    private void initViews(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment feedback = FeedbackAPI.getFeedbackFragment();
        transaction.replace(R.id.layout_content, feedback);
        transaction.commit();
    }
}

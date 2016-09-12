package com.sina.crowclub.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragment;

/**
 * Created by wu on 2016/8/23.
 */
public class HandlerThreeFragment extends BaseFragment implements
    View.OnClickListener
{
    private static final String TAG = HandlerOneFragment.class.getSimpleName();
    /** View */
    private Button btnPrePage;
    private Button btnNextPage;
    private ViewPager mViewPager;

    /** Date */
    private Context mContext;
    private Handler mHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e("cur Thread is:" + Thread.currentThread().getId());
        }
    };

    /************************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handler_two,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        btnPrePage = $(view,R.id.btn_pre_page);
        btnNextPage = $(view,R.id.btn_next_page);
        mViewPager = $(view,R.id.viewpager);

        initData();
    }

    private void initData(){}

    @Override
    public void onClick(View v) {

    }
}

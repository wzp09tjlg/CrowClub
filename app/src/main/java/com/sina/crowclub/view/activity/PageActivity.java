package com.sina.crowclub.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.SelfPageView.PageView;

/**
 * Created by wu on 2016/10/24.
 */
public class PageActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    /** View */
    private PageView pageView;
    private Button btnAdd;
    private Button btnMinus;
    /** Data */
    private Context mContext;
    private int count = 0;
    private int countTotal = 20;

    private int pageWidth = 0;
    private int pageHeight = 0;
    private int delta = 100;

    private int curLeft = 0;
    private int curTop = 0;

    /**************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        initViews();
    }

    private void initViews(){
        pageView = $(R.id.page_view);
        btnAdd = $(R.id.btn_add);
        btnMinus = $(R.id.btn_minus);

        initData();
    }

    private void initData(){
        mContext = this;
        btnAdd.setOnClickListener(this);
        btnMinus.setOnClickListener(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        pageWidth = metrics.widthPixels;
        pageHeight = metrics.heightPixels /2 ;//测试使用100个点
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_add: //向下滑 书页向下移
               count += 1;
               curTop = curTop + delta;
               pageView.setCurPageTop(curTop);
               pageView.setNextPageTop(curTop - pageHeight );
               pageView.requestLayout();
               break;
           case R.id.btn_minus: //向上滑 书页向上移
               count -= 1;
               curTop = curTop - delta;
               pageView.setCurPageTop(curTop);
               pageView.setNextPageTop(curTop + pageHeight);
               pageView.requestLayout();
               break;
       }
    }
}

package com.sina.crowclub.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/9/29.
 */
public class FeedbackActivity extends BaseFragmentActivity
        /*implements View.OnClickListener*/
{
    /** View */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private Button btnQA;
    private Button btnUnreadMsgCount;
    private Button btnOpenActivity;

    /** Data */
    private Context mContext;
    private String mTitle;
    private int mUnreadCount = 0;

    /******************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        /*getExtral(getIntent());
        initViews();*/
    }

/*    private void getExtral(Intent intent){
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString("TITLE");
    }

    private void initViews(){
        initTitle();

        btnQA = $(R.id.btn_QA);
        btnUnreadMsgCount = $(R.id.btn_unReadMsgCount);
        btnOpenActivity = $(R.id.btn_openFeedbackActivity);

        initData();
    }

    private void initTitle(){
        viewTitle = $(R.id.layout_title);
        imgTitleBack = $(R.id.title_left_img);
        textTitle = $(R.id.title_center_text);
        imgTitleMenu = $(R.id.title_right_img);

        textTitle.setText(mTitle);
    }

    private void initData(){
        btnQA.setOnClickListener(this);
        btnUnreadMsgCount.setOnClickListener(this);
        btnOpenActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_img:
                finish();
                break;
            case R.id.btn_QA:
                Intent intentQA = new Intent(FeedbackActivity.this,QAActivity.class);
                startActivity(intentQA);
                break;
            case R.id.btn_unReadMsgCount:
                getUnreadMsgCount();
                break;
            case R.id.btn_openFeedbackActivity:
                openFeedbackActivity();
                break;
        }
    }

    private void getUnreadMsgCount(){
        FeedbackAPI.getFeedbackUnreadCount(new IUnreadCountCallback() {
            @Override
            public void onSuccess(int i) {
                mUnreadCount = i;
                LogUtil.e("getUnread Msg Count:" + i);
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.e("getUnread Msg failure: i-->" + i + "  s-->" + s);
            }
        });
    }

    private void openFeedbackActivity(){
        FeedbackAPI.openFeedbackActivity();
    }*/
}

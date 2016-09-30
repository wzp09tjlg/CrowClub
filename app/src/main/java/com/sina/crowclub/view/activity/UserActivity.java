package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
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
public class UserActivity extends BaseFragmentActivity
        implements View.OnClickListener
{
    /** View */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private Button btnBeedBack;

    /** Data */
    private Context mContext;
    private String mTitle;

    /**************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getExtal(getIntent());
        initViews();
    }

    private void getExtal(Intent intent){
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString("TITLE");
    }

    private void initViews(){
        initTitle();

        btnBeedBack = $(R.id.btn_feedback);

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
        mContext = this;

        imgTitleBack.setOnClickListener(this);
        btnBeedBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_img:
                finish();
                break;
            case R.id.btn_feedback:
                Bundle bundleFeedback = new Bundle();
                bundleFeedback.putString("TITLE","FEEDBACK");
                Intent intentFeedback = new Intent(UserActivity.this,FeedbackActivity.class);
                intentFeedback.putExtras(bundleFeedback);
                startActivity(intentFeedback);
                break;
        }
    }
}

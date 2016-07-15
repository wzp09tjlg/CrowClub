package com.sina.crowclub.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.widget.RefreshLayout;

/**
 * Created by wu on 2016/7/15.
 * 就是实现滑动的效果，具体网络数据 模拟就行。
 */
public class UserStoryActivity extends BaseFragmentActivity {
    private static final String TAG = UserStoryActivity.class.getSimpleName();
    private static final String TITLE = "TITLE";

    /** view */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView  textTitle;
    private ImageView imgTitleMenu;

    private RefreshLayout mRefreshLayout;

    /** data */
    private String mTitle;

    /************************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);
        getBundleData(getIntent());
        initViews();
    }

    private void getBundleData(Intent intent){
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString(TITLE,"");
    }

    private void initViews(){
        initTitle();

        mRefreshLayout = $(R.id.my_story_layout);

        initData();
    }

    private void initTitle(){
        viewTitle = $(R.id.title_layout);
        imgTitleBack = $(R.id.title_left_img);
        imgTitleMenu = $(R.id.title_right_img);
        textTitle = $(R.id.title_center_text);

        textTitle.setText(mTitle);
    }

    private void initData(){

    }

}

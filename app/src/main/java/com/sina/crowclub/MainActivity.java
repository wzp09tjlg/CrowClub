package com.sina.crowclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.view.activity.MessageActivity;
import com.sina.crowclub.view.activity.RefreshRecyclerActivity;
import com.sina.crowclub.view.activity.StarActivity;
import com.sina.crowclub.view.activity.Test2Activity;
import com.sina.crowclub.view.activity.UserSeriesActivity;
import com.sina.crowclub.view.activity.UserStoryActivity;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * 这里构建了一个比较规范的 demo,一些好的变成习惯在这里 会比较好的聚集.
 * 针对自己的编程能力的提高 和 编程习惯的规范 做铺垫
 * */
public class MainActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    private static final String TAG = "MainActivity";

    /*** view **/
    private Button mBtnUserStory;
    private Button mBtnMessage;
    private Button mBtnSeries;
    private Button mBtnStar;
    private Button mBtnRefreshRecycler;
    private Button mBtnAlbum;

    /** data */
    private Context mContext;

    /****************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         initViews();
    }

    private void initViews(){
        mBtnUserStory = $(R.id.btn_user_story);
        mBtnMessage = $(R.id.btn_user_message);
        mBtnSeries = $(R.id.btn_user_series);
        mBtnStar = $(R.id.btn_star);
        mBtnRefreshRecycler = $(R.id.btn_refresh_recycler);
        mBtnAlbum = $(R.id.btn_album);
        initData();
    }

    private void initData(){
        mContext = this;

        mBtnUserStory.setOnClickListener(this);
        mBtnMessage.setOnClickListener(this);
        mBtnSeries.setOnClickListener(this);
        mBtnStar.setOnClickListener(this);
        mBtnRefreshRecycler.setOnClickListener(this);
        mBtnAlbum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.btn_user_story:
                bundle.putString("TITLE",getResources().getString(R.string.user_story));
                Intent intentUserStory = new Intent(MainActivity.this, UserStoryActivity.class);
                intentUserStory.putExtras(bundle);
                startActivity(intentUserStory);
                break;
            case R.id.btn_user_message:
                bundle.putString("TITLE",getResources().getString(R.string.user_message));
                Intent intentUserMessage = new Intent(MainActivity.this, MessageActivity.class);
                intentUserMessage.putExtras(bundle);
                startActivity(intentUserMessage);
                break;
            case R.id.btn_user_series:
                bundle.putString("TITLE",getResources().getString(R.string.user_series));
                Intent intentUserSeries = new Intent(MainActivity.this, UserSeriesActivity.class);
                intentUserSeries.putExtras(bundle);
                startActivity(intentUserSeries);
                break;
            case R.id.btn_star:
                Intent intentStar = new Intent(MainActivity.this, StarActivity.class);
                startActivity(intentStar);
                break;
            case R.id.btn_refresh_recycler:
                bundle.putString("TITLE",getResources().getString(R.string.refreshRecycler));
                Intent intentRefreshRecycler = new Intent(MainActivity.this, RefreshRecyclerActivity.class);
                intentRefreshRecycler.putExtras(bundle);
                startActivity(intentRefreshRecycler);
                break;
            case R.id.btn_album:
                //AlbumActivity.launch(mContext,"连载");
                startActivity(new Intent(MainActivity.this,Test2Activity.class));
                break;
        }
    }
}

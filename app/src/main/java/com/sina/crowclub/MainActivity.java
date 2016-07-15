package com.sina.crowclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.view.activity.UserStoryActivity;
import com.sina.crowclub.view.base.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    /*** view **/
    private Button mBtnUserStory;

    /** data */

    /****************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         initViews();
    }

    private void initViews(){
        mBtnUserStory = $(R.id.btn_user_story);

        initData();
    }

    private void initData(){
        mBtnUserStory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_user_story:
                Intent intentUserStory = new Intent(MainActivity.this, UserStoryActivity.class);
                startActivity(intentUserStory);
                break;
        }
    }
}

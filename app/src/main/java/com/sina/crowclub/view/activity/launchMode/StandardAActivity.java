package com.sina.crowclub.view.activity.launchMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/12/7.
 */
public class StandardAActivity extends BaseFragmentActivity implements View.OnClickListener {
    /** View */
    private Button btnStartB;
    /** Data */
    /***************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statndard_a);
        initViews();
    }

    private void initViews(){
        btnStartB = $(R.id.btn_startB);
        btnStartB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_startB:
                Intent intent = new Intent(this,StandardBActivity.class);
                startActivity(intent);
                break;
        }
    }
}

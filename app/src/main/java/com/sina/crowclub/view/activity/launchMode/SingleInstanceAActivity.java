package com.sina.crowclub.view.activity.launchMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/12/8.
 */
public class SingleInstanceAActivity extends BaseFragmentActivity implements View.OnClickListener {
    /** View */
    private Button btnA;
    /** Data */
    /********************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleinstance_a);
        initViews();
    }

    private void initViews(){
        btnA = $(R.id.btn_startA);
        btnA.setOnClickListener(this);

        toLogPrint();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_startA:
                Intent intent = new Intent(this,SingleInstanceBActivity.class);
                startActivity(intent);
                break;
        }
    }


}

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
public class SingleInstanceCActivity extends BaseFragmentActivity  implements View.OnClickListener {
    /** View */
    private Button btnC1;
    private Button btnC2;
    /** Data */
    /********************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleinstance_c);
        initViews();
    }

    private void initViews(){
        btnC1 = $(R.id.btn_startC1);
        btnC2 = $(R.id.btn_startC2);

        btnC1.setOnClickListener(this);
        toLogPrint();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_startC1:
                Intent intent = new Intent(this,SingleInstanceAActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_startC2:
                break;
        }
    }
}

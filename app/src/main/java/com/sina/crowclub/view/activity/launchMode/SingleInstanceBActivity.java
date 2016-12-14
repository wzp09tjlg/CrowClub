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
public class SingleInstanceBActivity extends BaseFragmentActivity  implements View.OnClickListener {
    /** View */
    private Button btnB;
    /** Data */
    /********************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleinstance_b);
        initViews();
    }

    private void initViews(){
        btnB = $(R.id.btn_startB);
        btnB.setOnClickListener(this);

        toLogPrint();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_startB:
                Intent intent = new Intent(this,SingleInstanceCActivity.class);
                startActivity(intent);
                break;
        }
    }
}

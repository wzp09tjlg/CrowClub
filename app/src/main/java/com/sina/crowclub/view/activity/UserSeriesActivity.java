package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragment;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.fragment.GridDragFragment;
import com.sina.crowclub.view.fragment.ListDrag2Fragment;

/**
 * Created by wu on 2016/7/23.
 */
public class UserSeriesActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    private static final String TAG = UserSeriesActivity.class.getSimpleName();
    private static final String TITLE = "TITLE";

    /** View */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private Button mBtnListDrag;
    private Button mBtnGridDrag;
    private FrameLayout layoutContent;

    /** Data */
    private String mTitle;
    private Context mContext;

    /*******************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_series);
        getBundleExtra(getIntent());
        initViews();
    }

    private void getBundleExtra(Intent intent){
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString(TITLE,"");

    }

    private void initViews(){
        initTitle();

        mBtnListDrag = $(R.id.btn_list_drag);
        mBtnGridDrag = $(R.id.btn_grid_drag);
        layoutContent = $(R.id.layout_content);

        initData();
    }

    private void initTitle(){
        viewTitle = $(R.id.layout_title);
        imgTitleBack = $(R.id.title_left_img);
        textTitle = $(R.id.title_center_text);
        imgTitleMenu = $(R.id.title_right_img);

        textTitle.setText(mTitle);
        imgTitleMenu.setVisibility(View.INVISIBLE);
    }

    private void initData(){
        mContext = this;

        mBtnListDrag.setOnClickListener(this);
        mBtnGridDrag.setOnClickListener(this);

        initListener();
    }

    private void initListener(){
       imgTitleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BaseFragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = null;
        switch (v.getId()){
            case R.id.title_left_img:
                finish();
                break;
            case R.id.btn_list_drag:
                //fragment = new ListDragFragment();
                fragment = new ListDrag2Fragment();
                Toast.makeText(mContext,"LIST fragmenr",Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_content,fragment);
                //fragmentTransaction.add(R.id.layout_content,fragment);
                fragmentTransaction.commit();
                break;
            case R.id.btn_grid_drag:
                fragment = new GridDragFragment();
                Toast.makeText(mContext,"GRID fragmenr",Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_content,fragment);
                //fragmentTransaction.add(R.id.layout_content,fragment);
                fragmentTransaction.commit();
                break;
        }


    }
}


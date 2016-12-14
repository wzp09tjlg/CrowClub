package com.sina.crowclub.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragment;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.fragment.HandlerOneFragment;
import com.sina.crowclub.view.fragment.HandlerTwoFragment;

import java.util.List;

/**
 * Created by wu on 2016/8/22.
 */
public class FragmentHandleActivity extends BaseFragmentActivity
/*        implements
        View.OnClickListener*/
{
    private static final String TAG = FragmentHandleActivity.class.getSimpleName();
    private final String TITLE = "TITLE";
    /** View */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private Button btnAdd;
    private Button btnMinus;

    private FrameLayout layoutContent;

    /** Data */
    private Context mContext;
    private String mTitle;
    private int mCurIndex = -1;

    private HandlerOneFragment mHandlerOneFragment;
    private HandlerTwoFragment mHandlerTwoFragment;
    private List<BaseFragment> mFragments;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e("cur Thread :" + Thread.currentThread().getId());
        }
    };

    /*******************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_handler);
/*        getExtra(getIntent());
        initViews();*/
    }

/*    private void getExtra(Intent intent){
        Bundle bundle = intent.getExtras();

        mTitle = bundle.getString(TITLE);
    }

    private void initViews(){
        initTitle();

        btnAdd = $(R.id.btn_add_fragment);
        btnMinus = $(R.id.btn_minus_fragment);
        layoutContent = $(R.id.layout_content);

        initData();
    }

    private void initTitle(){
        viewTitle = $(R.id.layout_title);
        imgTitleBack = $(R.id.title_left_img);
        textTitle = $(R.id.title_center_text);
        imgTitleMenu = $(R.id.title_right_img);

        imgTitleMenu.setVisibility(View.INVISIBLE);
        textTitle.setText(mTitle);
    }

    private void initData(){
        mHandlerOneFragment = new HandlerOneFragment();
        mHandlerTwoFragment = new HandlerTwoFragment();

        mFragments = new ArrayList<>();
        mFragments.add(mHandlerOneFragment);
        mFragments.add(mHandlerTwoFragment);
        initListener();
    }

    private void initListener(){
        imgTitleBack.setOnClickListener(this);
        imgTitleMenu.setOnClickListener(this);

        btnAdd.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.title_left_img:
               finish();
               break;
           case R.id.title_right_img:
               break;
           case R.id.btn_add_fragment:
               doAddOperate();
               break;
           case R.id.btn_minus_fragment:
               doMinusOperate();
               break;
       }
    }

    private void doAddOperate(){
        mCurIndex = mCurIndex + 1;
        if(mCurIndex > mFragments.size())
            mCurIndex = mFragments.size() - 1;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layout_content, mFragments.get(mCurIndex));
        transaction.commit();

    }

    private void doMinusOperate(){
        mCurIndex = mCurIndex - 1;
        if(mCurIndex < 0)
            mCurIndex = 0;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(mFragments.get(mCurIndex));
        transaction.commit();
    }*/
}

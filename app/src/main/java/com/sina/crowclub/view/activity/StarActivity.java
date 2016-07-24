package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/7/24.
 * 屏幕切换时 会让activity 重新走生命周期
 */
public class StarActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    private static final String TAG = StarActivity.class.getSimpleName();
    private static int STATE_APPLY = 0;
    private static int STATE_OK = 1;

    /** View */
    private RelativeLayout layoutSetting;
    private EditText edtName;
    private Button btnTextColor;
    private Button btnBgColor;
    private Button btnTextSizeAdd;
    private Button btnTextSizeMinus;
    private Button btnSetOk;
    private Button btnSetApply;

    private TextView textShowApply;
    private TextView textShow;
    private Button btnCancel;

    /** Data */
    private Context mContext;
    private static int mState = 0;

    private static String mName = "";
    private static int mCountTextColor = 0;
    private static int mCountBgColor = 0;
    private static int mCountTextSize = 0;

    private int[] mTextColor = new int[]{Color.YELLOW,Color.GREEN,Color.CYAN,Color.BLACK};
    private int[] mBgColor = new int[]{Color.WHITE,Color.WHITE};
    private int[] mTextSize = new int[]{20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100};

    /**************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        initViews();
        LogUtil.e("onCreate");
    }

    private void initViews(){
        layoutSetting = $(R.id.layout_setting);
        edtName = $(R.id.edt_star_name);
        btnTextColor = $(R.id.btn_text_color);
        btnBgColor = $(R.id.btn_bg_color);
        btnTextSizeAdd = $(R.id.btn_text_size_add);
        btnTextSizeMinus = $(R.id.btn_text_size_minus);
        btnSetApply = $(R.id.btn_set_apply);
        btnSetOk = $(R.id.btn_set_ok);
        textShowApply = $(R.id.text_show_apply);

        textShow = $(R.id.text_show);
        textShow.setVisibility(View.GONE);
        btnCancel = $(R.id.btn_back);
        btnCancel.setVisibility(View.GONE);

        initData();
    }

    private void initData(){
        mContext = this;

        initListener();
    }

    private void initListener(){
        btnTextColor.setOnClickListener(this);
        btnBgColor.setOnClickListener(this);
        btnTextSizeAdd.setOnClickListener(this);
        btnTextSizeMinus.setOnClickListener(this);
        btnSetApply.setOnClickListener(this);
        btnSetOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_text_color:
                mCountTextColor =  (++mCountTextColor) % mTextColor.length;
                break;
            case R.id.btn_bg_color:
                mCountBgColor = (++mCountBgColor) % mBgColor.length;
                break;
            case R.id.btn_text_size_add:
                mCountTextSize =  (++mCountTextSize + mTextSize.length) % mTextSize.length;
                break;
            case R.id.btn_text_size_minus:
                mCountTextSize =  (--mCountTextSize + mTextSize.length) % mTextSize.length;
                break;
            case R.id.btn_set_apply:
                if(!checkInputText()) return;
                mState = STATE_APPLY;
                mName = edtName.getText().toString();
                ColorDrawable colorDrawable = new ColorDrawable(mBgColor[mCountBgColor]);
                textShowApply.setBackgroundDrawable(colorDrawable);
                textShowApply.setTextColor(mTextColor[mCountTextColor]);
                textShowApply.setTextSize(mTextSize[mCountTextSize]);
                textShowApply.setText(edtName.getText().toString());
                break;
            case R.id.btn_set_ok:
                if(!checkInputText()) return;
                mState = STATE_OK;
                mName = edtName.getText().toString();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//改变方向之后 会重新绘制

                break;
            case R.id.btn_back:
                mState = STATE_APPLY;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//改变方向之后 会重新绘制
                textShow.setVisibility(View.GONE);
                layoutSetting.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean checkInputText(){
        if(TextUtils.isEmpty(edtName.getText()))
          return false;
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mName = edtName.getText().toString();
        ColorDrawable colorDrawable = null;
        LogUtil.e("onResume   mState:" + mState + "  mName:" + mName);

        if(mState == STATE_OK){
            layoutSetting.setVisibility(View.GONE);
            colorDrawable = new ColorDrawable(mBgColor[mCountBgColor]);
//            textShow.setBackgroundDrawable();
            textShow.setText(mName);
            textShow.setTextColor(mTextColor[mCountTextColor]);
            textShow.setTextSize(mTextSize[mCountTextSize]);
            textShow.invalidate();//重画一下 看看是否有效果
            btnCancel.setVisibility(View.VISIBLE);
        }else{
            colorDrawable = new ColorDrawable(mBgColor[mCountBgColor]);
            textShowApply.setBackgroundDrawable(colorDrawable);
            textShowApply.setTextColor(mTextColor[mCountTextColor]);
            textShowApply.setTextSize(mTextSize[mCountTextSize]);
            textShowApply.setText(mName);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("onDestroy");
    }

}

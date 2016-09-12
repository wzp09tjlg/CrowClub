package com.sina.crowclub.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragment;

/**
 * Created by wu on 2016/8/23.
 */
public class HandlerOneFragment extends BaseFragment implements
        View.OnClickListener
{
    private static final String TAG = HandlerOneFragment.class.getSimpleName();
    private final String URL_PIC1 = "http://i5.3conline.com/images/piclib/201104/15/batch/1/91589/13028373157601a4gi62o58.jpg";
    private final String URL_PIC2 = "http://www.sinaimg.cn/dy/slidenews/43_img/2013_11/28364_1744968_154343.jpg";
    private final int MSG_WHT1 = 0X1101;
    private final int MSG_WHT2 = 0X1102;
    /** View */
    private Button btnAddPic;
    private Button btnMinusPic;
    private ImageView imgPic;

    /** Date */
    private Context mContext;
    private String mCurPath = "";
    private Handler mHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) { //handler 传递的消息都是在主线程中的消息队列执行
            super.handleMessage(msg);
            LogUtil.e("cur Thread is:" + Thread.currentThread().getId());
            switch (msg.what){
                case MSG_WHT1:
                    Toast.makeText(mContext,"WHT1  model:" + (String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case MSG_WHT2:
                    Toast.makeText(mContext,"WHT2  model:" + (String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /************************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handler_one,null);//container ,false);和左边的这种方式的区别自己研究
        initViews(view);
        return view;
    }

    private void initViews(View view){
        btnAddPic = $(view,R.id.btn_add_pic);
        btnMinusPic = $(view,R.id.btn_minus_pic);
        imgPic = $(view,R.id.img_opt_pic);

        initData();
    }

    private void initData(){
        mContext = getActivity();
        initListener();
    }

    private void initListener(){
        btnAddPic.setOnClickListener(this);
        btnMinusPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add_pic:
                String attr = "123";
                boolean isDigit = attr.matches("[0-9]+");
                Toast.makeText(mContext,"isDigit:" + isDigit,Toast.LENGTH_LONG).show();
                if(mCurPath.equals(URL_PIC1)) return;
                mCurPath = URL_PIC1;
                doOperate(URL_PIC1);
                break;
            case R.id.btn_minus_pic:
                String attr1 = "12123ds3";
                boolean isDigit1 = attr1.matches("[0-9]+");

                Toast.makeText(mContext,"isDigit1:" + isDigit1,Toast.LENGTH_LONG).show();
                if(mCurPath.equals(URL_PIC2)) return;
                mCurPath = URL_PIC2;
                doOperate(URL_PIC2);
                break;
        }
    }

    private void doOperate(String url){
        Glide.with(this).load(url).crossFade().dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                LogUtil.e("onException: model--->" + model);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Message msg = Message.obtain();
                if(model.equals(URL_PIC1))
                  msg.what = MSG_WHT1;
                else
                  msg.what = MSG_WHT2;
                msg.obj = model;
                mHandler.handleMessage(msg);
                LogUtil.e("onResourceReady: model--->" + model);
                return false;
            }
        }).into(imgPic);
    }
}

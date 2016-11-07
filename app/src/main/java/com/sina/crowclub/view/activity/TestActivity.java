package com.sina.crowclub.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.EmptyBean;
import com.sina.crowclub.utils.CommonHelper;
import com.sina.crowclub.utils.CommonPrefence;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.adapter.AlbumAdapter;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.RoundEditImageView;
import com.sina.crowclub.view.widget.ShowMoreView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wu on 2016/8/8.
 * 基本的类中 尽量少写无用的静态类.积少成多，还是很耗费资源的
 */
public class TestActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    private final String TAG = "TestActivity";
    private final String TAG_NAME = "TAG_NAME";
    private final String TAG_TYPE = "TAG_TYPE";
    private final String TAG_AGE = "TAG_AGE";
    /** View */
    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private Button btnTest4;
    private ImageView imgIcon;

    private RoundEditImageView roundEditImageView;

    private ShowMoreView showMoreView;

    /** Data */
    private Context mContext;
    private AlbumAdapter albumAdapter;

    /**********************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initViews();
    }

    private void initViews(){
        btnTest1 = $(R.id.btn_test1);
        btnTest2 = $(R.id.btn_test2);
        btnTest3 = $(R.id.btn_test3);
        btnTest4 = $(R.id.btn_test4);
        imgIcon = $(R.id.img_icon);
        showMoreView = $(R.id.view_showMore);
        roundEditImageView = $(R.id.rimg_edit);

        initData();
    }


    private void initData(){
        mContext = this;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_nice_girl_eat);
        roundEditImageView.setImageBitmap(bitmap);

        initListener();
    }

    private void initListener(){
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
        btnTest3.setOnClickListener(this);
        btnTest4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test1:
                //1.获取下本测试机的密度和分辨率
                //2.测试100px 到dp 在这个分辨率下的转换值
                int[] tempValue = CommonHelper.getDisplayValue(mContext);
                int px100value = CommonHelper.px2dp(mContext,100);
                LogUtil.e("tempValue:" + tempValue[0] +"*" + tempValue[1] );
                LogUtil.e("px100Value:" + px100value);
                // 测试同步和异步的网络请求
                doAsncOperate();
                // 保存本地的文件
                doSomeTestForSaveFile();
                break;
            case R.id.btn_test2:
                //3.测试100dp在当前的分辨率下转换成px是多少个点
                //4.获取本机的密度
                int dx100value = CommonHelper.dp2px(mContext,100);
                float desity = CommonHelper.getDisplayDesity(mContext);
                LogUtil.e("dx100value:" + dx100value);
                LogUtil.e("desity:" + desity);

                try {
                    Intent intent = new Intent();
                    ComponentName cmp = new ComponentName("com.sina.weibo","com.sina.weibo.SplashActivity");
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                    //WeiboHelpter.appToLoginWeibo(LaunchWeiboActivity.this);
                }catch (Exception e){
                }
                // 获取本地保存文件
                doSomeTestGetFile();
                break;
            case R.id.btn_test3:
                //显示自定义的toast
                //GloableToast.show("dshajfhdsjahfslahf");
                //显示编辑之后的圆图
                getEditOvalIcon(200);//设置图像的大小是200
                //针对bitmap做圆角处理
                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_nice_girl_eat);
                //getRoundCornerBitmap(bitmap,30f);
                break;
            case R.id.btn_test4:
                //doGetBeanFromReflect();
                doSomeTestForShowMore();
                break;
        }
    }

    private void doAsncOperate(){
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("https://www.facebook.com").build();

        //同步请求
        new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                  LogUtil.e("11");
                  Response responseAsnc = client.newCall(request).execute();
                  LogUtil.e("22");
                  LogUtil.e("response:" + responseAsnc.toString());
                  LogUtil.e("33");
              }catch (Exception e){
                  LogUtil.e("44 e:" + e.getMessage());
              };
            }
        }).start();

        //异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void doSomeTestForSaveFile(){
        CommonPrefence.put(TAG_NAME,"zhangsan");
        CommonPrefence.put(TAG_TYPE,true);
        CommonPrefence.put(TAG_AGE,123);
        LogUtil.e("save success");
    }

    private void doSomeTestGetFile(){
        String name = CommonPrefence.get(TAG_NAME,"lisi");
        boolean type = CommonPrefence.get(TAG_TYPE,false);
        int age = CommonPrefence.get(TAG_AGE,0);
        LogUtil.e("get success");
        LogUtil.e("name:" + name + "  type:" + type + "  age:" + age);
    }

    private void getEditOvalIcon(int size){
        Bitmap bitmap = roundEditImageView.extractBitmap(size);//参数的意思绘制图片的长宽大小
        imgIcon.setImageBitmap(bitmap);
    }

    private void doSomeClassLoader(){
        ClassLoader classLoader = getClassLoader();
        LogUtil.i("classLoader name:" + classLoader.toString() + "  parent name:"  + classLoader.getParent());
    }

    private void doViewLearn(){
        View view = new View(mContext);
    }

    private void getRoundCornerBitmap(Bitmap bitmap,float corner){
       Bitmap tempBitmap = CommonHelper.getRoundCornerBitmap(bitmap,corner);
       imgIcon.setImageBitmap(tempBitmap);
    }

    //通过反射获取类对象
    private void doGetBeanFromReflect(){
        String name = "com.sina.crowclub.network.Parse.EmptyBean";
        EmptyBean bean = null;
/*        try{ //第一种方式
            bean =  (EmptyBean) Class.forName(name).newInstance();
            if(bean != null){
                bean.res_code = 200;
                bean.res_msg = "this is all right";
            }

        }catch (Exception e){}*/

        try{//第二种方式 (java中定义的类只能是public的不可能是private 或者protected,当前在一个类当中的类是不算的)
            Class<?> cls=Class.forName(name);
            bean= (EmptyBean) cls.newInstance();
            if(bean != null){
                bean.res_code = 200;
                bean.res_msg = "this is all right";
            }
        }catch (Exception e){}

        if(bean == null){
            LogUtil.e("abcd","get bean from reflect error");
        }else{
            LogUtil.e("abcd","get bean from reflect right  bean:" + bean);
        }
    }

    private void doSomeTestForShowMore(){
        showMoreView.setContent(tempSting);
    }

    private String tempSting = "  下面这个函数是在上面函数的基础上，" +
            "在不绘制UI的前提下，计算一段文本显示的高度，" +
            "获得它高度的主要目的是为了站位，方便异步的显示这些文字。" +
            "下面的代码在逻辑上做了相应的具体业务的处理，" +
            "如果文字没有超出最大行数，那么就返回这段文字实际高度，" +
            "如果超过了最大行数，那么就只返回最大行数之内的文本的高度" +
            "  下面这个函数是在上面函数的基础上，" +
            "在不绘制UI的前提下，计算一段文本显示的高度，" +
            "获得它高度的主要目的是为了站位，方便异步的显示这些文字。" +
            "下面的代码在逻辑上做了相应的具体业务的处理，" +
            "如果文字没有超出最大行数，那么就返回这段文字实际高度，" +
            "如果超过了最大行数，那么就只返回最大行数之内的文本的高度" +
            "  下面这个函数是在上面函数的基础上，" +
            "在不绘制UI的前提下，计算一段文本显示的高度，" +
            "获得它高度的主要目的是为了站位，方便异步的显示这些文字。" +
            "下面的代码在逻辑上做了相应的具体业务的处理，" +
            "如果文字没有超出最大行数，那么就返回这段文字实际高度，" +
            "如果超过了最大行数，那么就只返回最大行数之内的文本的高度";
}

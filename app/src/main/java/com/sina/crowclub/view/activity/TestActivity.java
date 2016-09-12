package com.sina.crowclub.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.utils.CommonHelper;
import com.sina.crowclub.utils.CommonPrefence;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.adapter.AlbumAdapter;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.DragList.DragListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private DragListView listView;
    /** Data */
    private Context mContext;
    private AlbumAdapter albumAdapter;

    private List<StoryBean> mData;
    /**********************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initViews();
    }

    private void initViews(){
        listView = $(R.id.list);
        btnTest1 = $(R.id.btn_test1);
        btnTest2 = $(R.id.btn_test2);

        initData();
    }


    private void initData(){
        mContext = this;
        String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

        mData = new ArrayList<>();
        for(int i=0;i<50;i++){
            StoryBean bean = new StoryBean();
            bean.id = i;
            bean.name = tempName.substring(new Random().nextInt(5) + 1
                    ,new Random().nextInt(50) + 6 );

            if(TextUtils.isEmpty(bean.name))
                bean.albumName = "StoryName";

            bean.create_time = new Random().nextInt(1000);
            if(i % 3 == 0) {
                bean.albumId = new Random().nextInt(5);
                bean.albumName = tempName.substring(new Random().nextInt(5) + 1
                        ,new Random().nextInt(50) + 6);
                if(TextUtils.isEmpty(bean.albumName))
                    bean.albumName = "this is my Album";
            }
            mData.add(bean);
        }

        albumAdapter = new AlbumAdapter(mContext,mData);
        listView.setAdapter(albumAdapter);

        initListener();
    }

    private void initListener(){
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
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
}

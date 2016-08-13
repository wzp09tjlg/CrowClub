package com.sina.crowclub.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AbsListView;
import android.widget.ListView;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.adapter.AlbumAdapter;
import com.sina.crowclub.view.widget.RefreshList.XListViewFooter;
import com.sina.crowclub.view.widget.SelfDragListView.SelfDragListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/8/10.
 */
public class SelfListActivity extends Activity {
    private static final String TAG = SelfListActivity.class.getSimpleName();
    /** View */
    private SelfDragListView listView;

    private XListViewFooter footer;
    /** Data */
    private Context mContext;
    private List<StoryBean> mData;
    private AlbumAdapter adapter;

    private boolean isScrollerBottom = false;
    /****************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_detail);
        getExtra(getIntent());
        initViews();
    }

    private void getExtra(Intent intent){
        Bundle bundle = intent.getExtras();
        if(bundle == null) return;
        LogUtil.e("1");
        String data = bundle.getString("DATA");
        LogUtil.e("2");
        String data1 = bundle.getString("DATA1");
        LogUtil.e("3");
        String data2 = bundle.getString("DATA2","DATA2 DEFUALT");
        LogUtil.e("4");
        int temp = bundle.getInt("INT",123);
        LogUtil.e("5");
    }

    private void initViews(){
        listView = (SelfDragListView)findViewById(R.id.selflist);

        footer = new XListViewFooter(this);
//        listView.addFooterView(footer);
        initListView(listView);
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

            adapter = new AlbumAdapter(mContext,mData);
            listView.setAdapter(adapter);
        }
    }

    private void initListView(final ListView listView){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
                            doAddData();
                            listView.addFooterView(footer);
                            footer.show();
                        }

                        if(listView.getFirstVisiblePosition() == 0){
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && !isScrollerBottom) {
                    isScrollerBottom = true;
                } else
                    isScrollerBottom = false;
            }
        });
    }

    private void doAddData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

                final List<StoryBean> data = new ArrayList<>();
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
                    data.add(bean);
                }

                try {
                    Thread.sleep(3000);

                }catch (Exception e){

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mData.addAll(data);
                        footer.hide();
                        listView.removeFooterView(footer);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}

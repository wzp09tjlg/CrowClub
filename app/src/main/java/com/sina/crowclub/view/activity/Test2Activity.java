package com.sina.crowclub.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.view.adapter.AlbumAdapter;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.DlistView.DragListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/8/9.
 */
public class Test2Activity extends BaseFragmentActivity {
    private static final String TAG = "Test2Activity";
    /** View */
        private DragListView listView;
    /** Data */
    private Context mContext;
    private AlbumAdapter adapter;

    private List<StoryBean> mData;
    /**********************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        findViews();
    }

    private void findViews(){
        listView = $(R.id.list);

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
                    ,new Random().nextInt(5) + 6 );

            if(TextUtils.isEmpty(bean.name))
                bean.albumName = "StoryName";

            bean.create_time = new Random().nextInt(1000);
            if(i % 3 == 0) {
                bean.albumId = new Random().nextInt(5);
                bean.albumName = tempName.substring(new Random().nextInt(5) + 1
                        ,new Random().nextInt(5) + 6);
                if(TextUtils.isEmpty(bean.albumName))
                    bean.albumName = "this is my Album";
            }
            mData.add(bean);
        }

        adapter = new AlbumAdapter(mContext,mData);
        listView.setAdapter(adapter);

        listView.setDropViewListener(new DragListView.DropViewListener() {
            @Override
            public void drop(int from, int to) {
                //数据交换
                if(from < to){
                    for(int i=from; i<to; i++){
                        Collections.swap(mData, i, i+1);
                    }
                }else if(from > to){
                    for(int i=from; i>to; i--){
                        Collections.swap(mData, i, i-1);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}

package com.sina.crowclub.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.view.adapter.AlbumAdapter;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.SelfDragListView.SelfDragListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/8/12.
 */
public class LoadDragActivity extends BaseFragmentActivity{
    /** View */
    private SelfDragListView listView;

    /** Data */
    private Context mContext;
    private List<StoryBean> mData;
    private AlbumAdapter albumAdapter;
    /****************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_drag);
        initViews();
    }

    private void initViews(){
        listView = $(R.id.list);
        listView.setDragSortAble(true);
        listView.setOnItemDragSort(getDragListener());
        listView.setmOnLoadListener(getLoadListener());
        listView.setMore(true);

        initData();
    }

    private void initData(){
        mContext = this;
        String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

        mData = new ArrayList<>();
        for(int i=0;i<20;i++) {
            StoryBean bean = new StoryBean();
            bean.id = i;
            bean.name = tempName.substring(new Random().nextInt(5) + 1
                    , new Random().nextInt(10) + 6);

            if (TextUtils.isEmpty(bean.name))
                bean.albumName = "StoryName";

            bean.create_time = new Random().nextInt(100);
            if (i % 3 == 0) {
                bean.albumId = new Random().nextInt(5);
                bean.albumName = tempName.substring(new Random().nextInt(5) + 1
                        , new Random().nextInt(10) + 6);
                if (TextUtils.isEmpty(bean.albumName))
                    bean.albumName = "this is my Album";
            }
            mData.add(bean);
        }
        albumAdapter = new AlbumAdapter(mContext,mData);
        listView.setAdapter(albumAdapter);
    }

    private SelfDragListView.OnItemDragSort getDragListener(){
        SelfDragListView.OnItemDragSort listener = new SelfDragListView.OnItemDragSort() {
            @Override
            public void onItemDragSort(int start, int end) {
                Toast.makeText(mContext,"start:" + start + "  stop:" + end,Toast.LENGTH_SHORT).show();
            }
        };
        return listener;
    }

    private SelfDragListView.OnLoadListener getLoadListener(){
        SelfDragListView.OnLoadListener listener = new SelfDragListView.OnLoadListener() {
            @Override
            public void onLoadListener() {
                getData();
            }
        };
        return listener;
    }

    private int count = 0;

    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

                    List<StoryBean> data = new ArrayList<>();
                    for(int i=0;i<20;i++) {
                        StoryBean bean = new StoryBean();
                        bean.id = i;
                        bean.name = tempName.substring(new Random().nextInt(5) + 1
                                , new Random().nextInt(50) + 6);

                        if (TextUtils.isEmpty(bean.name))
                            bean.albumName = "StoryName";

                        bean.create_time = new Random().nextInt(1000);
                        if (i % 3 == 0) {
                            bean.albumId = new Random().nextInt(5);
                            bean.albumName = tempName.substring(new Random().nextInt(5) + 1
                                    , new Random().nextInt(50) + 6);
                            if (TextUtils.isEmpty(bean.albumName))
                                bean.albumName = "this is my Album";
                        }
                        data.add(bean);

                        Thread.sleep(2000);
                        count +=1;
                        if(count<5){
                            listView.setPullMore(true);
                        }else{
                            listView.setPullMore(false);
                        }

                        albumAdapter.addData(data);
                        albumAdapter.notifyDataSetChanged();

                    }
                }catch ( Exception e){}
            }
        }).start();
    }
}

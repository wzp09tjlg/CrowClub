package com.sina.crowclub.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.view.adapter.RecyclerAdapter;
import com.sina.crowclub.view.base.BaseFragment;
import com.sina.crowclub.view.widget.helper.ItemTouchHelperAdapter;
import com.sina.crowclub.view.widget.helper.OnStartDragListener;
import com.sina.crowclub.view.widget.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/7/29.
 */
public class ListDrag2Fragment extends BaseFragment {
    private static final String TAG = ListDrag2Fragment.class.getSimpleName();

    /** View */
    private RecyclerView recyclerView;

    /** Data */
    private List<StoryBean> mData;

    private RecyclerAdapter adapter;

    //拖拽的接口类
    private OnStartDragListener mOnStartDragListener;
    private ItemTouchHelper mItemTouchHelper;
    /**********************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_series,null);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        recyclerView = $(view,R.id.recyclerview);

        initData();
    }

    private void initData(){
        String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

        mData = new ArrayList<>();
        for(int i=0;i<100;i++){
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
            if( i % 5 == 0)
                bean.isEditable = false;
            else
                bean.isEditable = true;
            mData.add(bean);
        }

        adapter = new RecyclerAdapter(mData);
        //设置recyclerview 的方向 adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        //
        mOnStartDragListener = getOnStartDragListener();
        mItemTouchHelper = new ItemTouchHelper(getCallBack(adapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private OnStartDragListener getOnStartDragListener(){
        mOnStartDragListener = new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
              mItemTouchHelper.startDrag(viewHolder);
            }
        };
        return mOnStartDragListener;
    }

    private ItemTouchHelper.Callback getCallBack(ItemTouchHelperAdapter adapter){
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        return callback;
    }

}

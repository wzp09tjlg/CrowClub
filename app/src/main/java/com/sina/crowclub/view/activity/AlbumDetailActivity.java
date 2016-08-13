package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.view.adapter.AlbumDetailAdapter;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.ListViewDecoration;
import com.sina.crowclub.view.widget.SwipeMenuRecyclerView.SwipeMenuRecyclerView;
import com.sina.crowclub.view.widget.SwipeMenuRecyclerView.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/8/13.
 */
public class AlbumDetailActivity extends BaseFragmentActivity implements
    View.OnClickListener
{
    private static final String TAG = AlbumDetailActivity.class.getSimpleName();
    private static final String TITLE = "TITLE";

    /** View */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private SwipeMenuRecyclerView swipeMenuRecyclerView;

    /** Data */
    private Context mContext;
    private String mTitle;

    private List<StoryBean> mData;
    private AlbumDetailAdapter albumDetailAdapter;

    /**********************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        getExtra(getIntent());
        initViews();
    }

    private void getExtra(Intent intent){
        Bundle bundle = intent.getExtras();

        mTitle = bundle.getString(TITLE);
    }

    private void initViews(){
        initTitle();

        swipeMenuRecyclerView = $(R.id.list_content);

        getData();
    }

    private void initTitle(){
        viewTitle = $(R.id.title_layout);
        imgTitleBack = $(R.id.title_left_img);
        textTitle = $(R.id.title_center_text);
        imgTitleMenu = $(R.id.title_right_img);

        textTitle.setText(mTitle);
        imgTitleMenu.setVisibility(View.INVISIBLE);
    }

    private void getData(){
        mContext = this;
        mData = new ArrayList<>();
        mData.addAll(addData());

        albumDetailAdapter = new AlbumDetailAdapter(mContext,mData);

        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false));
        swipeMenuRecyclerView.setHasFixedSize(true);
        swipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());

        swipeMenuRecyclerView.setLongPressDragEnabled(true);
        swipeMenuRecyclerView.setAdapter(albumDetailAdapter);

        swipeMenuRecyclerView.setOnItemMoveListener(onItemMoveListener);
        swipeMenuRecyclerView.setmOnItemDragCompelete(getCompeleteListener());

        swipeMenuRecyclerView.setOnScrollListener(mOnScrollListener);

        initListener();
    }

    private void initListener(){
        imgTitleBack.setOnClickListener(this);
    }

    boolean isStartMove = false;

    /**
     * 当Item移动的时候。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            // 当Item被拖拽的时候。
            isStartMove = true;
            StoryBean bean = albumDetailAdapter.getData().get(fromPosition);
            if (fromPosition < toPosition) {
                mData.add(toPosition + 1, bean);
                mData.remove(fromPosition);
            } else {
                mData.add(toPosition, bean);
                mData.remove(fromPosition + 1);
            }
            albumDetailAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;// 返回true表示处理了，返回false表示你没有处理。
        }

        @Override
        public void onItemDismiss(int position) {}
    };

    private SwipeMenuRecyclerView.OnItemDragCompelete getCompeleteListener(){
        SwipeMenuRecyclerView.OnItemDragCompelete listener = new SwipeMenuRecyclerView.OnItemDragCompelete() {
            @Override
            public void onItemDragCompelete(int start, int stop) {

                if(start != stop){

                    if(isStartMove){
                        isStartMove = false;
                        Toast.makeText(mContext," success",Toast.LENGTH_SHORT).show();
                    }else
                        ;
                }else{
                    isStartMove = false;
                }
            }
        };
        return listener;
    }

    private boolean isLoading = false; //正在加载中
    private boolean isEnded = false;//是否滑到底
    private  boolean isMore = true;//是否有更多的数据
    private int count = 0;
    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                isEnded = true;
            }
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState){
            if(!isLoading && isEnded && isMore && newState == RecyclerView.SCROLL_STATE_IDLE ){
                isLoading = true;
                doAddData();
            }
        }
    };

    private void doAddData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(count > 3) {
                    isMore = false;
                } else{
                    count +=1;
                    mData.addAll(addData());
                }

                try{
                    Thread.sleep(3000);
                }catch (Exception e){}

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        albumDetailAdapter.notifyDataSetChanged();
                    }
                });

                isEnded = false;
                isLoading = false;
            }
        }).start();
    }

    private List<StoryBean> addData(){
        String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

        final List<StoryBean> data = new ArrayList<>();
        for(int i=0;i<20;i++){
            StoryBean bean = new StoryBean();
            bean.id = i;
            bean.name = tempName.substring(new Random().nextInt(5) + 1
                    ,new Random().nextInt(10) + 6 );

            if(TextUtils.isEmpty(bean.name))
                bean.albumName = "StoryName";

            bean.create_time = new Random().nextInt(1000);
            if(i % 3 == 0) {
                bean.albumId = new Random().nextInt(5);
                bean.albumName = tempName.substring(new Random().nextInt(5) + 1
                        ,new Random().nextInt(10) + 6);
                if(TextUtils.isEmpty(bean.albumName))
                    bean.albumName = "this is my Album";
            }
            data.add(bean);
        }
        return data;
    }

    int temp = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_img:
                temp +=1;
                if(temp % 3 == 0){
                    swipeMenuRecyclerView.setLongPressDragEnabled(false);
                    albumDetailAdapter.setEditAble(false);
                    albumDetailAdapter.notifyDataSetChanged();
                }else{
                    swipeMenuRecyclerView.setLongPressDragEnabled(true);
                    albumDetailAdapter.setEditAble(true);
                    albumDetailAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}


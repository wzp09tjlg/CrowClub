package com.sina.crowclub.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sina.crowclub.R;
import com.sina.crowclub.view.widget.ListViewDecoration;
import com.sina.crowclub.view.widget.SwipeMenuRecyclerView.SwipeMenu;
import com.sina.crowclub.view.widget.SwipeMenuRecyclerView.SwipeMenuCreator;
import com.sina.crowclub.view.widget.SwipeMenuRecyclerView.SwipeMenuItem;
import com.sina.crowclub.view.widget.SwipeMenuRecyclerView.SwipeMenuRecyclerView;
import com.sina.crowclub.view.widget.SwipeMenuRecyclerView.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wu on 2016/8/28.
 */
public class NewsActivity extends AppCompatActivity implements
        View.OnClickListener
{
    private static final String TAG = NewsActivity.class.getSimpleName();
    /** View */
    private Toolbar toolbar;

    private SwipeMenuRecyclerView swipeMenuRecyclerView;

    /** Data */
    private Context mContext;
    private List<String> mData;

    /*************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcomstyle);
        initViews();
    }

    private void initViews(){
        initTitle();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeMenuRecyclerView = (SwipeMenuRecyclerView)findViewById(R.id.recycler_view);

        initData();
    }

    private void initTitle(){}

    private void initData(){
        mContext = this;
        mData = new ArrayList<>();
        for(int i=0;i<30;i++){
            mData.add("我是第"+i+"条数据");
        }

        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        swipeMenuRecyclerView.setLayoutManager(manager);
        swipeMenuRecyclerView.setHasFixedSize(true);
        swipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        swipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。

        swipeMenuRecyclerView.setSwipeMenuCreator(getSwipeMenuCreator());//设置侧滑的菜单
    }

    private OnItemMoveListener getItemMoveListener(){
        OnItemMoveListener listener    = new OnItemMoveListener() {
                    @Override
                    public boolean onItemMove(int fromPosition, int toPosition) {
                        // 当Item被拖拽的时候。
                        Collections.swap(mData, fromPosition, toPosition);
                      //  mMenuAdapter.notifyItemMoved(fromPosition, toPosition);
                        return true;// 返回true表示处理了，返回false表示你没有处理。
                    }

                    @Override
                    public void onItemDismiss(int position) {
                        // 当Item被滑动删除掉的时候，在这里是无效的，因为这里没有启用这个功能。
                        // 使用Menu时就不用使用这个侧滑删除啦，两个是冲突的。
                    }
                };
        return listener;
    }

    private SwipeMenuCreator getSwipeMenuCreator() {
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int size = getResources().getDimensionPixelSize(R.dimen.dimen_60);

                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.drawable_red_selector)
                        .setImage(R.drawable.icon_menu_close)
                        .setWidth(size)
                        .setHeight(size);

                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧菜单。

                SwipeMenuItem wechatItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.drawable_greens_selector)
                        .setImage(R.drawable.icon_menu_wechat)
                        .setWidth(size)
                        .setHeight(size);

                swipeRightMenu.addMenuItem(wechatItem);// 添加一个按钮到右侧菜单。
            }
        };
        return swipeMenuCreator;
    }

    @Override
    public void onClick(View v) {

    }
}

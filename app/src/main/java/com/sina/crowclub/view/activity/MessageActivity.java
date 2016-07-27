package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.utils.CommonHelper;
import com.sina.crowclub.view.adapter.AbsBaseAdapter;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.RefreshList.RefreshLayout;
import com.sina.crowclub.view.widget.swipe.SwipeMenu;
import com.sina.crowclub.view.widget.swipe.SwipeMenuCreator;
import com.sina.crowclub.view.widget.swipe.SwipeMenuItem;
import com.sina.crowclub.view.widget.swipe.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/7/23.
 * 实现手机qq消息左侧滑出 菜单自定义的显示的功能
 */
public class MessageActivity extends BaseFragmentActivity implements
        View.OnClickListener,AbsListView.OnItemClickListener,
        RefreshLayout.OnLoadListener
{
    private static final String TAG = MessageActivity.class.getSimpleName();
    public static final String TITLE = "TITLE";

    /** View */
    //title
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private RefreshLayout mRefreshLayout;
    private SwipeMenuListView mListView;

    /** Data */
    private String mTitle;
    private Context mContext;

    private List<StoryBean> mData;
    private SwipeMenuCreator swipeMenuCreator;
    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener;
    private AbsBaseAdapter adapter;

    /*********************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_message);
        getBundleExral(getIntent());
        initViews();
    }

    private void getBundleExral(Intent intent){
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString(TITLE,"");
    }

    private void initViews(){
        initTitle();

        mRefreshLayout = $(R.id.layout_refresh);
        mListView = (SwipeMenuListView) mRefreshLayout.getListView();

        initData();
    }

    private void initTitle(){
        viewTitle = $(R.id.layout_title);
        imgTitleBack = $(viewTitle,R.id.title_left_img);
        textTitle = $(viewTitle,R.id.title_center_text);
        imgTitleMenu = $(viewTitle,R.id.title_right_img);

        textTitle.setText(mTitle);
        imgTitleMenu.setVisibility(View.INVISIBLE);
    }

    private void initData(){
        mContext = this;

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

        imgTitleBack.setOnClickListener(this);

        mRefreshLayout.setOnLoadListener(this);
        mRefreshLayout.setPullRefresh(false);//设置不能下拉刷新
        mRefreshLayout.setLoading(false);//设置不能上来加载

        mListView.setControlMenu(true); //打开控制的开关
        mListView.setMenuCreator(getSwipeMenuCreator());
        mListView.setAdapter(getAdapter(mContext,mData));
        mListView.setOnMenuItemClickListener(getMenuItemClickListener());
        mListView.setOnItemClickListener(this);
    }

    private SwipeMenuCreator getSwipeMenuCreator(){
     swipeMenuCreator = new SwipeMenuCreator() {
         @Override
         public void create(SwipeMenu menu) {//未打开控制menu的开关
             SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
             deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                     0x3F, 0x25)));
             deleteItem.setPadding(CommonHelper.dpToPx(mContext, 16));
             deleteItem.setTitle("取消赞");
             deleteItem.setTitleSize(CommonHelper.dpToPx(mContext, 15));
             deleteItem.setTitleColor(Color.WHITE);
             deleteItem.setIcon(R.drawable.icon_like_delete);
             menu.addMenuItem(deleteItem);

             SwipeMenuItem cancelItem = new SwipeMenuItem(mContext);
             cancelItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                     0x3F, 0x25)));
             cancelItem.setPadding(CommonHelper.dpToPx(mContext, 16));
             cancelItem.setTitle("取消收藏");
             cancelItem.setTitleSize(CommonHelper.dpToPx(mContext, 15));
             cancelItem.setTitleColor(Color.WHITE);
             cancelItem.setIcon(R.drawable.icon_collection_delete);
             menu.addMenuItem(cancelItem);
         }

         @Override
         public void create(SwipeMenu menu, Object obj) {//打开控制menu的开关
             if(((StoryBean)obj).isEditable){
                 SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                 deleteItem.setBackground(new ColorDrawable(Color.rgb(0x09,
                         0xaF, 0x25)));
                 deleteItem.setPadding(CommonHelper.dpToPx(mContext, 16));
                 deleteItem.setTitle("取消赞");
                 deleteItem.setTitleSize(CommonHelper.dpToPx(mContext, 15));
                 deleteItem.setTitleColor(Color.WHITE);
                 deleteItem.setIcon(R.drawable.icon_like_delete);
                 menu.addMenuItem(deleteItem);
             }

             SwipeMenuItem cancelItem = new SwipeMenuItem(mContext);
             cancelItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                     0x3F, 0x25)));
             cancelItem.setPadding(CommonHelper.dpToPx(mContext, 16));
             cancelItem.setTitle("取消收藏");
             cancelItem.setTitleSize(CommonHelper.dpToPx(mContext, 15));
             cancelItem.setTitleColor(Color.WHITE);
             cancelItem.setIcon(R.drawable.icon_collection_delete);
             menu.addMenuItem(cancelItem);
         }
     };
        return swipeMenuCreator;
    }

    private SwipeMenuListView.OnMenuItemClickListener getMenuItemClickListener(){
        onMenuItemClickListener = new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if(mData.get(position).isEditable){
                    switch (index){
                        case 0:
                            Toast.makeText(mContext,"取消赞",Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(mContext,"取消收藏",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }else{
                    switch (index){
                        case 0:
                            Toast.makeText(mContext,"取消收藏",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                return false;
            }
        };
        return onMenuItemClickListener;
    }

    private AbsBaseAdapter getAdapter(Context context,List<StoryBean> data){
        adapter = new AbsBaseAdapter(context,data);
        return adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext,"position: " + position + "  is clicked.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        mRefreshLayout.setPullRefresh(false);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setPullRefresh(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_img:
                finish();
                break;
        }
    }
}

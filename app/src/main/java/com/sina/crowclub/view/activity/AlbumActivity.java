package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
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
import com.sina.crowclub.view.adapter.AlbumAdapter;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.RefreshList.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/8/8.
 */
public class AlbumActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    private static final String TAG = AlbumActivity.class.getSimpleName();
    private static final String TITLE = "TITLE";
    /** View */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private RefreshLayout mRefreshLayout;

    /** Data */
    private Context mContext;
    private String mTitle;
    private int mCurIndex = 1;
    private int mRequestIndex;
    private List<StoryBean> mData;

    private AlbumAdapter albumAdapter;

    /**************************************************/
    public static void launch(Context context,String title){
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        Intent intent = new Intent(context,AlbumActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        getExtra(getIntent());
        initViews();
    }

    private void getExtra(Intent intent){
        Bundle bundle = intent.getExtras();

        mTitle = bundle.getString(TITLE);
    }

    private void initViews(){
        initTitle();

        mRefreshLayout = $(R.id.refresh);

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
        mRefreshLayout.setAdapter(albumAdapter);
        mRefreshLayout.setOnLoadListener(getLoadListener());
        mRefreshLayout.getListView().setOnItemClickListener(getOnItemClick());
        mRefreshLayout.getListView().setOnItemLongClickListener(getOnItemLongClick());

        imgTitleBack.setOnClickListener(this);
    }

    private RefreshLayout.OnLoadListener getLoadListener(){
        RefreshLayout.OnLoadListener listener = new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoadMore() {
                mCurIndex += 1;
                requestData(mCurIndex);
            }

            @Override
            public void onRefresh() {
                mRefreshLayout.setPullRefresh(false);
            }
        };
        return listener;
    }

    private void requestData(int page){
        mRequestIndex = page;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

               List<StoryBean>  data = new ArrayList<>();
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

                try{
                    Thread.sleep(500);
                    albumAdapter.addData(data);
                }catch (Exception e){}
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_img:
                finish();
                break;
        }
    }

    private AbsListView.OnItemClickListener getOnItemClick(){
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext," onItemClick ",Toast.LENGTH_LONG).show();
            }
        };
        return listener;
    }

    private AbsListView.OnItemLongClickListener getOnItemLongClick(){
        AbsListView.OnItemLongClickListener listener = new AbsListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext," onItemLongClick ",Toast.LENGTH_LONG).show();
                return false;
            }
        };
        return listener;
    }

}

package com.sina.crowclub.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.network.Parse.SyncBean;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.adapter.AbsBaseAdapter;
import com.sina.crowclub.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/8/23.
 */
public class HandlerTwoFragment extends BaseFragment implements
    View.OnClickListener
{
    private static final String TAG = HandlerOneFragment.class.getSimpleName();
    private final int MSG_WHT1 = 0X1101;
    private final int MSG_WHT2 = 0X1102;
    /** View */
    private Button btnAddData;
    private Button btnMinusData;
    private ListView mListView;

    /** Date */
    private Context mContext;
    private List<StoryBean> mData;
    private AbsBaseAdapter mAdapter;
    private String json = "[{\"story_id\":\"26495\",\"version\":0},{\"story_id\":\"26494\",\"version\":2},{\"story_id\":\"26360\",\"version\":0},{\"story_id\":\"26311\",\"version\":1},{\"story_id\":\"26361\",\"version\":0},{\"story_id\":\"26425\",\"version\":4},{\"version\":0},{\"version\":0},{\"version\":0},{\"story_id\":\"26419\",\"version\":0},{\"version\":0},{\"story_id\":\"26294\",\"version\":0},{\"story_id\":\"26488\",\"version\":0},{\"story_id\":\"26486\",\"version\":0},{\"story_id\":\"26305\",\"version\":2},{\"story_id\":\"26458\",\"version\":0},{\"story_id\":\"26451\",\"version\":0},{\"story_id\":\"26442\",\"version\":2},{\"story_id\":\"26433\",\"version\":0}]";
    private List<SyncBean> mDataSync;

    private Handler mHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e("cur Thread is:" + Thread.currentThread().getId());
            switch (msg.what){
                case MSG_WHT1:
                    break;
                case MSG_WHT2:
                    break;
            }
        }
    };
    /************************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handler_two,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        btnAddData = $(view,R.id.btn_add_data);
        btnMinusData = $(view,R.id.btn_minus_data);
        mListView = $(view,R.id.list_data);

        initData();
    }

    private void initData(){
        mContext = getActivity();
        mData = new ArrayList<>();

        String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

        mData = new ArrayList<>();
        for(int i=0;i<40;i++){
            StoryBean bean = new StoryBean();
            bean.id = i;
            bean.name = tempName.substring(new Random().nextInt(5) + 1
                    ,new Random().nextInt(10) + 6 );

            if(TextUtils.isEmpty(bean.name))
                bean.albumName = "StoryName";

            bean.create_time = new Random().nextInt(100);
            if(i % 3 == 0) {
                bean.albumId = new Random().nextInt(5);
                bean.albumName = tempName.substring(new Random().nextInt(5) + 1
                        ,new Random().nextInt(15) + 6);
                if(TextUtils.isEmpty(bean.albumName))
                    bean.albumName = "this is my Album";
            }
            if( i % 5 == 0)
                bean.isEditable = false;
            else
                bean.isEditable = true;
            mData.add(bean);
        }
        mAdapter = new AbsBaseAdapter(mContext,mData);
        mListView.setAdapter(mAdapter);

        initListener();
    }

    private void initListener(){
        btnAddData.setOnClickListener(this);
        btnMinusData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_data:
                String tempJson = "[{\"story_id\":25463,\"version\":0},{\"story_id\":25462,\"version\":0}]";
                mDataSync = json2listT(tempJson,SyncBean.class);
                if(mDataSync == null)
                    LogUtil.e("data is null or empty");
                else
                    LogUtil.e("data size:" + mDataSync.size() + "  position 0:" + mDataSync.get(0).toString());
                break;
            case R.id.btn_minus_data:
                break;
        }
    }

    /** json转换成list<Y>数据*/
    @SuppressWarnings("unchecked")
    public  final <T> List<T> json2listT(String jsonStr, Class<T> tC) {
        //json字符串不能为空
        if(TextUtils.isEmpty(jsonStr)) return null;
        //json字符串必须为数组节点类型
        if(!(jsonStr.startsWith("[") && jsonStr.endsWith("]"))) return null;
        List<T> listT = null;
        try {
            //创建泛型对象
            T t =  tC.newInstance();
            //利用类加载加载泛型的具体类型
            Class<T> classT = (Class<T>) Class.forName(t.getClass().getName());
            List<Object> listObj = new ArrayList<Object>();
            //将数组节点中json字符串转换为object对象到Object的list集合
            listObj = new GsonBuilder().create().fromJson(jsonStr, new TypeToken<List<Object>>(){}.getType());
            //转换未成功
            if(listObj == null || listObj.isEmpty()) return null;
            listT = new ArrayList<T>();
            //将Object的list中的的每一个元素中的json字符串转换为泛型代表的类型加入泛型代表的list集合返回
            for (Object obj : listObj) {
                T perT = new GsonBuilder().create().fromJson(obj.toString(), classT);
                listT.add(perT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listT;
    }
}

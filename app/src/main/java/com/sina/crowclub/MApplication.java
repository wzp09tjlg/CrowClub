package com.sina.crowclub;

import android.app.Application;
import android.content.Context;

import com.sina.crowclub.utils.CommonPrefence;
import com.sina.crowclub.view.base.BaseFragmentActivity;

import java.util.Stack;

/**
 * Created by wu on 2016/7/16.
 */
public class MApplication extends Application {
    private static final String TAG = "MApplication";
    private static Context mContext;

    private Stack<BaseFragmentActivity> activities;

    private static CommonPrefence commonPrefence;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //全局变量
        commonPrefence = new CommonPrefence(mContext);

        //其他配置
    }

    public static CommonPrefence getCommonPrefence(){
        if(commonPrefence == null)
            commonPrefence = new CommonPrefence(mContext);
        return commonPrefence;
    }

    public void addActivity(BaseFragmentActivity activity){
        if(activities == null)
            activities = new Stack<BaseFragmentActivity>();
        activities.push(activity);
    }

    public void removeActivity(BaseFragmentActivity activity){
        if(activities == null || activities.isEmpty())
            return;
        activities.push(activity);
    }

    public void removeActivityAll(){
        if(activities == null || activities.isEmpty())
            return;
        for(BaseFragmentActivity activity:activities)
            activities.pop();
    }

}

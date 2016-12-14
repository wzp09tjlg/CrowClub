package com.sina.crowclub.view.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sina.crowclub.utils.LogUtil;

/**
 * Created by wu on 2016/12/14.
 */
public class CommonService extends Service {
    /** Data */
    private Context mContext;

    /*************************/
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initData();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initData(){
        mContext = this;
        LogUtil.e("Services Context id:" + mContext);
        LogUtil.e("getBaseContext id:" + getBaseContext());

        stopSelf();
    }
}

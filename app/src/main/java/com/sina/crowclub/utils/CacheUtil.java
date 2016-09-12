package com.sina.crowclub.utils;

import android.content.Context;

/**
 * Created by wu on 2016/8/18.
 */
public class CacheUtil {
    private static CacheUtil instance;
    private static int count = 1;

    private CacheUtil(){}

    public static CacheUtil getInstance(){
        if(instance == null){
            instance = new CacheUtil();
        }
        return instance;
    }

    public static void doCache(final Context context, final String... params){
        new Thread(new Runnable() {
            @Override
            public void run() {
               while (true){
                   try {
                       Thread.sleep(1000);
                       count = count + 1;

                       LogUtil.e("wuzp  time:" + System.currentTimeMillis() + "   url:" + params[0]);

                       GlideCacheUtil.doCache(context,params[count % params.length]);

                       if(count == 10)
                           break;
                   }catch (Exception e){}
               }
            }
        }).start();
    }
}

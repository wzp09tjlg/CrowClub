package com.sina.crowclub.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;

/**
 * Created by wu on 2016/8/19.
 */
public class GlideCacheUtil {

    public static void doCache(Context context, String yourUrl){
        try {
            FutureTarget<File> future = Glide.with(context)
                    .load(yourUrl)
                    .downloadOnly(500, 500);
            File cacheFile = future.get();
            LogUtil.e("file url:" + cacheFile.getAbsolutePath());

//            Glide.getPhotoCacheDir(context,yourUrl);

        }catch (Exception e){
            LogUtil.e("wuzuuuu e." + e.getMessage());
        }
    }

}

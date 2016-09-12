package com.sina.crowclub.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by wu on 2016/8/19.
 */
public class CustomCachingGlideModule implements GlideModule {
    @Override public void applyOptions(Context context, GlideBuilder builder) {
        /*MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache( new LruResourceCache( customMemoryCacheSize ));
        builder.setBitmapPool( new LruBitmapPool( customBitmapPoolSize ));*/

        int cacheSize100MegaBytes = 104857600;

        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes)
        ); //如果是将图片缓存到data/data/包名/cache/下 系统会在文件超过1M时 清除掉

        /*int cacheSize100MegaBytes = 104857600;
        String downloadDirectoryPath = Environment.getDownloadCacheDirectory().getPath();
        builder.setDiskCache(
                new DiskLruCacheFactory( downloadDirectoryPath, cacheSize100MegaBytes )
        );*/
    }

    @Override public void registerComponents(Context context, Glide glide) {
        // nothing to do here
    }
}

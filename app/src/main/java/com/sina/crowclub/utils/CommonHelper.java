package com.sina.crowclub.utils;

import android.content.Context;

/**
 * Created by wu on 2016/7/23.
 * 常用那个的工具类
 */
public class CommonHelper {

    /**实现从dip到px的转换*/
    public static int dpToPx(Context context, int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * (float) dps);
    }

    public static int pxToDp(Context context, float px) {
        return Math.round(px / context.getResources().getDisplayMetrics().density);
    }
}

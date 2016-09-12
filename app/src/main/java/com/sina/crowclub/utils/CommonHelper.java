package com.sina.crowclub.utils;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

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

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /** 获取手机的分辨率 */
    public static int[] getDisplayValue(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        Display display = wm.getDefaultDisplay();
        display.getSize(point);
        int[] value = new int[2];
        value[0] = point.x;
        value[1] = point.y;
        return value;
    }

    /** 手机密度 */
    public static float getDisplayDesity(Context context){
        float desity = context.getResources().getDisplayMetrics().density;
        return  desity;
    }

    /** 增加一个实例化方法 */
    public static  <T extends View> T $(View view,int id){
        return (T)view.findViewById(id);
    }

    /** url转map */
    public static Map<String,String> Url2Map(String url){
        if(TextUtils.isEmpty(url)) return null;
        int tempPosition = url.indexOf("?");
        if(tempPosition<1) return null;
        String SubUrl = url.substring(tempPosition + 1);
        Map<String, String> map = null;
        if (SubUrl != null && SubUrl.indexOf("&") > -1 && SubUrl.indexOf("=") > -1) {
            map = new HashMap<String, String>();
            String[] arrTemp = SubUrl.split("&");
            for (String str : arrTemp) {
                String[] qs = str.split("=");
                map.put(qs[0], qs[1]);
            }
        }
        return map;
    }
}

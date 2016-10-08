package com.sina.crowclub.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    /** 获取json中的某个字段 */
    public static String getJsonElement(String json,String elementName){
        if(TextUtils.isEmpty(json) || TextUtils.isEmpty(elementName)) return "";
        String tempJson = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            tempJson = jsonObject.get(elementName).toString();
        }catch (Exception e){}
        return tempJson;
    }

    /** 获取json的key是数字的json转化为字符串数组 */
    public static String getKeyNumJson(String json){
        if(TextUtils.isEmpty(json)) return "";
        JsonParser jsonParser = new JsonParser();
        Set<Map.Entry<String,JsonElement>> entrySet = jsonParser.parse(json)
                .getAsJsonObject().entrySet();
        StringBuilder builder = new StringBuilder("[");
        int tempLen = entrySet.size();
        for(Map.Entry<String, JsonElement> entry : entrySet){
            builder.append(entry.getValue().toString());
            -- tempLen;
            if(tempLen>0)
            builder.append(",");
        }
        builder.append("]");
        return builder.toString();
    }

    /** 获取json中key为数字的串的第一个bean */
    public static String getKeyNumBeanJson(String json){
        if(TextUtils.isEmpty(json)) return "";
        JsonParser jsonParser = new JsonParser();
        Set<Map.Entry<String,JsonElement>> entrySet = jsonParser.parse(json)
                .getAsJsonObject().entrySet();
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, JsonElement> entry : entrySet){
            builder.append(entry.getValue().toString());
            break;
        }
        return builder.toString();
    }

    /** 创建带圆角的bitmap */
    public static Bitmap getRoundCornerBitmap(Bitmap bitmap, float corner) {
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        Bitmap roundCornerBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, corner, corner, paint);
        PorterDuffXfermode xfermode=new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return roundCornerBitmap;
    }
}

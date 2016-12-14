package com.sina.crowclub.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.InputStream;
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

    public static int[] getDisplayValue(Activity activity){
        int[] tempValue = new int[]{0,0};
        DisplayMetrics dm = new DisplayMetrics();
        //dm = activity.getWindowManager().getDefaultDisplay().getMetrics();//效果与下边一样
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        tempValue[0] = dm.widthPixels;
        tempValue[1] = dm.heightPixels;
        return tempValue;
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

    /** 防止OOM 处理图片 */
    public static void scaleImage(final Activity activity, final ImageView imageView, int drawableResId) {
        // 获取屏幕的高宽
        int[] outSize = CommonHelper.getDisplayValue(activity);
        // 解析将要被处理的图片
        Bitmap resourceBitmap = readBitmap(activity,drawableResId);//获取的图片就是采用 RGB565 和 RGB8888没关系，关键是看获取的option类型
        if (resourceBitmap == null) {
            return;
        }

        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize[0] * 1.0f / resourceBitmap.getWidth());
        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize[0], bitmapScaledHeight, false);

        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;

                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = imageView.getMeasuredHeight();

                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;
                // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(),
                        scaledBitmap.getHeight() - offset * 2);

                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                    scaledBitmap.recycle();
                    System.gc();
                }
                BitmapDrawable bd = new BitmapDrawable(activity.getResources(), finallyBitmap);
                LogUtil.e("width:" + bd.getMinimumWidth() + "  height:" + bd.getMinimumHeight());
                LogUtil.e("width * height:" + (bd.getMinimumHeight() * bd.getMinimumHeight()));
                imageView.setBackgroundDrawable(bd);
                return true;
            }
        });
    }

    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}

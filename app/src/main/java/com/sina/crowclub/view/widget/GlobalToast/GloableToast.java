package com.sina.crowclub.view.widget.GlobalToast;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wu on 2016/9/30.
 */
public class GloableToast {
    private static BaseToast baseToast;
    private static GloableToast gloableToast;
    private static Context mContext;

    private GloableToast(Context context){
        baseToast = new BaseToast(context,false);
    }

    public static GloableToast getInsance(Context context){
        if(gloableToast == null){
            gloableToast = new GloableToast(context);
        }
        mContext = context;
        return gloableToast;
    }

    public static void show(String msg){
        show(msg,3000);
    }

    public static void show(String msg,int duration){
        LinearLayout mLayout=new LinearLayout(mContext);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv = new TextView(mContext);
        tv.setText(msg);
        tv.setTextColor(Color.rgb(121, 121, 121));
        tv.setGravity(Gravity.CENTER);
        baseToast.setView(mLayout);
        baseToast.setDuration(duration);
        baseToast.show();
    }
}

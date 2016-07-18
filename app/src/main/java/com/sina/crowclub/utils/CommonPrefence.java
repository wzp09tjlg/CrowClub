package com.sina.crowclub.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wu on 2016/7/16.
 */
public class CommonPrefence {
    private final String NAME = "CROWCLUB";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public CommonPrefence(Context context){
        sharedPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //保存数据
    public static void put(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }

    public static void put(String key,int value){
        editor.putInt(key,value);
        editor.commit();
    }

    public static void put(String key,long value){
        editor.putLong(key,value);
        editor.commit();
    }

    public static void put(String key,float value){
        editor.putFloat(key,value);
        editor.commit();
    }

    public static void put(String key,boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void remove(String key){
        editor.remove(key);
        editor.commit();
    }

    public static void clear(){
        editor.clear();
        editor.commit();
    }

    //获取数据
    public static String get(String key,String defualtValue){
        return sharedPreferences.getString(key,defualtValue);
    }

    public static int get(String key,int defualtValue){
        return sharedPreferences.getInt(key,defualtValue);
    }

    public static float get(String key,float defualtValue){
        return sharedPreferences.getFloat(key,defualtValue);
    }

    public static long get(String key,long defualtValue){
        return sharedPreferences.getLong(key,defualtValue);
    }

    public static boolean get(String key,boolean defualtValue){
        return sharedPreferences.getBoolean(key,defualtValue);
    }

}

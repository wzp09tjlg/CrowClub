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

}

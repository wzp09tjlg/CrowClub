package com.sina.crowclub.utils;

import com.google.gson.Gson;

/**
 * Created by wu on 2016/9/27.
 */
public class GsonUtil<T> {
    private Class<T> type;
    private static Gson gson;

    public GsonUtil(Class<T> type){
       if(gson == null)
           gson = new Gson();
        this.type = type;
    }

    public T parse(String json){
        return gson.fromJson(json,type);
    }
}

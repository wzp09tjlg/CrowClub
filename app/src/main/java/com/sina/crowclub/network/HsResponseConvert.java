package com.sina.crowclub.network;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by wu on 2016/7/15.
 */
public class HsResponseConvert<T> implements Converter<ResponseBody,T>{
    private Type type;
    private Gson gson;

    public HsResponseConvert(Type type){
        this.type = type;
        gson = new Gson();
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        return null;
    }

//    private T parseBaseBean(String result){
//        JSONObject jsonObject = null;
//        try{
//            jsonObject = new JSONObject(result);
//            Data data = (T)gson.fromJson(jsonObject.get("data"),type.getClass());
//        }catch (Exception e){}
//        return null;
//    }
}

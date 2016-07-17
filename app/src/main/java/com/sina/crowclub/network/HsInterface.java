package com.sina.crowclub.network;

import com.sina.crowclub.network.Parse.BaseBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by wu on 2016/7/15.
 * 花生故事网络接口 retrofit2.0
 */
public interface HsInterface {

    @GET(UrlConstant.MY_STORY_URL+"?")
    Call<BaseBean> getUserSotryList();
}

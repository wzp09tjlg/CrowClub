package com.sina.crowclub.network;

/**
 * Created by wu on 2016/7/15.
 * 网络请求的地址都存放在这里
 */
public class UrlConstant {

    /** 线上正式 */
    //public static final String HOST = "221.179.193.164";
    //public static final String HOST_KEY = "http://221.179.193.164/huasheng/client_hengmin";

    /** 线下测试 */
    public static final String HOST = "221.179.193.164";
    public static final String HOST_KEY = "http://221.179.193.164/huasheng/client_hengmin";

    /** 我的故事 */
    public static final String MY_STORY_URL = HOST + "/user/story_list.json";
}

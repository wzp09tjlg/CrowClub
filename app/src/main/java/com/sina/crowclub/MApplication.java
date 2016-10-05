package com.sina.crowclub;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.sina.crowclub.utils.CommonPrefence;
import com.sina.crowclub.utils.FinalUtil;
import com.sina.crowclub.utils.ThreadPool;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.GlobalToast.GloableToast;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by wu on 2016/7/16.
 */
public class MApplication extends Application {
    private static final String TAG = "MApplication";
    /** Data */
    public static Context mContext;
    private Stack<BaseFragmentActivity> activities;
    private static CommonPrefence commonPrefence;

    /*************************************************/
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //全局变量
        initGlobalVar();

        //其他配置
    }

    // 初始化全局变量
    private void initGlobalVar(){
        commonPrefence = new CommonPrefence(mContext);   //初始化sharepre
        FeedbackAPI.init(this, FinalUtil.ALIBC_APP_KEY); //初始化阿里百川
        GloableToast.getInsance(mContext);

        ThreadPool.init();                               //线程池的初始化
    }

    public static CommonPrefence getCommonPrefence(){
        if(commonPrefence == null)
            commonPrefence = new CommonPrefence(mContext);
        return commonPrefence;
    }

    public void addActivity(BaseFragmentActivity activity){
        if(activities == null)
            activities = new Stack<BaseFragmentActivity>();
        activities.push(activity);
    }

    public void removeActivity(BaseFragmentActivity activity){
        if(activities == null || activities.isEmpty())
            return;
        activities.push(activity);
    }

    public void removeActivityAll(){
        if(activities == null || activities.isEmpty())
            return;
        for(BaseFragmentActivity activity:activities)
            activities.pop();
    }

    //全局变量的自定义属性设置 (SDK2.0版本貌似没啥卵用)
    private void setAlibcSetting(){
        Map<String,String> params = new HashMap<>();
        params.put("enableAudio","1");//enableAudio(是否开启语音 1：开启 0：关闭)
        params.put("bgColor","#ffffff");    //bgColor(消息气泡背景色 "#ffffff")，
        params.put("color","#ffffff");      //color(消息内容文字颜色 "#ffffff")，
        params.put("avatar","http://img3.yxlady.com/yl/UploadFiles_5361/20150726/20150726173616914.jpg");     //avatar(当前登录账号的头像)，string，为http url
        params.put("toAvatar","http://image.tianjimedia.com/uploadImages/2015/019/24/IV84D6JHY723.jpg");   //toAvatar(客服账号的头像),string，为http url
        params.put("themeColor","#ffffff"); //themeColor(标题栏自定义颜色 "#ffffff")
        params.put("profilePlaceholder","顶部联系方式");  //profilePlaceholder: (顶部联系方式)，string
        params.put("profileTitle","顶部联系方式左侧提示内容");        //profileTitle: （顶部联系方式左侧提示内容）, String
        params.put("chatInputPlaceholder","输入框里面的内容");//chatInputPlaceholder: (输入框里面的内容),string
        params.put("profileUpdateTitle","更新联系方式标题");  //profileUpdateTitle:(更新联系方式标题), string
        params.put("profileUpdateDesc","更新联系方式文字描述");   //profileUpdateDesc:(更新联系方式文字描述), string
        params.put("profileUpdatePlaceholder","更新联系方式");   //profileUpdatePlaceholder:(更新联系方式), string
        params.put("profileUpdateCancelBtnText","取消更新"); //profileUpdateCancelBtnText: (取消更新), string
        params.put("profileUpdateConfirmBtnText","确定更新");//profileUpdateConfirmBtnText: (确定更新),string
        params.put("sendBtnText","发消息");       //sendBtnText: (发消息),string
        params.put("sendBtnTextColor","white");  //sendBtnTextColor: ("white"),string
        params.put("sendBtnBgColor","red");    //sendBtnBgColor: ('red'),string
        params.put("hideLoginSuccess","隐藏登录成功的toast");  //hideLoginSuccess: true  隐藏登录成功的toast
        params.put("pageTitle","Web容器的标题");         //pageTitle: （Web容器标题）, string
        params.put("photoFromCamera","拍摄一张照片");   //photoFromCamera: (拍摄一张照片),String
        params.put("photoFromAlbum","从相册选取");    //photoFromAlbum: (从相册选取), String
        params.put("voiceContent","点击这里录制语音");      //voiceContent:(点击这里录制语音), String
        params.put("voiceCancelContent","滑到这里取消录音");//voiceCancelContent: (滑到这里取消录音), String
        params.put("voiceReleaseContent","松开取消录音");//voiceReleaseContent: (松开取消录音), String
        //FeedbackAPI.setUICustomInfo(params); //阿里百川中1.0的sdk中有这样的参数,在SDK2.0中没有这样的参数了
    }

}

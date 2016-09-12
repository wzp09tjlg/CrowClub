package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/8/29.
 */
public class WebViewActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    private final String URL = "https://www.baidu.com/";
    /** View */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WebView mWebView;

    /** Data */
    private Context mContext;
    private String mTitle;
    private Handler mHandler = new Handler();

    /**********************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getExtral(getIntent());
        initViews();
    }

    private void getExtral(Intent intent){
        Bundle bundle = intent.getExtras();

        mTitle = bundle.getString("TITLE");
    }

    private void initViews(){
        initTitle();

        mSwipeRefreshLayout = $(R.id.refresh);
        mWebView = $(R.id.webview);

        initData();
    }

    private void initTitle(){
        viewTitle = $(R.id.layout_title);
        imgTitleBack = $(R.id.title_left_img);
        textTitle = $(R.id.title_center_text);
        imgTitleMenu = $(R.id.title_right_img);

        textTitle.setText(mTitle);
        imgTitleMenu.setVisibility(View.INVISIBLE);
    }

    private void initData(){
        mContext = this;
        mSwipeRefreshLayout.setOnRefreshListener(getRefreshListener());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(URL);
            }
        },300);
        initWebView();

        initListener();
    }

    private void initWebView(){
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setTextSize(WebSettings.TextSize.NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        mWebView.setWebChromeClient(new MWebChromeClient());//加载的过程监听  某个过程
        mWebView.setWebViewClient(new MWebViewClient());//加载的状态的监听 某个点
    }

    private void initListener(){
        imgTitleBack.setOnClickListener(this);
    }

    private SwipeRefreshLayout.OnRefreshListener getRefreshListener(){
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               mWebView.reload();
            }
        };
        return listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_img:
                finish();
                break;
        }
    }

    private class MWebChromeClient extends WebChromeClient{//过程的监听
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsTimeout() {
            return super.onJsTimeout();
        }
    }

    private class MWebViewClient extends WebViewClient{//状态的值的监听
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {//每次加载数据都会调用这个方法
            view.loadUrl(url);
            LogUtil.e("shouldOverrideUrlLoading:  url:" + url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
    }
}


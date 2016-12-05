package com.sina.crowclub.view.fragment;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragment;

/**
 * Created by wu on 2016/12/1.
 */
public class WebViewFragment extends BaseFragment {
    /** View */
    private WebView webView;
    /** Data */


    /*******************************************/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("WebViewFragment : onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("WebViewFragment : onCreateView");
        View view  = inflater.inflate(R.layout.fragment_webview,null);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        webView = $(view,R.id.webview);//如果不对webview设置属性 那么打开的就是系统的webview了。这里需要做下了解

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setTextSize(WebSettings.TextSize.NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        webView.setWebChromeClient(new MWebChromeClient());//加载的过程监听  某个过程
        webView.setWebViewClient(new MWebViewClient());//加载的状态的监听 某个点

        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e("WebViewFragment : onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("WebViewFragment : onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e("WebViewFragment : onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e("WebViewFragment : onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("WebViewFragment : onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e("WebViewFragment : onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e("WebViewFragment : onDetach");
    }

    private class MWebChromeClient extends WebChromeClient {//过程的监听
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

    private class MWebViewClient extends WebViewClient {//状态的值的监听
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {//每次加载数据都会调用这个方法
            view.loadUrl(url);
            LogUtil.e("shouldOverrideUrlLoading:  url:" + url);
            return true;
            //重写这个方法时 才能让webview自己去加载 而不是调用的系统的webview去加载数据
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

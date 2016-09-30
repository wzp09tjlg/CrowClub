package com.sina.crowclub;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sina.crowclub.view.activity.CacheActivity;
import com.sina.crowclub.view.activity.FragmentHandleActivity;
import com.sina.crowclub.view.activity.LoadDragActivity;
import com.sina.crowclub.view.activity.MapJsonActivity;
import com.sina.crowclub.view.activity.MessageActivity;
import com.sina.crowclub.view.activity.RefreshRecyclerActivity;
import com.sina.crowclub.view.activity.StarActivity;
import com.sina.crowclub.view.activity.TestActivity;
import com.sina.crowclub.view.activity.UserActivity;
import com.sina.crowclub.view.activity.UserSeriesActivity;
import com.sina.crowclub.view.activity.WebViewActivity;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * 这里构建了一个比较规范的 demo,一些好的变成习惯在这里 会比较好的聚集.
 * 针对自己的编程能力的提高 和 编程习惯的规范 做铺垫
 * */
public class MainActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    private final String TAG = "MainActivity";

    /*** view **/
    private Button mBtnUserStory;
    private Button mBtnMessage;
    private Button mBtnSeries;
    private Button mBtnStar;
    private Button mBtnRefreshRecycler;
    private Button mBtnAlbum;
    private Button mBtnAlbumDetail;
    private Button mBtnFragmentHandler;
    private Button mBtnWebView;
    private Button mBtnMapJson;
    private Button mBtnUser;

    /** data */
    private Context mContext;

    /****************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         initViews();
    }

    private void initViews(){
        mBtnUserStory = $(R.id.btn_user_story);
        mBtnMessage = $(R.id.btn_user_message);
        mBtnSeries = $(R.id.btn_user_series);
        mBtnStar = $(R.id.btn_star);
        mBtnRefreshRecycler = $(R.id.btn_refresh_recycler);
        mBtnAlbum = $(R.id.btn_album);
        mBtnAlbumDetail = $(R.id.btn_album_detail);
        mBtnFragmentHandler = $(R.id.btn_fragment_handler);
        mBtnWebView = $(R.id.btn_webview);
        mBtnMapJson = $(R.id.btn_map_json);
        mBtnUser = $(R.id.btn_user);
        initData();
    }

    private void initData(){
        mContext = this;

        mBtnUserStory.setOnClickListener(this);
        mBtnMessage.setOnClickListener(this);
        mBtnSeries.setOnClickListener(this);
        mBtnStar.setOnClickListener(this);
        mBtnRefreshRecycler.setOnClickListener(this);
        mBtnAlbum.setOnClickListener(this);
        mBtnAlbumDetail.setOnClickListener(this);
        mBtnFragmentHandler.setOnClickListener(this);
        mBtnWebView.setOnClickListener(this);
        mBtnMapJson.setOnClickListener(this);
        mBtnUser.setOnClickListener(this);
        //showDialog();
        //doAddSomeViewToWindow();
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.btn_user_story:
                bundle.putString("TITLE",getResources().getString(R.string.user_story));
                Intent intentUserStory = new Intent(MainActivity.this, TestActivity.class);//
                // UserStoryActivity.class);
                intentUserStory.putExtras(bundle);
                startActivity(intentUserStory);
                break;
            case R.id.btn_user_message:
                bundle.putString("TITLE",getResources().getString(R.string.user_message));
                Intent intentUserMessage = new Intent(MainActivity.this, MessageActivity.class);
                intentUserMessage.putExtras(bundle);
                startActivity(intentUserMessage);
                break;
            case R.id.btn_user_series:
                bundle.putString("TITLE",getResources().getString(R.string.user_series));
                Intent intentUserSeries = new Intent(MainActivity.this, UserSeriesActivity.class);
                intentUserSeries.putExtras(bundle);
                startActivity(intentUserSeries);
                break;
            case R.id.btn_star:
                Intent intentStar = new Intent(MainActivity.this, StarActivity.class);
                startActivity(intentStar);
                break;
            case R.id.btn_refresh_recycler:
                bundle.putString("TITLE",getResources().getString(R.string.refreshRecycler));
                Intent intentRefreshRecycler = new Intent(MainActivity.this, RefreshRecyclerActivity.class);
                intentRefreshRecycler.putExtras(bundle);
                startActivity(intentRefreshRecycler);
                break;
            case R.id.btn_album:
                //AlbumActivity.launch(mContext,"连载");
                Bundle bundle1 = new Bundle();
                //bundle.putString("DATA","123");
                Intent intent = new Intent(MainActivity.this,LoadDragActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.btn_album_detail:
                Bundle bundle2 = new Bundle();
                bundle2.putString("TITLE","ALBUM DETAIL");
                Intent intent1 = new Intent(MainActivity.this, CacheActivity.class);
                intent1.putExtras(bundle2);
                startActivity(intent1);
                break;
            case R.id.btn_fragment_handler:
                Bundle bundleFragmentHandler = new Bundle();
                bundleFragmentHandler.putString("TITLE","FRAGMENTAHNDLER");
                Intent intentFragmentHandler =new Intent(MainActivity.this, FragmentHandleActivity.class);
                intentFragmentHandler.putExtras(bundleFragmentHandler);
                startActivity(intentFragmentHandler);
                break;
            case R.id.btn_webview:
                Bundle bundleWebView = new Bundle();
                bundleWebView.putString("TITLE","WebView");
                Intent intentWebView =new Intent(MainActivity.this, WebViewActivity.class);
                intentWebView.putExtras(bundleWebView);
                startActivity(intentWebView);
                break;
            case R.id.btn_map_json:
                Bundle bundleMapJson = new Bundle();
                bundleMapJson.putString("TITLE","WebView");
                Intent intentMapJson =new Intent(MainActivity.this, MapJsonActivity.class);
                intentMapJson.putExtras(bundleMapJson);
                startActivity(intentMapJson);
                break;
            case R.id.btn_user:
                Bundle bundleUser = new Bundle();
                bundleUser.putString("TITLE","USER");
                Intent intentUser = new Intent(MainActivity.this, UserActivity.class);
                intentUser.putExtras(bundleUser);
                startActivity(intentUser);
                break;
        }
    }

    private void doAddSomeViewToWindow(){
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();// 获取WINDOW界面的
        //Gravity.TOP|Gravity.LEFT;这个必须加
        windowParams.gravity = Gravity.CENTER;//Gravity.TOP | Gravity.LEFT;
        //得到preview左上角相对于屏幕的坐标
        //windowParams.x = 300;
        //windowParams.y = 300;
        //设置拖拽item的宽和高
        windowParams.width = (int) (300);// 放大dragScale倍，可以设置拖动后的倍数
        windowParams.height = (int) (300);// 放大dragScale倍，可以设置拖动后的倍数
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;//透明的处理
        windowParams.windowAnimations = 0;
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_nice_girl_eat));
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);// "window"
        windowManager.addView(iv, windowParams);
    }

    protected void showDialog(){
        Dialog dialog = new Dialog(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout layout = new LinearLayout(mContext);
        TextView text = new TextView(mContext);
        ViewGroup.LayoutParams layoutParams1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addView(text,layoutParams1);
        dialog.addContentView(layout,layoutParams);
        dialog.show();
    }
}

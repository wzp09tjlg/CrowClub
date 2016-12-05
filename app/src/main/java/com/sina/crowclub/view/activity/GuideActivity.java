package com.sina.crowclub.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.CommonHelper;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragmentActivity;

/**
 * Created by wu on 2016/12/4.
 */
public class GuideActivity extends BaseFragmentActivity {
    /** View */
    private SwipeRefreshLayout refreshLayout;
    private WebView webView;
    private ImageView imgGuide;
    /** Data */
    private Context mContext;
    /**************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
    }

    private void initViews(){
        refreshLayout = $(R.id.layout_refresh);
        webView = $(R.id.webview);
        imgGuide = $(R.id.img_guide);

        initData();
    }

    private void initData(){
        mContext = this;
        int[] temp = CommonHelper.getDisplayValue(mContext);
        float desty = CommonHelper.getDisplayDesity(mContext);

        LogUtil.e("w*h:" + temp[0] + "*" + temp[1]);
        LogUtil.e("desty:" + desty);
    }

    @Override
    protected void addGuide() {
        super.addGuide();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guide_1);
        imgGuide.setImageBitmap(getScaleDrawable(bitmap));
        //scaleImage(this,imgGuide,R.drawable.guide_9);
    }

    //1536 * 1952
    private void scaleImage(final Activity activity, final View view, int drawableResId) {
        // 获取屏幕的高宽
        Point outSize = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);
        // 解析将要被处理的图片
        Bitmap resourceBitmap = BitmapFactory.decodeResource(activity.getResources(), drawableResId);

        if (resourceBitmap == null) {
            return;
        }

        // 开始对图片进行拉伸或者缩放（两个方想向的拉伸缩放处理）

        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize.x * 1.0f / resourceBitmap.getWidth());

        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize.x, bitmapScaledHeight, false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;

                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = view.getMeasuredHeight();

                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;

                // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(),
                        scaledBitmap.getHeight() - offset * 2);

                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                    scaledBitmap.recycle();
                    System.gc();
                }

                // 设置图片显示
                view.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), finallyBitmap));
                return true;
            }
        });
    }

    private Bitmap getScaleDrawable(Bitmap icon){
        int width = icon.getWidth(), height = icon.getHeight();
        LogUtil.e("w:" + width + "  h:" + height);
        int[] temp = CommonHelper.getDisplayValue(mContext);
        float xScale = 1.0f *  temp[0] / width ;
        float yScale = 1.0f *  temp[1] / height;
        Matrix matrix = new Matrix();
        matrix.postScale(1 * xScale, 1 * yScale);
        // 得到新的图片
        Bitmap newIcon = Bitmap.createBitmap(icon, 0, 0, width, height, matrix, true);
        icon.recycle();
        return newIcon;
    }
}

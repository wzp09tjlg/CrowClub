package com.sina.crowclub.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.CommonHelper;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.service.CommonService;

import java.io.InputStream;

/**
 * Created by wu on 2016/12/4.
 */
public class GuideActivity extends BaseFragmentActivity {
    /** View */
    private ViewGroup rootView;
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
        rootView = $(R.id.root_view);
        refreshLayout = $(R.id.layout_refresh);
        webView = $(R.id.webview);
        imgGuide = $(R.id.img_guide);

        initData();
    }

    private void someMethod(){
        LogUtil.e("Activity's context id:" + GuideActivity.this.mContext );
        LogUtil.e("getBaseContext() id:" + getBaseContext());
        Intent intentS = new Intent(GuideActivity.this, CommonService.class);
        startService(intentS);
    }

    private void initData(){
        mContext = this;

        LogUtil.e("Activity's context id:" + GuideActivity.this.mContext );
        LogUtil.e("getBaseContext() id:" + getBaseContext());

        float desity = CommonHelper.getDisplayDesity(mContext);
        LogUtil.e("desity:" + desity);
    }

    @Override
    protected void addGuide() {
        super.addGuide();
        //测试方案 加载大图(初次) 耗费内存21 直接添加bitmap 或者是drawable 消耗内存的情况
        /*imgGuide.setImageResource(R.drawable.guide_1);*/
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guide_1);
        LogUtil.e("width:" + bitmap.getWidth() + "  height:" + bitmap.getHeight());
        LogUtil.e("width * height:" + (bitmap.getWidth() * bitmap.getHeight()));
        imgGuide.setImageBitmap(bitmap);

        //采用其AGB565编码 显示 消耗内存5M
        /*CommonHelper.scaleImage(this,imgGuide,R.drawable.guide_1);*/

        //方案0  手机 10M 平板 显示不全 17M
        /*final ImageView imageView = new ImageView(mContext);
        CommonHelper.scaleImage(this,imageView,R.drawable.guide_1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootView.removeView(imageView);
            }
        });
        rootView.addView(imageView);*/
        //方案1  手机 10m 平板 只显示一半，另一半是空白 12M
        /*GuideImageView imageView = new GuideImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setBitmap(readBitmap(mContext,R.drawable.guide_1));
        rootView.addView(imageView);//添加引导图片*/
        //方案2 手机 10M 平板 显示不全 12M
        /*ImageView guideImage = new ImageView(mContext);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
        guideImage.setLayoutParams(params);
        guideImage.setScaleType(ImageView.ScaleType.CENTER_CROP);//如果不加这行代码 那么就会出现两边白边，加上这刚代码 平板上适配很丑
        guideImage.setImageBitmap(readBitmap(mContext,R.drawable.guide_1));
        rootView.addView(guideImage);*/
        //方案3 手机 80M 平板 显示中间 两边留白 40M
        /*int[] temp = CommonHelper.getDisplayValue(mContext);
        Bitmap bitmap = decodeSampledBitmapFromResource(getResources(),R.drawable.guide_1,temp[0],temp[1]);
        imgGuide.setImageBitmap(bitmap);*/
    }

    private void scaleImage(final Activity activity, final ImageView imageView, int drawableResId) {
        // 获取屏幕的高宽
        int[] outSize = CommonHelper.getDisplayValue(activity);
        // 解析将要被处理的图片
        Bitmap resourceBitmap = readBitmap(mContext,drawableResId);//获取的图片就是采用 RGB565 和 RGB8888没关系，关键是看获取的option类型
        if (resourceBitmap == null) {
            return;
        }

        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize[0] * 1.0f / resourceBitmap.getWidth());
        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize[0], bitmapScaledHeight, false);

        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;

                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = imageView.getMeasuredHeight();

                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;
                // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(),
                        scaledBitmap.getHeight() - offset * 2);

                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                    scaledBitmap.recycle();
                    System.gc();
                }
                BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), finallyBitmap);
                imageView.setBackgroundDrawable(bd);
                return true;
            }
        });
    }

    public Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //计算缩放比例
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}

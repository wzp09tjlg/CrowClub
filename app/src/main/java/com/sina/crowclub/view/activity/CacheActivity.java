package com.sina.crowclub.view.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sina.crowclub.R;
import com.sina.crowclub.utils.CommonHelper;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragmentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by wu on 2016/8/18.
 */
public class CacheActivity extends BaseFragmentActivity implements
        View.OnClickListener
{
    private static final String TAG = CacheActivity.class.getSimpleName();
    /** View */
    private Button btnCache;
    private Button btnGetCache;
    private ImageView imgGetCache;
    private TextView textInfo;
    /** Data */
    private Context mContext ;
    private int count = 1;
    private String[] params = new String[]{
            "http://b.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=c091c5ca0bfa513d51aa6bd8055632c6/314e251f95cad1c8f698ac317c3e6709c93d5180.jpg"
            ,"http://e.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=42f2a7fab01c8701d6b6b5e01f44f912/e1fe9925bc315c60582972f18eb1cb134954776b.jpg"
            ,"http://e.hiphotos.baidu.com/baike/s%3D500/sign=680e32f48eb1cb133a693c13ed5556da/b812c8fcc3cec3fda0a5fe6ed588d43f87942715.jpg"};


    /************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        initViews();
    }

    private void initViews(){
        btnCache = $(R.id.btn_cache);
        btnGetCache = $(R.id.btn_getCache);
        imgGetCache = $(R.id.img_cache);
        textInfo = $(R.id.text_info);

        initData();
    }

    private void initData(){
        mContext = this;

        textInfo.setText("");

        btnCache.setOnClickListener(this);
        btnGetCache.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cache:
                //1.存缓存
                doSavedCache(params);

                String url = "http://www.baidu.com/index.php?a=1&b=2&c=3&d=cnjsahdfksjafjksd";

                int tempPosition = url.indexOf("?");

                LogUtil.e("tempPosition:" + tempPosition);

                Map<String,String> map =  CommonHelper.Url2Map(url);

                if(map == null){
                    LogUtil.e("is null");
                }else{
                    LogUtil.e("is not null");
                }

                /*count = count + 1;
                String url = params[count % params.length];
                Glide
                        .with(mContext)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .signature(new StringSignature("1.0.0"))
                        .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        LogUtil.e("wuzp: onException  model:" + model + "   e.getMessage():" + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        LogUtil.e("wuzp  onResourceReady  model:" + model + " isFromMemoryCache: " + isFromMemoryCache);
                        return false;
                    }
                }).crossFade()
                        .dontAnimate()
                        .into(imgGetCache);*/

                break;
            case R.id.btn_getCache:
                count = count + 1;
//                Glide
//                        .with(mContext)
//                        .load(params[count % params.length])
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .signature(new StringSignature("1.0.0"))
//                        .listener(new RequestListener<String, GlideDrawable>() {
//                            @Override
//                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                LogUtil.e("wuzp: onException  model:" + model + "   e.getMessage():" + e.getMessage());
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                LogUtil.e("wuzp  onResourceReady  model:" + model + " isFromMemoryCache: " + isFromMemoryCache);
//                                return false;
//                            }
//                        }).crossFade()
//                        .dontAnimate()
//                        .into(imgGetCache);


               final  String url1 = params[count % params.length];
                File file = Glide.getPhotoCacheDir(mContext);
                String[] paths = file.list();
                for(int i=0;i<paths.length;i++){
                    LogUtil.e("i=" + i + "   file:" + paths[i]);
                }

                File tempPic = new File(file.getPath());
                if(tempPic == null)
                    LogUtil.e("tempFile is null");
                else
                {
                    LogUtil.e("tempFile is not null");
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                           /*final  Bitmap bitmap =  Glide.with(mContext)
                                    .load(url1)
                                    .asBitmap()
                                    //.fitCenter()
                                    .listener(new RequestListener<String, Bitmap>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            String base64 =  imageZoom(resource);
                                            LogUtil.e("1222 base64;" + base64);
                                            return false;
                                        }
                                    })
                                    .into(1080, 1080)
                                    .get();*/

                            byte[] bytes = Glide.with(mContext) //缓存做压缩处理了。得到的图片是模糊不清的图片
                                    .load(url1)
                                    .asBitmap()
                                    .toBytes(Bitmap.CompressFormat.JPEG, 90)//这里控制了图片显示的质量
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(1080 , 1080)
                                    .get();

                            LogUtil.e("byte length:" + bytes.length);
                           final  Bitmap bitmap1 =  BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imgGetCache.setImageBitmap(bitmap1);
                                    imgGetCache.invalidate();
                                }
                            });
                        }catch (Exception e){
                            LogUtil.e("e.message:" + e.getMessage());
                        }
                    }
                }).start();
                break;
        }
    }

    private void doSavedCache(String[] images){
        if(images == null || images.length == 0) return;//如果为空值 就直接同步内容 或保存本地数据库

        boolean isLast = false;
        for(int i=0;i<images.length;i++){
            if (i == images.length -1)
                isLast = true;
            doSaveOperate(images[i],isLast);
        }
    }

    private void doSaveOperate(final String url,final boolean isLast){
        new Thread(new Runnable() { //缓存的动作在子线程中执行，上传数据的动作回到主线程中操作
            @Override
            public void run() {
                Glide.with(mContext).load(url)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                if(isLast){//不管是不是将图片缓存成功,在最后的动作都是保存数据.加回调的目的是将保存数据的动作放在 缓存图片之后
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mSavedCacheCallBack.onSavedCacheCallBack();
                                        }
                                    });
                                }
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                LogUtil.e("do save image ,and model is:" + model + "  width:" + resource.getWidth() + "  height:" + resource.getHeight());
                                if(isLast){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mSavedCacheCallBack.onSavedCacheCallBack();
                                        }
                                    });
                                }
                                return false;
                            }
                        })
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            }
        }).start();

    }


    private OnSavedCacheCallBack mSavedCacheCallBack = getSavedCacheCallBack();
    private OnSavedCacheCallBack getSavedCacheCallBack(){
        mSavedCacheCallBack = new OnSavedCacheCallBack() {
            @Override
            public void onSavedCacheCallBack() {}
        };
        return mSavedCacheCallBack;
    }

    public interface OnSavedCacheCallBack{
        void onSavedCacheCallBack();
    }

    public static String imageZoom(String path) {
        //图片允许最大空间   单位：KB
        float maxSize = 500.00f;
        int maxWidth = 1080;
        File file = new File(path);
        if (file != null && file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);

                // 取得图片
                BitmapFactory.Options options = new BitmapFactory.Options();
                // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），
                // 说白了就是为了内存优化
                options.inJustDecodeBounds = true;
                // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
                BitmapFactory.decodeStream(fis, null, options);
                fis.close();

                int srcWidth = options.outWidth;


                // 先根据宽度缩放图片
                int i = 0;
                Bitmap scaleBitmap = null;
                while (true) {
                    // 这一步是根据要设置的大小，使宽和高都能满足
                    if (srcWidth >> i <= maxWidth) {
//                    if ((options.outWidth >> i <= maxWidth)
//                            && (options.outHeight >> i <= maxWidth)) {
                        // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                        fis = new FileInputStream(file);

                        // 这个参数表示 新生成的图片为原始图片的几分之一。
                        options.inSampleSize = (int) Math.pow(2.0D, i);
                        // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                        options.inJustDecodeBounds = false;
                        scaleBitmap = BitmapFactory.decodeStream(fis, null, options);
                        fis.close();
                        break;
                    }
                    i += 1;
                }

                // 旋转图片
                Bitmap descBitmap  = scaleBitmap;
                if (scaleBitmap != null && scaleBitmap != descBitmap) {
                    scaleBitmap.recycle();
                }

                // JPG压缩图片
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                descBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] data = baos.toByteArray();

                //将字节换成KB
                double mid = data.length / 1024;
                if (mid > maxSize) {
                    double scale = mid / maxSize;
                    //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍
                    // （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
                    Bitmap newBitMap = zoomImage(descBitmap, descBitmap.getWidth() / Math.sqrt(scale),
                            descBitmap.getHeight() / Math.sqrt(scale));
                    if (descBitmap != null && descBitmap != newBitMap) {
                        descBitmap.recycle();
                    }

                    baos.close();
                    baos = new ByteArrayOutputStream();
                    newBitMap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    data = baos.toByteArray();
                    if (newBitMap != null) {
                        newBitMap.recycle();
                    }
                } else {
                    if (descBitmap != null) {
                        descBitmap.recycle();
                    }
                }

                baos.close();
                if (data != null) {
                    String base64ImgStr = Base64.encodeToString(data, Base64.NO_WRAP);
                    return base64ImgStr;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static String imageZoom(Bitmap bitmap) {
        if (bitmap != null ) {
            try {
                byte[] data = Bitmap2Bytes(bitmap);
                if (data != null) {
                    String base64ImgStr = Base64.encodeToString(data, Base64.NO_WRAP);
                    return base64ImgStr;
                }
            } catch (Exception e){

            }
        }
        return "";
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}

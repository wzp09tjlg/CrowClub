package com.sina.crowclub.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wu on 2016/12/11.
 * 为处理引导页中占用大量的内存问题，以后使用引导页都使用自己定义的这种控件
 */
public class GuideImageView extends View {
    /** Data */
    private Bitmap bitmap;
    private Paint mPaint;//画笔
    /***************************************/
    public GuideImageView(Context context){
        super(context);
        initViews();
    }

    public GuideImageView(Context context, AttributeSet attr){
        super(context,attr);
        initViews();
    }

    public GuideImageView(Context context,AttributeSet attr,int flag){
        super(context,attr,flag);
        initViews();
    }

    private void initViews(){
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);*/
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    //自己去画一个view图，所以这里需要自己重写一个draw方法，看看这种方式 能不能降低对内存的消耗
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap != null){
            canvas.drawBitmap(bitmap,0,0,mPaint);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        mPaint = new Paint();
        postInvalidate();//设置图片之后 需要刷新一下，重新绘制view
    }
}

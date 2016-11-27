package com.sina.crowclub.view.widget.SelfPageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.OverScroller;

import com.sina.crowclub.utils.LogUtil;

/**
 * Created by wu on 2016/10/23.
 */
public class PageView extends View {
    /** DATA */
    private Paint paint=new Paint();
    private Paint paint2=new Paint();

    private int pageSizeWidth = 0; //一页的宽度
    private int pageSizeHeight = 0;//一页的高度

    private int curPageLeft = 0;  //当前页的Left坐标
    private int curPageTop = 0;   //当前页的Top坐标

    private int nextPageLeft = 0;  //下一页的Left坐标
    private int nextPageTop = 0;   //下一页的Top坐标

    private OverScroller overScroller;
    /*********************************/
    public PageView(Context context){
        super(context);
        init(context);
    }

    public PageView(Context context, AttributeSet attr){
        super(context,attr);
        init(context);
    }

    public PageView(Context context,AttributeSet attr,int flag){
        super(context,attr,flag);
        init(context);
    }

    private void init(Context context){
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        pageSizeWidth = display.widthPixels;
        pageSizeHeight = display.heightPixels / 2;//测试使用100个点

        LogUtil.e("pageSizeWidth:" + pageSizeWidth + "  pageSizeHeight:" + pageSizeHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*drawBackgroud(canvas);//绘制背景
        drawCutPage(canvas);  //绘制上一页
        drawNextPage(canvas); //绘制下一页*/
        canvas.drawColor(Color.GREEN);

        Paint paint=new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.BLUE);
        canvas.drawText("绿色部分为Canvas剪裁前的区域", 20, 80, paint);
        canvas.save();

        Rect rect=new Rect(20,200,900,1000);
        canvas.clipRect(rect);
        canvas.drawColor(Color.YELLOW);
        paint.setColor(Color.BLACK);
        canvas.drawText("黄色部分为Canvas剪裁后的区域", 10, 310, paint);

        canvas.restore();
        paint.setColor(Color.RED);
        canvas.drawText("restore() 之后的区域和文字", 20, 170, paint);
    }

    private void drawBackgroud(Canvas canvas){
        canvas.drawColor(0x90ff02);//这里是颜色还是颜色的地址？
    }

    private void drawCutPage(Canvas canvas){
        canvas.save();
        Rect rect=new Rect(curPageLeft,curPageTop,pageSizeWidth,pageSizeHeight);
        LogUtil.e("curPageLeft:" + curPageLeft + "   curPageTop:" + curPageTop);
        canvas.clipRect(rect);
        paint.setTextSize(60);
        paint.setColor(Color.BLUE);
        canvas.drawText(content1, 20, 80, paint);
        canvas.restore();
    }

    private void drawNextPage(Canvas canvas){

        Rect rect=new Rect( nextPageLeft, nextPageTop,pageSizeWidth,pageSizeHeight);
        LogUtil.e("nextPageLeft:" + nextPageLeft + "   nextPageTop:" + nextPageTop);
        canvas.clipRect(rect);
        paint2.setTextSize(60);
        paint2.setColor(Color.BLACK);
        canvas.drawText(content2, 30, 310, paint2);
        canvas.restore();
    }

    // 设置绘制的当前页 和下一页的各个坐标点
    public void setPageSizeWidth(int pageSizeWidth) {
        this.pageSizeWidth = pageSizeWidth;
    }

    public void setPageSizeHeight(int pageSizeHeight) {
        this.pageSizeHeight = pageSizeHeight;
    }

    public void setCurPageLeft(int curPageLeft) {
        this.curPageLeft = curPageLeft;
    }

    public void setCurPageTop(int curPageTop) {
        this.curPageTop = curPageTop;
    }

    public void setNextPageLeft(int nextPageLeft) {
        this.nextPageLeft = nextPageLeft;
    }

    public void setNextPageTop(int nextPageTop) {
        this.nextPageTop = nextPageTop;
    }

    private String content1 = "沉默片刻，然后说道：“只要活着，一切都有可能，那为何要走？”\n" +
            "　　说话间，来自西陵神殿的强者已经杀至台前，新教的信徒再如何虔诚，也不可能减慢这些人的步伐，只是徒流鲜血罢了。" +
            "　　陈皮皮站在叶苏身后，开始收拾行囊，他如今是个雪山气海皆废的废物，没有办法参与战斗，却显得很平静，很有信心。陈皮皮跪坐在叶苏身边，看着那道白烟，神情微惘，有些痛。\n" +
            "　　对他来说，叶红鱼的死讯，也意味着很多东西，童年的记忆，观里的生活，就此戛然而止，再没有分享的同伴，同时这意味着，父子反目的悲剧。\n" +
            "　　“不是终结。”\n" +
            "　　\n" +
            "　　离开临康城后，这样的情形，已经发生了很多次，他们每次都能冲破西陵神殿的阻截，他相信今天也不会例外，哪怕那道白烟已经升起。\n" +
            "　　因为他相信她asjklhdajkshfdjkahsjdhaklshdklajsdqiowjdokasncklnmlzxncklzjnxklcjaskldjaklsjdma,nsdcasljhd能保护师兄离开。\n" +
            "　　唐小棠站立的位置，在他和叶苏之前。\n";
    private String content2 = "他沉默片刻，然后说道：“只要活着，一切都有可能，那为何要走？”\n" +
            "　　说话间，来自西陵神殿的强者已经杀至台前，新教的信徒再如何虔诚，也不可能减慢这些人的步伐，只是徒流鲜血罢了。\n" +
            "　　陈皮皮站在叶苏身后，开始收拾行囊，他如今是个雪山气海皆废的废物，没有办法参与战斗，却显得很平静，很有信心。\n" +
            "陈皮皮跪坐在叶苏身边，看着那道白烟，神情微234234!@@##$%$%^&&***(惘，有些痛。\n" +
            "　　对他来说，叶红鱼的死讯，也意味着很多东西，童年的记忆，观里的生活，就此戛然而止，再没有分享的同伴，同时这意味着，父子反目的悲剧。\n" +
            "　　“不是终结。”\n" +
            "　　\n" +
            "　　离开临康城后，这样的情形，已经发生了很多次，他们每次都能冲破西陵神殿的阻截，他相信今天也不会例外，哪怕那道白烟已经升起。\n" +
            "　　因为他相信她能保护师兄离开。\n" +
            "　　唐小棠站立的位置，在他和叶苏之前。\n";
}

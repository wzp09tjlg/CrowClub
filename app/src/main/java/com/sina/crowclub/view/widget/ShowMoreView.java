package com.sina.crowclub.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;

/**
 * Created by wu on 2016/11/7.
 * 自定义的view 主要功能是显示文字，
 * 当文字显示过多时 显示规定的行数，
 * 并且提供显示跟过的按钮
 * 当前提供两种显示方式
 * 1.基本方式 "显示更多"就是显示在文字的下边(当然也可以跟在文字的后边,待实现)
 * 2.更多方式 "显示更多"是现在文字的上边,并有颜色渐变的效果
 *
 * 简单的一个组合自定义的view，
 * 涉及到的是如何加载布局 以及通过自定义属性去控制显示的类型
 */
public class ShowMoreView extends RelativeLayout implements
        View.OnClickListener
{
    private static final int DEFAULT_LINES_BELOW = 2;    //一般显示方式 是显示两行
    private static final int DEFAULT_LINES_FLOATING = 4; //浮层显示方式 是显示四行
    private static final int TYPE_BELOW = 0;     //默认显示的方式
    private static final int TYPE_FLOATING = 1;  //浮层显示方式

    private static final int STATE_CLOSE = 0;    //当前的状态关闭状态
    private static final int STATE_OPEN = 1;     //当前的状态打开状态

    /** View */
    private TextView textContent;
    private TextView textShowMore;

    /** Data */
    private int mLines = DEFAULT_LINES_BELOW;  //默认显示的函数
    private int mShowType;                     //显示的类型
    private int mCurState = STATE_CLOSE;

    private String mContent = "";

    /*******************************************/
    public ShowMoreView(Context context){
        super(context);
        init(context,null);
    }

    public ShowMoreView(Context context, AttributeSet attribute){
        super(context,attribute);
        init(context,attribute);
    }

    public ShowMoreView(Context context,AttributeSet attributeSet,int flag){
        super(context,attributeSet,flag);
        init(context,attributeSet);
    }

    private  void init(Context context,AttributeSet attributeSet){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ShowMoreView);
        mLines = typedArray.getInteger(R.styleable.ShowMoreView_defaultLines,DEFAULT_LINES_BELOW);
        mShowType = typedArray.getInteger(R.styleable.ShowMoreView_showType,TYPE_BELOW);
        typedArray.recycle();//这种资源在使用之后 应该记得及时回收

        initView(context);
    }

    private void initView(Context context){
        View viewRoot = null;
        if(mShowType == TYPE_BELOW){
            viewRoot = LayoutInflater.from(context).inflate(R.layout.view_showmore_showbelow,null);
       }else{
            viewRoot = LayoutInflater.from(context).inflate(R.layout.view_showmore_showfloating,null);
            mLines = DEFAULT_LINES_FLOATING;
       }
        textContent = (TextView)viewRoot.findViewById(R.id.text_content);
        textShowMore = (TextView)viewRoot.findViewById(R.id.text_showmore);
        textContent.setMaxLines(mLines);
        textShowMore.setOnClickListener(this);
        addView(viewRoot);//添加到布局中去.这种方式添加的布局 会多一层布局，因为本身就是一层布局，这里再了加一层布局
    }

    @Override//对每个子view 进行绘制
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_showmore:
                doOperateShow();
                break;
        }
    }

    private void doOperateShow(){
        if(mCurState == STATE_CLOSE){ //当前是关闭状态,做打开处理
            mCurState = STATE_OPEN;
            if(!TextUtils.isEmpty(mContent)){
                int tempTextWidth = textContent.getWidth();
                int lastCharIndex = getLastCharIndexForLimitTextView(textContent,mContent,tempTextWidth,mLines);
                LogUtil.e("abcd","tempTextWidth:" + tempTextWidth + "  lastCharIndex:" + lastCharIndex);
                if(lastCharIndex > -1){
                    textContent.setMaxLines(lastCharIndex);
                    textContent.invalidate();
                }
                textShowMore.setVisibility(INVISIBLE);
            }
        }else{//当前是打开状态,做关闭处理
            mCurState = STATE_CLOSE;
        }
    }

    private int getLastCharIndexForLimitTextView(TextView textView, String content, int width, int maxLine){
        TextPaint textPaint  = textView.getPaint();
        StaticLayout staticLayout = new StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        if(staticLayout.getLineCount()>maxLine) return staticLayout.getLineStart(maxLine) - 1;//exceed 超过得了设置的最大行
        else return -1;//not exceed the max line
    }

    /*** 暴露的方法 */
    public void setmLines(int mLines) {
        this.mLines = mLines;
    }

    public void setContent(String content){
        this.mContent = content;
        textContent.setText(content);
        int tempTextWidth = textContent.getWidth();
        int tempContentLine = getLastCharIndexForLimitTextView(textContent,content
                ,tempTextWidth,mLines);
        if(tempContentLine <= mLines){ //如果现实的行数 比最大行小或者刚好等于最大行，那么就不显示 点击显示更多
           textShowMore.setVisibility(INVISIBLE);
        }else{
            textShowMore.setVisibility(VISIBLE);
        }
        postInvalidate();
    }

}

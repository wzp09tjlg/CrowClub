package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.view.adapter.AbsBaseAdapter;
import com.sina.crowclub.view.adapter.StoryBean;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/7/15.
 * 就是实现滑动的效果，具体网络数据 模拟就行。
 */
public class UserStoryActivity extends BaseFragmentActivity implements
  RefreshLayout.OnLoadListener,ListView.OnScrollListener
{
    private static final String TAG = UserStoryActivity.class.getSimpleName();
    private static final String TITLE = "TITLE";

    //排序类型
    private final String SORT_STORY = "SORT_STORY";
    private final String SORT_SERIES = "SORT_SERIES";
    private final String SORT_TYPE_TIME = "SORT_TYPE_TIME";
    private final String SORT_TYPE_SINGLE = "SORT_TYPE_SINGLE";
    private final String SORT_TYPE_SERIES = "SORT_TYPE_SERIES";
    private final String SORT_TYPE_TIME_ORDER = "SORT_TYPE_TIME_ORDER";
    private final String SORT_TYPE_TIME_REVERSE_ORDER = "SORT_TYPE_TIME_REVERSE_ORDER";

//    貌似在安卓中使用枚举效率不高,所以不推荐使用枚举.使用静态变量会效率更好些
//    enum story_sort{
//        SORT_STORY,SORT_SERIES
//    }
//
//    enum story_type_sort{
//        SORT_TYPE_TIME,SORT_TYPE_SINGLE,SORT_TYPE_SERIES
//    }
//
//    enum story_type_time_sort{
//        SORT_TYPE_TIME_ORDER,SORT_TYPE_TIME_REVERSE_ORDER
//    }

    /** view */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView  textTitle;
    private ImageView imgTitleMenu;

    private RefreshLayout mRefreshLayout;

    //浮层排序
    private View viewSortF;
    private TextView textStorySortF;
    private TextView textSeriesSortF;
    private View viewStorySortDividerF;
    private View viewSeriesSortDividerF;
    private View viewSortDividerF;

    //浮层故事类型排序
    private View viewTypeSortF;
    private TextView textStoryTimeSortF;
    private TextView textStorySingleSortF;
    private TextView textStorySeriesSortF;
    private View viewStoryTypeDividerF;

    //浮层故事类型排序选项
    private View viewTypeSortContentF;
    private ListView listTypeSortContextF;

    //布局中排序
    private View viewSort;
    private TextView textStorySort;
    private TextView textSeriesSort;
    private View viewStorySortDivider;
    private View viewSeriesSortDivider;
    private View viewSortDivider;

    //布局故事类型排序
    private View viewTypeSort;
    private TextView textStoryTimeSort;
    private TextView textStorySingleSort;
    private TextView textStorySeriesSort;
    private View viewStoryTypeDivider;

    /** data */
    private Context mContext;
    private String mTitle;
    private LayoutInflater layoutInflater;

    private List<StoryBean> mData;
    private AbsBaseAdapter adapter;

    private String mStorySort = SORT_STORY;
    private String mStorySortType = SORT_TYPE_TIME;
    /************************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);
        getBundleData(getIntent()); //这里使用 getIntent() getExtras() 都可以？
        initViews();
    }

    private void getBundleData(Intent intent){
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString(TITLE,"");
    }

    private void initViews(){
        initTitle();

        mContext = this;
        mRefreshLayout = $(R.id.my_story_layout);
        layoutInflater = LayoutInflater.from(mContext);

        //浮层排序
        viewSortF = $(R.id.layout_sort);
        textStorySortF = $(viewSortF,R.id.text_story);
        textSeriesSortF = $(viewSortF,R.id.text_series);
        viewStorySortDividerF = $(viewSortF,R.id.view_story_divider);
        viewSeriesSortDividerF = $(viewSortF,R.id.view_series_divider);
        viewSortDividerF = $(viewSortF,R.id.view_story_sort_bottom_divider);
        viewSortF.setVisibility(View.GONE);

        //浮层故事类型排序
        viewTypeSortF = $(R.id.layout_type_sort);
        textStoryTimeSortF = $(viewTypeSortF,R.id.text_sort_time);
        textStorySingleSortF = $(viewTypeSortF,R.id.text_sort_single);
        textStorySeriesSortF = $(viewTypeSortF,R.id.text_sort_series);
        viewStoryTypeDividerF = $(viewTypeSortF,R.id.view_story_type_sort_bottom_divider);
        viewTypeSortF.setVisibility(View.GONE);

        //浮层故事类型排序选项
        viewTypeSortContentF = $(R.id.layout_type_sort_content);
        listTypeSortContextF = $(R.id.list_story_type_sort_content);
        viewTypeSortContentF.setVisibility(View.GONE);

        //布局排序
        viewSort = layoutInflater.inflate(R.layout.view_story_sort,null);//如果一开始不加入父布局,效果会怎样
        textStorySort = $(viewSortF,R.id.text_story);
        textSeriesSort = $(viewSortF,R.id.text_series);
        viewStorySortDivider = $(viewSortF,R.id.view_story_divider);
        viewSeriesSortDivider = $(viewSortF,R.id.view_series_divider);
        viewSortDivider = $(viewSortF,R.id.view_story_sort_bottom_divider);
        mRefreshLayout.addHeaderView(viewSort);

        //布局故事类型排序
        viewTypeSort = layoutInflater.inflate(R.layout.view_story_type_sort,null);//没有加入父布局
        textStoryTimeSort = $(viewTypeSortF,R.id.text_sort_time);
        textStorySingleSort = $(viewTypeSortF,R.id.text_sort_single);
        textStorySeriesSort = $(viewTypeSortF,R.id.text_sort_series);
        viewStoryTypeDivider = $(viewTypeSortF,R.id.view_story_type_sort_bottom_divider);
        mRefreshLayout.addHeaderView(viewTypeSort);

        initData();
    }

    private void initTitle(){
        viewTitle = $(R.id.title_layout);
        imgTitleBack = $(R.id.title_left_img);
        imgTitleMenu = $(R.id.title_right_img);
        textTitle = $(R.id.title_center_text);

        textTitle.setText(mTitle);
    }

    private void initData(){
        String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

        mData = new ArrayList<>();
        for(int i=0;i<100;i++){
            StoryBean bean = new StoryBean();
            bean.id = i;
            bean.name = tempName.substring(new Random().nextInt(5) + 1
                    ,new Random().nextInt(50) + 6 );

            if(TextUtils.isEmpty(bean.name))
                bean.albumName = "StoryName";

            bean.create_time = new Random().nextInt(1000);
            if(i % 3 == 0) {
                bean.albumId = new Random().nextInt(5);
                bean.albumName = tempName.substring(new Random().nextInt(5) + 1
                        ,new Random().nextInt(50) + 6);
                if(TextUtils.isEmpty(bean.albumName))
                    bean.albumName = "this is my Album";
            }
            mData.add(bean);
        }

        adapter = new AbsBaseAdapter(mContext,mData);
        mRefreshLayout.setAdapter(adapter);
        mRefreshLayout.setOnLoadListener(this);
        mRefreshLayout.setOnScrollListener(this);
    }

    /**
     * 上拉加载 下拉刷新 listener
     * */
    @Override
    public void onLoadMore() {
        mRefreshLayout.setPullRefresh(false);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setPullRefresh(false);
    }

    /**
     * listview 滚动listener
     * */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //scrollState 有三种状态 在滚动状态  停止状态  和因为指尖带动的滑动状态
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View firstView = view.getChildAt(0);
        int height = firstView.getHeight();
        int bottom = firstView.getBottom();
        int top  = firstView.getTop();

        // 标题高度
        int titleHeight = viewTitle.getHeight();
        int titleWidth = viewTitle.getWidth();
        int titleBottom = viewTitle.getBottom();

        // 浮层排序高度
        int viewSortFHeight = viewSortF.getHeight();
        int viewSortFWight  = viewSortF.getWidth();

        //因为排序的高度和title的高度不一样，所以这里需要做两个判断，1是针对sort滑动之后做判断，
        // 2是针对typesort滑动做 titleHeight - viewSortHeight 长度的判断，让浮层 viewSortF 滑动覆盖title
        if(firstVisibleItem == 0){
            int offset = top;

            if(top < 0)
                viewSortF.setVisibility(View.VISIBLE);
            else
                viewSortF.setVisibility(View.GONE);

            if(Math.abs(offset) < height){
                viewSortF.layout(0,titleBottom + offset,viewSortFWight
                        ,titleBottom + offset + viewSortFHeight);
                //viewSortF.requestLayout();//没有效果 因为view的大小没有变化 只是位置变化了，所需要的动作是重新绘制
                viewSortF.invalidate(); //要求重新绘制
            }
        }

        int viewSortFTop = viewSortF.getTop(); //如果是滑动太快 会导致滚动方法调用不及时，可能没有显示出来，也可能显示出来zhilayout到一半的位置
        if(firstVisibleItem > 0 && viewSortFTop > 0){
            viewSortF.setVisibility(View.VISIBLE);
            viewSortF.layout(0,0,viewSortFWight,viewSortFHeight);
            viewSortF.invalidate();
        }
    }

    private void setTitlebarColor(float alpha) {
        int color = getResources().getColor(R.color.color_title_bar);
        int red = Color.red(color);
        int green = Color.red(color);
        int blue = Color.red(color);
        viewTitle.setBackgroundColor(Color.argb((int) (256 * alpha), red, green, blue));

        int iconAlpha = (int) ((1.0f - alpha) * 255);
        Drawable leftDrawable = viewTitle.getBackground();
        leftDrawable.setAlpha(iconAlpha);
        viewTitle.setBackgroundDrawable(leftDrawable);
        leftDrawable.clearColorFilter();
    }
}

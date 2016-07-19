package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.CommonPrefence;
import com.sina.crowclub.view.adapter.AbsBaseAdapter;
import com.sina.crowclub.view.adapter.StoryBean;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.widget.TypeSortPopupwindow;
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
        ,View.OnClickListener,TypeSortPopupwindow.ItemPopupWindowListener
        ,PopupWindow.OnDismissListener
{
    private static final String TAG = UserStoryActivity.class.getSimpleName();
    private static final String TITLE = "TITLE";

    //排序类型
    public static final String SORT_STORY = "SORT_STORY";
    public static final String SORT_STORY_TYPE = "SORT_STORY_TYPE";
    public static final String SORT_TYPE_TIME = "SORT_TYPE_TIME";
    public static final String SORT_TYPE_SERIES = "SORT_TYPE_SERIES";

    /** view */
    private ViewGroup viewTitle;
    private ImageView imgTitleBack;
    private TextView  textTitle;
    private ImageView imgTitleMenu;

    private RefreshLayout mRefreshLayout;

    //浮层排序
    private ViewGroup viewSortF;
    private TextView textStorySortF;
    private TextView textSeriesSortF;
    private View viewStorySortDividerF;
    private View viewSeriesSortDividerF;
    private View viewSortDividerF;

    //浮层故事类型排序
    private ViewGroup viewTypeSortF;
    private ViewGroup viewTypeSortWrapperF;
    private TextView textStoryTimeSortF;
    private TextView textStorySingleSortF;
    private TextView textStorySeriesSortF;
    private View viewStoryTypeDividerF;

    //浮层故事类型排序选项
    private ViewGroup viewTypeSortContentF;
    private ListView listTypeSortContentF;

    //浮层故事类型排序选项背景
    private View viewTypeSortContentBackgroud;

    //布局中排序
    private View viewSort;
    private TextView textStorySort;
    private TextView textSeriesSort;
    private View viewStorySortDivider;
    private View viewSeriesSortDivider;
    private View viewSortDivider;

    //布局故事类型排序
    private View viewTypeSort;
    private ViewGroup viewTypeSortWrapper;
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

    private int mStorySort = 0; // 0 表示我的故事 1 表示我的连载
    private int mStorySortType = 0; // 0 表示按照时间 1 表示按照单个故事 2 表示按照连载
    private int mStorySortTypeTime = 0; //0 表示按照时间顺序 1表示按照时间逆序
    private int mStorySortTypeSeries = 0; // 表示选中的连载的位置 从0开始

    private TypeSortPopupwindow typeSortPopupwindow;

    /************************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);
        getBundleData(getIntent()); //这里使用 getIntent() getExtras() 都可以？
        initViews();
    }

    private void getBundleData(Intent intent){
        //从intent中获取数据
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString(TITLE,"");

        //从本地保存文件中获取数据
        mStorySort = CommonPrefence.get(SORT_STORY,0);//默认是我的故事
        mStorySortType = CommonPrefence.get(SORT_STORY_TYPE,0);//默认是时间排序
        mStorySortTypeTime = CommonPrefence.get(SORT_TYPE_TIME,0);//按照时间顺序排序
        mStorySortTypeSeries = CommonPrefence.get(SORT_TYPE_SERIES,0);//默认是选中第一个位置的连载
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
        doOperateSort(viewSortF,mStorySort);
        viewSortF.setVisibility(View.GONE);

        //浮层故事类型排序
        viewTypeSortF = $(R.id.layout_type_sort);
        viewTypeSortWrapperF = $(viewTypeSortF,R.id.layout_type_sort_wrapper);
        textStoryTimeSortF = $(viewTypeSortF,R.id.text_sort_time);
        textStorySingleSortF = $(viewTypeSortF,R.id.text_sort_single);
        textStorySeriesSortF = $(viewTypeSortF,R.id.text_sort_series);
        viewStoryTypeDividerF = $(viewTypeSortF,R.id.view_story_type_sort_bottom_divider);
        doOperateStorySortType(viewTypeSortF,mStorySortType);
        viewTypeSortF.setVisibility(View.GONE);

        //浮层故事类型排序选项
        viewTypeSortContentF = $(R.id.layout_type_sort_content);
        listTypeSortContentF = $(R.id.list_story_type_sort_content);
        viewTypeSortContentF.setVisibility(View.GONE);

        //浮层故事类型排序选项背景
        viewTypeSortContentBackgroud = $(R.id.view_story_type_sort_bottom_cover);
        viewTypeSortContentBackgroud.setVisibility(View.GONE);

        //布局排序
        viewSort = layoutInflater.inflate(R.layout.view_story_sort,null);//如果一开始不加入父布局,效果会怎样
        textStorySort = $(viewSort,R.id.text_story);
        textSeriesSort = $(viewSort,R.id.text_series);
        viewStorySortDivider = $(viewSort,R.id.view_story_divider);
        viewSeriesSortDivider = $(viewSort,R.id.view_series_divider);
        viewSortDivider = $(viewSort,R.id.view_story_sort_bottom_divider);
        doOperateSort((ViewGroup) viewSort,mStorySort);
        mRefreshLayout.addHeaderView(viewSort);

        //布局故事类型排序
        viewTypeSort = layoutInflater.inflate(R.layout.view_story_type_sort,null);//没有加入父布局
        viewTypeSortWrapper = $(viewTypeSort,R.id.layout_type_sort_wrapper);
        textStoryTimeSort = $(viewTypeSort,R.id.text_sort_time);
        textStorySingleSort = $(viewTypeSort,R.id.text_sort_single);
        textStorySeriesSort = $(viewTypeSort,R.id.text_sort_series);
        viewStoryTypeDivider = $(viewTypeSort,R.id.view_story_type_sort_bottom_divider);
        doOperateStorySortType((ViewGroup) viewTypeSort,mStorySortType);
        if(mStorySort == 0)  viewTypeSortWrapper.setVisibility(View.VISIBLE);
        else viewTypeSortWrapper.setVisibility(View.GONE);
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

        initListener();
    }

    private void doOperateSort(ViewGroup viewGroup,int type){
        TextView textStorySort = (TextView)viewGroup.findViewById(R.id.text_story);
        TextView textSeriesSort = (TextView)viewGroup.findViewById(R.id.text_series);

        View viewStorySortDivider = viewGroup.findViewById(R.id.view_story_divider);
        View viewSeriesSortDivider = viewGroup.findViewById(R.id.view_series_divider);

      if(type == 0 ){ //我的故事
          textStorySort.setSelected(true);
          textSeriesSort.setSelected(false);

          viewStorySortDivider.setSelected(true);
          viewSeriesSortDivider.setSelected(false);
      }else if(type == 1){ //我的连载
          textSeriesSort.setSelected(true);
          textStorySort.setSelected(false);

          viewSeriesSortDivider.setSelected(true);
          viewStorySortDivider.setSelected(false);
      }
    }

    private void doOperateStorySortType(ViewGroup viewGroup,int type){
          TextView textStoryTypeTime = (TextView)viewGroup.findViewById(R.id.text_sort_time);
          TextView textStoryTypeSingle = (TextView)viewGroup.findViewById(R.id.text_sort_single);
          TextView textStoryTypeSeries = (TextView)viewGroup.findViewById(R.id.text_sort_series);

          if(type == 0){
              textStoryTypeTime.setSelected(true);
              textStoryTypeSingle.setSelected(false);
              textStoryTypeSeries.setSelected(false);
          }else if(type == 1){
              textStoryTypeTime.setSelected(false);
              textStoryTypeSingle.setSelected(true);
              textStoryTypeSeries.setSelected(false);
          }else if(type == 2){
              textStoryTypeTime.setSelected(false);
              textStoryTypeSingle.setSelected(false);
              textStoryTypeSeries.setSelected(true);
          }
    }

    private void initListener(){
        textStorySortF.setOnClickListener(this);
        textSeriesSortF.setOnClickListener(this);

        textStoryTimeSortF.setOnClickListener(this);
        textStorySingleSortF.setOnClickListener(this);
        textStorySeriesSortF.setOnClickListener(this);

        textStorySort.setOnClickListener(this);
        textSeriesSort.setOnClickListener(this);

        textStoryTimeSort.setOnClickListener(this);
        textStorySingleSort.setOnClickListener(this);
        textStorySeriesSort.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_story:
                if(mStorySort == 0) return;
                mStorySort = 0;
                CommonPrefence.put(SORT_STORY,mStorySort);

                doOperateSort(viewSortF,mStorySort);
                doOperateSort((ViewGroup)viewSort,mStorySort);

                viewTypeSortWrapper.setVisibility(View.VISIBLE);
                viewTypeSortWrapperF.setVisibility(View.VISIBLE);
                mRefreshLayout.getListView().setSelection(0);
                //设置adapter  和 请求数据
                break;
            case R.id.text_series:
                if(mStorySort == 1) return;
                mStorySort = 1;
                CommonPrefence.put(SORT_STORY,1);

                doOperateSort(viewSortF,1);
                doOperateSort((ViewGroup)viewSort,1);

                viewTypeSortWrapper.setVisibility(View.GONE);
                viewTypeSortWrapperF.setVisibility(View.GONE);
                mRefreshLayout.getListView().setSelection(0);
                //设置adapter  和 请求数据
                break;
            case R.id.text_sort_time:
                List<String> mDataTime = new ArrayList<>();
                mDataTime.add("时间顺序");
                mDataTime.add("时间逆序");
                typeSortPopupwindow = new TypeSortPopupwindow(mContext,0,mDataTime,this);
                typeSortPopupwindow.setDismissListener(this);
                typeSortPopupwindow.show(v);
                showTypeSortContentBackgroud();

                if(mStorySortType == 0) return;
                mStorySortType = 0;

                CommonPrefence.put(SORT_STORY_TYPE,0);

                doOperateStorySortType(viewTypeSortF,mStorySortType);
                doOperateStorySortType((ViewGroup) viewTypeSort,mStorySortType);

                mRefreshLayout.getListView().setSelection(0);
                break;
            case R.id.text_sort_single:
                if(mStorySortType == 1) return;
                mStorySortType = 1;

                CommonPrefence.put(SORT_STORY_TYPE,mStorySortType);

                doOperateStorySortType(viewTypeSortF,mStorySortType);
                doOperateStorySortType((ViewGroup) viewTypeSort,mStorySortType);

                mRefreshLayout.getListView().setSelection(0);
                //请求数据，刷新adapter
                break;
            case R.id.text_sort_series:
                List<String> mDataSeries = new ArrayList<>();
                mDataSeries.add("我的连载1");
                mDataSeries.add("我的连载2");
                mDataSeries.add("我的连载3");
                mDataSeries.add("我的连载4");
                mDataSeries.add("我的连载5");
                typeSortPopupwindow = new TypeSortPopupwindow(mContext,0,mDataSeries,this);
                typeSortPopupwindow.setDismissListener(this);
                typeSortPopupwindow.show(v);
                showTypeSortContentBackgroud();

                if(mStorySortType == 2) return;
                mStorySortType = 2;

                CommonPrefence.put(SORT_STORY_TYPE,mStorySortType);

                doOperateStorySortType(viewTypeSortF,mStorySortType);
                doOperateStorySortType((ViewGroup) viewTypeSort,mStorySortType);

                mRefreshLayout.getListView().setSelection(0);
                // 设置 连载的adapter 刷新数据， 处理点击事件
                break;
        }
    }

    private void showTypeSortContentBackgroud(){
        //因为不是在同一个父容器内,所以需要对坐标进行转换 (标题栏高度 + header1的高度 + header2的高度)  /
        // 转换有问题,没有达到效果
        int coverTop = viewTitle.getBottom() + Math.abs(viewSort.getBottom()) + Math.abs(viewTypeSortWrapper.getBottom()) ;

        //做了处理，这里针对view进行布局layout，但是方法不管用。因为view是在最外层布局上，所以这里的针对view的位子做了改变
        viewTypeSortContentBackgroud.setX(0f);
        viewTypeSortContentBackgroud.setY(coverTop * 1.0f);
        viewTypeSortContentBackgroud.invalidate(); //设置需要重新绘制吧，不然还是之前的动作

        viewTypeSortContentBackgroud.setVisibility(View
                .VISIBLE);
    }

    private void hideTypeSortContentBackgroud(){
        viewTypeSortContentBackgroud.setVisibility(View.GONE);
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
        /**针对故事和连载排序 做浮层*/
        View firstView = view.getChildAt(0); //这里获得的view可不是加载到list中的第一个view，
        // 是滑动过程中显示在第一个的view
        int height = firstView.getHeight();
        int top  = firstView.getTop();

        // 标题高度
        int titleHeight = viewTitle.getHeight();
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

        /**针对故事类型排序 做浮层*/
        int viewTypeSortFWidth = viewTypeSortF.getWidth();
        int viewTypeSortFHeight = viewTypeSortF.getHeight();

        if(mStorySort == 0 && firstVisibleItem >= 1 && viewTypeSortWrapper.getVisibility() == View.VISIBLE){
            viewTypeSortF.setVisibility(View.VISIBLE);
            viewTypeSortF.layout(0,titleBottom,viewTypeSortFWidth,titleHeight + viewTypeSortFHeight);
        }

        if(mStorySort == 0 && viewTypeSortWrapper.getVisibility() == View.VISIBLE && viewSortFTop > 0){
            viewTypeSortF.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onItemPopupWindowListener(View view, int position) {
       if(typeSortPopupwindow != null)
           typeSortPopupwindow.dismiss();
        Toast.makeText(mContext,"position:" + position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDismiss() {
        hideTypeSortContentBackgroud();
    }
}

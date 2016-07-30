package com.sina.crowclub.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.adapter.RecyleAdapter;
import com.sina.crowclub.view.base.BaseFragmentActivity;
import com.sina.crowclub.view.fragment.listener.OnListItemClickListener;
import com.sina.crowclub.view.widget.LoadingRecycler.EndlessRecyclerOnScrollListener;
import com.sina.crowclub.view.widget.LoadingRecycler.HeaderAndFooterRecyclerViewAdapter;
import com.sina.crowclub.view.widget.LoadingRecycler.Utils.RecyclerViewStateUtils;
import com.sina.crowclub.view.widget.LoadingRecycler.ViewTer.LoadingFooter;
import com.sina.crowclub.view.widget.helper.ItemTouchHelperAdapter;
import com.sina.crowclub.view.widget.helper.OnStartDragListener;
import com.sina.crowclub.view.widget.helper.SimpleItemTouchHelperCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/7/27.
 */
public class RefreshRecyclerActivity extends BaseFragmentActivity {
    private static final String TAG = RefreshRecyclerActivity.class.getSimpleName();
    public static final String TITLE = "TITLE";
    private static final int COUNT = 102;
    private static final int REQUEST_COUNT = 10;

    /** View */
    private View viewTitle;
    private ImageView imgTitleBack;
    private TextView textTitle;
    private ImageView imgTitleMenu;

    private RecyclerView recyclerView;

    /** Data */
    private Context mContext;
    private String mTitle;
    private int currentCount = 1;

    private List<StoryBean> mData;
    private RecyleAdapter recyleAdapter;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;

    private PreviewHandler mHandler;

    private OnStartDragListener mOnStartDragListener;
    private ItemTouchHelper mItemTouchHelper;

    private OnListItemClickListener<StoryBean> mOnListItemClickListener;
    /************************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycler);
        getBundle(getIntent());
        initViews();
    }

    private void getBundle(Intent intent){
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString(TITLE);
    }

    private void initViews(){
        initTitle();

        recyclerView = $(R.id.refresh_recycler);

        initData();
    }

    private void initTitle(){
        viewTitle = $(R.id.layout_title);
        imgTitleBack = $(R.id.title_left_img);
        textTitle = $(R.id.title_center_text);
        imgTitleMenu = $(R.id.title_right_img);

        textTitle.setText(mTitle);
        imgTitleMenu.setVisibility(View.INVISIBLE);
    }

    private void initData(){
        mContext = this;

        String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

        mData = new ArrayList<>();
        for(int i=0;i<10;i++){
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
            if( i % 5 == 0)
                bean.isEditable = false;
            else
                bean.isEditable = true;
            mData.add(bean);
        }

        currentCount = mData.size();

        recyleAdapter = new RecyleAdapter(mData,getOnStartDragListener(),getOnListItemClickListener());
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(recyleAdapter);

        //改变recycleView 时，固定recycleView的数量大小
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = getItemDecoration(LinearLayoutManager.VERTICAL,1,0xFFDDDDDD);
        recyclerView.addItemDecoration(itemDecoration); //设置分割线
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);

        recyclerView.addOnScrollListener(getScrollerListener);

        mHandler = new PreviewHandler(RefreshRecyclerActivity.this);

        mItemTouchHelper = new ItemTouchHelper(getCallBack(recyleAdapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private EndlessRecyclerOnScrollListener getScrollerListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if(state == LoadingFooter.State.Loading) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }

            if (currentCount < COUNT) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(RefreshRecyclerActivity.this, recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                requestData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(RefreshRecyclerActivity.this, recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEndNothing, null);
            }
        }
    };

    private void requestData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一下网络请求失败的情况
                mHandler.sendEmptyMessage(-1);
                //mHandler.sendEmptyMessage(-3); //断网情况
            }
        }.start();
    }

    private static class PreviewHandler extends Handler {
        private WeakReference<RefreshRecyclerActivity> ref;
        PreviewHandler(RefreshRecyclerActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final RefreshRecyclerActivity activity = ref.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            switch (msg.what) {
                case -1:
                    int currentSize = activity.recyleAdapter.getItemCount();
                    //模拟组装10个数据
                    ArrayList<StoryBean> newList = new ArrayList<>();
                    String tempName = "thisisianameandhasernothinngmeafulofthisworldbutjusttotestandtesttest";

                    for(int i=0;i<10;i++){
                        if (newList.size() + currentSize >= COUNT) {
                            break;
                        }
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
                        if( i % 5 == 0)
                            bean.isEditable = false;
                        else
                            bean.isEditable = true;
                        newList.add(bean);
                    }

                    activity.addItems(newList);
                    RecyclerViewStateUtils.setFooterViewState(activity.recyclerView, LoadingFooter.State.Normal);
                    break;
                case -2:
                    activity.notifyDataSetChanged();
                    break;
                case -3:
                    RecyclerViewStateUtils.setFooterViewState(activity, activity.recyclerView, REQUEST_COUNT, LoadingFooter.State.NetWorkError, activity.mFooterClick);
                    break;
            }
        }
    }

    private void notifyDataSetChanged(){
        headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<StoryBean> list){
        mData.addAll(list);
        currentCount += list.size();
       LogUtil.e("adapterCount;" + recyleAdapter.getItemCount() + "  currentCount:" + currentCount);
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(RefreshRecyclerActivity.this, recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
        }
    };

    private OnStartDragListener getOnStartDragListener(){
        mOnStartDragListener = new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        };
        return mOnStartDragListener;
    }

    private ItemTouchHelper.Callback getCallBack(ItemTouchHelperAdapter adapter){
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        return callback;
    }

    private OnListItemClickListener<StoryBean> getOnListItemClickListener(){
        mOnListItemClickListener = new OnListItemClickListener<StoryBean>() {
            @Override
            public void onListItemClickListener(View view, int position, StoryBean bean) {
                Toast.makeText(mContext,"position:" + position + "  bean:" + bean,Toast.LENGTH_SHORT).show();
            }
        };
        return mOnListItemClickListener;
    }

    //自定义的itemDecoration
    private RecyclerView.ItemDecoration getItemDecoration(final int _orientation,final int _size,final int _color){
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            /**
             * 水平方向的
             */
            public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

            /**
             * 垂直方向的
             */
            public static final int VERTICAL = LinearLayoutManager.VERTICAL;

            // 画笔
            private Paint paint = new Paint();

            // 布局方向
            private int orientation = _orientation;
            // 分割线颜色
            private int color = _color;
            // 分割线尺寸
            private int size = _size;

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);

                if (orientation == VERTICAL) {
                    drawHorizontal(c, parent);
                } else {
                    drawVertical(c, parent);
                }
            }

            /**
             * 设置分割线颜色
             *
             * @param color 颜色
             */
            public void setColor(int color) {
                this.color = color;
                paint.setColor(color);
            }

            /**
             * 设置分割线尺寸
             *
             * @param size 尺寸
             */
            public void setSize(int size) {
                this.size = size;
            }

            // 绘制垂直方向的分割线
            protected void drawVertical(Canvas c, RecyclerView parent) {
                final int top = parent.getPaddingTop();
                final int bottom = parent.getHeight() - parent.getPaddingBottom();

                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int left = child.getRight() + params.rightMargin;
                    final int right = left + size;

                    c.drawRect(left, top, right, bottom, paint);
                }
            }

            // 绘制水平方向的分割线
            protected void drawHorizontal(Canvas c, RecyclerView parent) {
                final int left = parent.getPaddingLeft();
                final int right = parent.getWidth() - parent.getPaddingRight();

                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int top = child.getBottom() + params.bottomMargin;
                    final int bottom = top + size;

                    c.drawRect(left, top, right, bottom, paint);
                }
            }
        };
        return itemDecoration;
    }
}

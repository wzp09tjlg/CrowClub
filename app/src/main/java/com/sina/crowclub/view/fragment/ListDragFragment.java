package com.sina.crowclub.view.fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.view.adapter.RecyleAdapter;
import com.sina.crowclub.view.base.BaseFragment;
import com.sina.crowclub.view.widget.helper.ItemTouchHelperAdapter;
import com.sina.crowclub.view.widget.helper.OnStartDragListener;
import com.sina.crowclub.view.widget.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wu on 2016/7/23.
 */
public class ListDragFragment extends BaseFragment {
    private static final String TAG = ListDragFragment.class.getSimpleName();

    /** View  */
    private RecyclerView recyclerView;

    /** Data */
    private List<StoryBean> mData;
    private RecyleAdapter recyleAdapter;

    private OnStartDragListener mOnStartDragListener;
    private ItemTouchHelper mItemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_series,null);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        recyclerView = $(view,R.id.recyclerview);

        initData();
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

        recyleAdapter = new RecyleAdapter(mData,getOnStartDragListener());

        recyclerView.setHasFixedSize(true); //改变recycleView 时，固定recycleView的数量大小
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);// 设置显示的方式，默认是垂直方向
        RecyclerView.ItemDecoration itemDecoration = getItemDecoration(LinearLayoutManager.VERTICAL,1,0xFFDDDDDD);
        recyclerView.addItemDecoration(itemDecoration); //设置分割线
        recyclerView.setAdapter(recyleAdapter);//设置adapter

        mItemTouchHelper = new ItemTouchHelper(getCallBack(recyleAdapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

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

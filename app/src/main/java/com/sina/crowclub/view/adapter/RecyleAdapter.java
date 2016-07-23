package com.sina.crowclub.view.adapter;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.utils.CommonHelper;
import com.sina.crowclub.view.widget.helper.ItemTouchHelperAdapter;
import com.sina.crowclub.view.widget.helper.ItemTouchHelperViewHolder;
import com.sina.crowclub.view.widget.helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by wu on 2016/7/23.
 */
public class RecyleAdapter extends RecyclerView.Adapter<RecyleAdapter.ViewHolder> implements
        ItemTouchHelperAdapter
{
    private static final String TAG = RecyleAdapter.class.getSimpleName();
    private List<StoryBean> mData;
    private OnStartDragListener mOnStartDragListener;

    public RecyleAdapter(List<StoryBean> data,OnStartDragListener onStartDragListener){
        mData = data;
        mOnStartDragListener = onStartDragListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_drag,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        StoryBean bean = mData.get(position);
        holder.textName.setText(bean.name);
        holder.imgDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                    mOnStartDragListener.onStartDrag(holder);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder
    {
        private TextView textName;
        private ImageView imgDrag;

        public ViewHolder(View view){
            super(view);
            textName = CommonHelper.$(view,R.id.text_series_name);
            imgDrag = CommonHelper.$(view,R.id.img_drag);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    /** 实现接口的方法 */
    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
}

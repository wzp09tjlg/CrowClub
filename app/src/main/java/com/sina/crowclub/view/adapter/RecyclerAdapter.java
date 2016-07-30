package com.sina.crowclub.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.view.widget.helper.ItemTouchHelperAdapter;
import com.sina.crowclub.view.widget.helper.ItemTouchHelperViewHolder;
import com.sina.crowclub.view.widget.helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by wu on 2016/7/29.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
  implements ItemTouchHelperAdapter
{
    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    private List<StoryBean> mData;
    private OnStartDragListener onStartDragListener;

    public RecyclerAdapter(List<StoryBean> data){
        mData = data;
        onStartDragListener = null;
    }

    public RecyclerAdapter(List<StoryBean> data,OnStartDragListener onStartDragListener){
        mData = data;
        this.onStartDragListener = onStartDragListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_drag,null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       StoryBean bean = mData.get(position);
       holder.name.setText(bean.name);
        if(onStartDragListener != null){
            holder.imgDrag.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    onStartDragListener.onStartDrag(holder);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder
    {
        private TextView name;
        private ImageView imgDrag;

        public ViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.text_series_name);
            imgDrag = (ImageView)view.findViewById(R.id.img_drag);
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
}

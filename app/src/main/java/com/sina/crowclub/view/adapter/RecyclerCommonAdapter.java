package com.sina.crowclub.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wu on 2016/7/28.
 */
public abstract class RecyclerCommonAdapter<T> extends RecyclerView.Adapter<RecyclerCommonViewHolder> {
    private int mLayoutId;
    private List<T> mData;

    public RecyclerCommonAdapter(int layoutId){
        mLayoutId = layoutId;
    }

    @Override
    public RecyclerCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerCommonViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutId,null));
    }

    @Override
    public void onBindViewHolder(RecyclerCommonViewHolder holder, int position) {
        convertView(holder,position,mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public abstract void convertView(RecyclerCommonViewHolder holder,int position,T t);
}

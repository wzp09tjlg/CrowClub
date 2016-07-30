package com.sina.crowclub.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wu on 2016/7/28.
 */
public class RecyclerCommonViewHolder extends RecyclerView.ViewHolder {
    private RecyclerCommonViewConvert commonViewConvert;

    public RecyclerCommonViewHolder(View view){
        super(view);
        if(commonViewConvert != null)
          commonViewConvert.convert(view);
    }

    public void setCommonViewConvert(RecyclerCommonViewConvert commonViewConvert) {
        this.commonViewConvert = commonViewConvert;
    }

    interface RecyclerCommonViewConvert{
        void convert(View view);
    }
}

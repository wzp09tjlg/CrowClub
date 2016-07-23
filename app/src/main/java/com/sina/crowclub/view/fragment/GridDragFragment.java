package com.sina.crowclub.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.crowclub.R;
import com.sina.crowclub.view.base.BaseFragment;

/**
 * Created by wu on 2016/7/23.
 */
public class GridDragFragment extends BaseFragment {
    private static final String TAG = GridDragFragment.class.getSimpleName();

    /** View */
    private RecyclerView recyclerView;

    /** Data */

    /**********************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_series,null);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        recyclerView = $(view,R.id.recyclerview);
    }
}

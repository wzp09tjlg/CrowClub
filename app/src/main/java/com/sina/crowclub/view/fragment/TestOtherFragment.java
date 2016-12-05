package com.sina.crowclub.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.base.BaseFragment;

/**
 * Created by wu on 2016/12/1.
 */
public class TestOtherFragment extends BaseFragment {
    /** View */

    /** Data */
    /***********************************************/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("TestOtherFragment : onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("TestOtherFragment : onCreateView");
        View view  = inflater.inflate(R.layout.fragment_other,null);
        initViews(view);
        return view;
    }

    private void initViews(View view){
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e("TestOtherFragment : onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("TestOtherFragment : onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e("TestOtherFragment : onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e("TestOtherFragment : onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("TestOtherFragment : onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e("TestOtherFragment : onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e("TestOtherFragment : onDetach");
    }
}

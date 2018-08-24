package com.bt.andy.sanlianASxcx.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.IconAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 16:41
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class Home_F extends Fragment {
    private View         mRootView;
    private TextView     mTv_title;
    private RecyclerView mRecy_icon;
    private IconAdapter  mIconAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.home_f, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        mTv_title = mRootView.findViewById(R.id.tv_title);
        mRecy_icon = mRootView.findViewById(R.id.recy_icon);
    }

    private void initData() {
        mTv_title.setText("主页");
        GridLayoutManager mGridManager = new GridLayoutManager(getContext(), 2);
        mRecy_icon.setLayoutManager(mGridManager);
        List<String> mData = new ArrayList<>();
        mData.add("配送");
        mData.add("安装");
        mData.add("维修");
        mIconAdapter = new IconAdapter(getContext(), mData);
        mRecy_icon.setAdapter(mIconAdapter);
    }

}

package com.bt.andy.sanlianASxcx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvServiceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/27 10:09
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ServiceFragment extends Fragment {
    private View     mRootView;
    private ListView lv_tour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_distri, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        lv_tour = (ListView) mRootView.findViewById(R.id.lv_tour);
    }

    private void initData() {
        List<String> mData = new ArrayList();
        mData.add("1");
        mData.add("2");
        mData.add("3");
        mData.add("4");
        mData.add("5");
        LvServiceAdapter tourPlanAdapter = new LvServiceAdapter(getContext(), mData);
        lv_tour.setAdapter(tourPlanAdapter);
    }
}

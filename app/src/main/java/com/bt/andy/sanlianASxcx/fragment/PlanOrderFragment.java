package com.bt.andy.sanlianASxcx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvAcceptAdapter;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/29 9:18
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class PlanOrderFragment extends Fragment {
    private View               mRootView;
    private SmartRefreshLayout smt_refresh;
    private ListView           lv_tour;
    private LvAcceptAdapter    tourPlanAdapter;
    private List               mData;
    private String             mKind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order_list, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        smt_refresh = (SmartRefreshLayout) mRootView.findViewById(R.id.smt_refresh);
        lv_tour = (ListView) mRootView.findViewById(R.id.lv_tour);
        smt_refresh.setEnableRefresh(false);
        smt_refresh.setEnableLoadMore(false);
        mKind = getActivity().getIntent().getStringExtra("kind");
    }

    private void initData() {
        if (null == mData)
            mData = new ArrayList();
        tourPlanAdapter = new LvAcceptAdapter(getContext(), mData, mKind, "plan");
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO:弹出详情
                ToastUtils.showToast(getContext(), "弹出详情");
                showMoreInfo();
            }
        });
    }

    private void showMoreInfo() {
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        View v = View.inflate(getContext(), R.layout.alert_ps_jd, null);
        TextView tv_address = v.findViewById(R.id.tv_address);
        TextView tv_date = v.findViewById(R.id.tv_date);
        TextView tv_jdate = v.findViewById(R.id.tv_jdate);
        TextView tv_azdate = v.findViewById(R.id.tv_azdate);
        TextView tv_task = v.findViewById(R.id.tv_task);
        TextView tv_person = v.findViewById(R.id.tv_person);
        TextView tv_close = v.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setTitle("详细信息");
        dialog.setView(v);
        dialog.show();
    }

    public void setDataList(List data) {
        if (null == mData) {
            mData = new ArrayList();
        } else {
            mData.clear();
        }
        mData.addAll(data);
        if (null == tourPlanAdapter) {
            tourPlanAdapter = new LvAcceptAdapter(getContext(), mData, mKind, "rob");
        } else {
            tourPlanAdapter.notifyDataSetChanged();
        }
    }
}

package com.bt.andy.sanlianASxcx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvAcceptAdapter;
import com.bt.andy.sanlianASxcx.messegeInfo.InstAndRepInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.util.GetOrderDetailInfoUtil;
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
    private ImageView          img_no_msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order_list, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        smt_refresh = (SmartRefreshLayout) mRootView.findViewById(R.id.smt_refresh);
        img_no_msg = mRootView.findViewById(R.id.img_no_msg);
        lv_tour = (ListView) mRootView.findViewById(R.id.lv_tour);
        smt_refresh.setEnableRefresh(false);
        smt_refresh.setEnableLoadMore(false);
        mKind = getActivity().getIntent().getStringExtra("kind");
    }

    private void initData() {
        img_no_msg.setVisibility(View.VISIBLE);
        if (null == mData)
            mData = new ArrayList();
        tourPlanAdapter = new LvAcceptAdapter(getContext(), mData, mKind, "plan");
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String orderID = "";
                if ("0".equals(mKind)) {
                    PeiSInfo.ApplyBean bean = (PeiSInfo.ApplyBean) mData.get(i);
                    orderID = bean.getId();
                } else {
                    InstAndRepInfo.ApplyBean bean = (InstAndRepInfo.ApplyBean) mData.get(i);
                    orderID = bean.getId();
                }
                new GetOrderDetailInfoUtil(getContext(), mKind, false).showMoreInfo(orderID);
            }
        });
    }

    public void setDataList(List data) {
        if (null == mData) {
            mData = new ArrayList();
        } else {
            mData.clear();
        }
        if (null != img_no_msg) {
            if (data.size() > 0) {
                img_no_msg.setVisibility(View.GONE);
            } else {
                img_no_msg.setVisibility(View.VISIBLE);
            }
        }
        mData.addAll(data);
        if (null == tourPlanAdapter) {
            tourPlanAdapter = new LvAcceptAdapter(getContext(), mData, mKind, "rob");
        } else {
            tourPlanAdapter.notifyDataSetChanged();
        }
    }
}

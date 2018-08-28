package com.bt.andy.sanlianASxcx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvOrderAdapter;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/27 9:55
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderFragment extends Fragment {
    private View           mRootView;
    private ListView       lv_tour;
    private List           mData;
    private LvOrderAdapter tourPlanAdapter;

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
        String kind = getActivity().getIntent().getStringExtra("kind");
        mData = new ArrayList();
        //        mData.add("1");
        //        mData.add("2");
        //        mData.add("3");
        //        mData.add("4");
        //        mData.add("5");
        tourPlanAdapter = new LvOrderAdapter(getContext(), mData, kind);
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO:
                ToastUtils.showToast(getContext(), "弹出详情");
            }
        });
        if ("0".equals(kind)) {
            //获取提货单
            getTihuo();
        } else if ("1".equals(kind)) {
            //获取安装预约
            getAzYuYue();
        } else if ("2".equals(kind)) {
            //获取维修预约
            getWxYuYue();
        }

    }

    private void getWxYuYue() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();


    }

    private void getAzYuYue() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();

    }

    private void getTihuo() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();
        String peiSUrl = NetConfig.SELECTAPPLY1;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        params.put("psstatus", "1");
        HttpOkhUtils.getInstance().doGetWithParams(peiSUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(getContext(), code + "网络连接错误");
                    return;
                }
                Gson gson = new Gson();
                PeiSInfo peiSInfo = gson.fromJson(resbody, PeiSInfo.class);
                int result = peiSInfo.getResult();
                if (result == 1) {
                    List<PeiSInfo.ApplyBean> apply = peiSInfo.getApply();
                    for (PeiSInfo.ApplyBean bean : apply) {
                        mData.add(bean);
                    }
                    tourPlanAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(getContext(), "获取配送单失败");
                }
            }
        });
    }
}

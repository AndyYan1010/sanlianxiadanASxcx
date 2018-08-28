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

import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvAcceptAdapter;
import com.bt.andy.sanlianASxcx.messegeInfo.InstAndRepInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/18 10:13
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ReceFragment extends Fragment {
    private View            mRootView;
    private ListView        lv_tour;
    private List            mData;
    private LvAcceptAdapter tourPlanAdapter;

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
        tourPlanAdapter = new LvAcceptAdapter(getContext(), mData, kind);
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO:弹出详情
                ToastUtils.showToast(getContext(), "弹出详情");
                showMoreInfo();
            }
        });
        if ("0".equals(kind)) {
            //获取配送单
            getPeiSong();
        } else if ("1".equals(kind)) {
            //获取安装待接单
            getAzInfo();
        } else if ("2".equals(kind)) {
            //获取维修待接单
            getWxInfo();
        }

    }

    private void showMoreInfo() {
        View v = View.inflate(getContext(), R.layout.alert_ps_jd, null);
        TextView tv_address = v.findViewById(R.id.tv_address);
        TextView tv_date = v.findViewById(R.id.tv_date);
        TextView tv_jdate = v.findViewById(R.id.tv_jdate);
        TextView tv_azdate = v.findViewById(R.id.tv_azdate);
        TextView tv_task = v.findViewById(R.id.tv_task);
        TextView tv_person = v.findViewById(R.id.tv_person);
        TextView tv_close = v.findViewById(R.id.tv_close);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(v).setTitle("详细信息").show();
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.create().dismiss();
            }
        });
    }

    private void getWxInfo() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();

    }

    private void getAzInfo() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();
        String azUrl = NetConfig.SELECTAPPLY;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        HttpOkhUtils.getInstance().doGetWithParams(azUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                try {
                    JSONArray jsonArray = new JSONArray(resbody);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        InstAndRepInfo info = gson.fromJson(jsonArray.get(i).toString(), InstAndRepInfo.class);
                        mData.add(info);
                    }
                    tourPlanAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(), "数据解析失败");
                }
            }
        });
    }

    private void getPeiSong() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();
        String peiSUrl = NetConfig.SELECTAPPLY1;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        params.put("psstatus", "0");
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

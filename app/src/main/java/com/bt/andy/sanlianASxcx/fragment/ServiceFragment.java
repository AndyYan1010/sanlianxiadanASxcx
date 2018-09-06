package com.bt.andy.sanlianASxcx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvServiceAdapter;
import com.bt.andy.sanlianASxcx.messegeInfo.AnzYuyueInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.util.GetOrderDetailInfoUtil;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/27 10:09
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ServiceFragment extends Fragment {
    private View               mRootView;
    private SmartRefreshLayout smt_refresh;
    private ListView           lv_tour;
    private List               mData;
    private LvServiceAdapter   tourPlanAdapter;
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
        mKind = getActivity().getIntent().getStringExtra("kind");
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取服务内容
        getServiceCont();
    }

    private void initData() {
        mData = new ArrayList();
        tourPlanAdapter = new LvServiceAdapter(getContext(), mData, mKind);
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String orderID = "";
                if ("0".equals(mKind)) {
                    PeiSInfo.ApplyBean bean = (PeiSInfo.ApplyBean) mData.get(i);
                    orderID = bean.getId();
                } else {
                    AnzYuyueInfo info = (AnzYuyueInfo) mData.get(i);
                    orderID = info.getId();
                }
                new GetOrderDetailInfoUtil(getContext(), mKind).showMoreInfo(orderID);
            }
        });
        //获取服务内容
        //        getServiceCont();
        smt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //获取服务内容
                getServiceCont();
            }
        });
    }

    private void getServiceCont() {
        if ("0".equals(mKind)) {
            //获取配送服务
            getPeisServ();
        } else if ("1".equals(mKind)) {
            //获取安装服务
            getAzServ();
        } else if ("2".equals(mKind)) {
            //获取维修服务
            getWxServ();
        }
    }

    private void getWxServ() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();
        String smfwwxUrl = NetConfig.SMFWWX;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        HttpOkhUtils.getInstance().doGetWithParams(smfwwxUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                smt_refresh.finishRefresh();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                smt_refresh.finishRefresh();
                if (code != 200) {
                    ToastUtils.showToast(getContext(), code + "网络连接错误");
                    return;
                }
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(resbody);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        AnzYuyueInfo anzYYInfo = gson.fromJson(jsonArray.get(i).toString(), AnzYuyueInfo.class);
                        mData.add(anzYYInfo);
                    }
                    tourPlanAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(), "数据获取失败");
                }
            }
        });
    }

    private void getAzServ() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();
        String smfwUrl = NetConfig.SMFW;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        HttpOkhUtils.getInstance().doGetWithParams(smfwUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                smt_refresh.finishRefresh();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                smt_refresh.finishRefresh();
                if (code != 200) {
                    ToastUtils.showToast(getContext(), code + "网络连接错误");
                    return;
                }
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(resbody);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        AnzYuyueInfo anzYYInfo = gson.fromJson(jsonArray.get(i).toString(), AnzYuyueInfo.class);
                        mData.add(anzYYInfo);
                    }
                    tourPlanAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(), "数据获取失败");
                }
            }
        });
    }

    private void getPeisServ() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();
        String peiSUrl = NetConfig.SELECTAPPLY1;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        params.put("psstatus", "2");
        HttpOkhUtils.getInstance().doGetWithParams(peiSUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                smt_refresh.finishRefresh();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                smt_refresh.finishRefresh();
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

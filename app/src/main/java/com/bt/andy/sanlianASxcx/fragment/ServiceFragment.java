package com.bt.andy.sanlianASxcx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvServiceAdapter;
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
    private View                         mRootView;
    private SmartRefreshLayout           smt_refresh;
    private ListView                     lv_tour;
    private List<PeiSInfo.ApplylistBean> mData;
    private LvServiceAdapter             tourPlanAdapter;
    private ImageView                    img_no_msg;
    private String                       mKind;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取服务内容
        //        getServiceCont();
    }

    private void initData() {
        img_no_msg.setVisibility(View.VISIBLE);
        mData = new ArrayList();
        tourPlanAdapter = new LvServiceAdapter(getContext(), mData);
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PeiSInfo.ApplylistBean applylistBean = mData.get(i);
                String ordertype = applylistBean.getOrdertype();
                if (null == ordertype || "".equals(ordertype)) {
                    ToastUtils.showToast(getContext(), "该订单未填写类型，不可查询");
                } else {
                    if (ordertype.contains("配送")) {
                        mKind = "0";
                    } else if (ordertype.contains("安装")) {
                        mKind = "1";
                    } else if (ordertype.contains("维修")) {
                        mKind = "2";
                    }
                    new GetOrderDetailInfoUtil(getContext(), mKind, true).showMoreInfo(applylistBean.getId());
                }
            }
        });
        smt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //获取服务内容
                getServiceCont();
            }
        });
        smt_refresh.setEnableLoadMore(false);
        //获取服务内容
        getServiceCont();
    }

    private void getServiceCont() {
        img_no_msg.setVisibility(View.VISIBLE);
        getPeisServ();
    }

    private void getPeisServ() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        if (null == mData) {
            mData = new ArrayList();
        } else {
            mData.clear();
        }
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
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                PeiSInfo peiSInfo = gson.fromJson(resbody, PeiSInfo.class);
                int result = peiSInfo.getResult();
                if (result == 1) {
                    List<PeiSInfo.ApplylistBean> applylist = peiSInfo.getApplylist();
                    if (applylist.size() > 0) {
                        img_no_msg.setVisibility(View.GONE);
                    }
                    for (PeiSInfo.ApplylistBean bean : applylist) {
                        mData.add(bean);
                    }
                    tourPlanAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(getContext(), "未获取到订单信息");
                }
            }
        });
    }
    public void manualRefresh() {
        //获取待接单
        getServiceCont();
    }
}

package com.bt.andy.sanlianASxcx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.bt.andy.sanlianASxcx.adapter.LvComplAdapter;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
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

public class ComplFragment extends Fragment {
    private View               mRootView;
    private SmartRefreshLayout smt_refresh;
    private ListView           lv_tour;
    private List               mData;
    private LvComplAdapter     tourPlanAdapter;
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
        //获取完成单
        getComplOrder();
    }

    private void initData() {
        mData = new ArrayList();
        tourPlanAdapter = new LvComplAdapter(getContext(), mData);
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO:
                showMoreInfo();
            }
        });
        smt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //获取完成单
                getComplOrder();
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

    private void getComplOrder() {
        if ("0".equals(mKind)) {
            //获取配送完成单
            getPeisComp();
        } else if ("1".equals(mKind)) {
            //获取安装完成单
            getAzComp();
        } else if ("2".equals(mKind)) {
            //获取维修完成单
            getWxComp();
        }
    }

    private void getWxComp() {

    }

    private void getAzComp() {

    }

    private void getPeisComp() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        mData.clear();
        String peiSUrl = NetConfig.SELECTAPPLY1;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        params.put("psstatus", "3");
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

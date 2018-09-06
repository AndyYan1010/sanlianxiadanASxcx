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

import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvAcceptAdapter;
import com.bt.andy.sanlianASxcx.messegeInfo.DetailOrderInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.InstAndRepInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.util.GetOrderDetailInfoUtil;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/29 9:15
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RobOrderFragment extends Fragment {
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
        tourPlanAdapter = new LvAcceptAdapter(getContext(), mData, mKind, "rob");
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //搜索详情，弹出详情框
                String orderID = "";
                if ("0".equals(mKind)) {
                    PeiSInfo.OrderazlistBean bean = (PeiSInfo.OrderazlistBean) mData.get(i);
                    orderID = bean.getId();
                } else {
                    InstAndRepInfo.OrderazlistBean bean = (InstAndRepInfo.OrderazlistBean) mData.get(i);
                    orderID = bean.getId();
                }
                new GetOrderDetailInfoUtil(getContext(), mKind).showMoreInfo(orderID);
                //获取订单详情
                //getMoreOrderInfo(orderID);
            }
        });
    }

    //搜索订单详情
    private void getMoreOrderInfo(String orderID) {
        ProgressDialogUtil.startShow(getContext(), "正在搜索");
        String detailUrl = NetConfig.DETAIL;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        HttpOkhUtils.getInstance().doGetWithParams(detailUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                    DetailOrderInfo detailInfo = gson.fromJson(jsonArray.get(0).toString(), DetailOrderInfo.class);
                    //展示订单详情
                    showMoreInfo(detailInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(), "获取订单详情失败");
                }
            }
        });
    }

    private void showMoreInfo(DetailOrderInfo detailInfo) {
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.alert_ps_jd, null);
        TextView tv_pm = v.findViewById(R.id.tv_pm);
        TextView tv_num = v.findViewById(R.id.tv_num);
        TextView tv_address = v.findViewById(R.id.tv_address);
        TextView tv_date = v.findViewById(R.id.tv_date);
        TextView tv_jdate = v.findViewById(R.id.tv_jdate);
        TextView tv_azdate = v.findViewById(R.id.tv_azdate);
        TextView tv_task = v.findViewById(R.id.tv_task);
        TextView tv_person = v.findViewById(R.id.tv_person);
        TextView tv_close = v.findViewById(R.id.tv_close);
        tv_pm.setText(detailInfo.getPinming());
        tv_num.setText(detailInfo.getQty());
        tv_address.setText(detailInfo.getFaddress());
        tv_date.setText(detailInfo.getFgodate());
        tv_jdate.setText(detailInfo.getFdate());
        tv_azdate.setText(detailInfo.getAzfdate());
        tv_task.setText(detailInfo.getNotes());
        if ("0".equals(mKind)) {
            tv_person.setText(detailInfo.getPsshifu());
        } else {
            tv_person.setText(detailInfo.getFshifu());
        }
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

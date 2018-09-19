package com.bt.andy.sanlianASxcx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvOrderAdapter;
import com.bt.andy.sanlianASxcx.messegeInfo.LoginInfo;
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
import com.uuzuche.lib_zxing.activity.CodeUtils;

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
    private View                         mRootView;
    private SmartRefreshLayout           smt_refresh;
    private ListView                     lv_tour;
    private List<PeiSInfo.ApplylistBean> mData;
    private LvOrderAdapter               tourPlanAdapter;
    private int REQUEST_CODE = 1002;//接收扫描结果
    private ImageView img_no_msg;
    private String mKind = "";

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
        //获取订单信息
        //getOrderInfo();
    }

    private void initData() {
        img_no_msg.setVisibility(View.VISIBLE);
        mData = new ArrayList();
        tourPlanAdapter = new LvOrderAdapter(getContext(), mData);
        lv_tour.setAdapter(tourPlanAdapter);
        lv_tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PeiSInfo.ApplylistBean applylistBean = mData.get(i);
                String ordertype = applylistBean.getOrdertype();
                if (null != ordertype && "".equals(ordertype)) {
                    if (ordertype.contains("配送")) {
                        mKind = "0";
                    } else if (ordertype.contains("安装")) {
                        mKind = "1";
                    } else if (ordertype.contains("维修")) {
                        mKind = "2";
                    }
                    new GetOrderDetailInfoUtil(getContext(), mKind, false).showMoreInfo(applylistBean.getId());
                } else {
                    ToastUtils.showToast(getContext(), "该订单未填写类型，不可查询");
                }
            }
        });
        smt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //获取订单信息
                getOrderInfo();
            }
        });
        //获取订单信息
        getOrderInfo();
    }

    //获取订单信息
    private void getOrderInfo() {
        img_no_msg.setVisibility(View.VISIBLE);
        getOrderList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** 处理二维码扫描结果*/
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //获取扫描信息，填写到对应et
                    if (requestCode == REQUEST_CODE) {
                        int which = tourPlanAdapter.getWhich();
                        PeiSInfo.ApplylistBean applylistBean = mData.get(which);
                        //提交二维码信息
                        sendQcode(applylistBean.getId(), result);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void sendQcode(String orderID, String Qcode) {
        ProgressDialogUtil.startShow(getContext(), "正在提交二维码...");
        String slewmUrl = NetConfig.SELECTEWM;
        RequestParamsFM params = new RequestParamsFM();
        params.put("QCode", Qcode);
        params.put("id", orderID);
        HttpOkhUtils.getInstance().doGetWithParams(slewmUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络连接错误" + code);
                    return;
                }
                Gson gson = new Gson();
                LoginInfo loginInfo = gson.fromJson(resbody, LoginInfo.class);
                int result = loginInfo.getResult();
                if (result == 1) {
                    ToastUtils.showToast(getContext(), "提交成功");
                } else {
                    ToastUtils.showToast(getContext(), "提交失败" + result);
                }
            }
        });
    }

    private void getOrderList() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        if (null == mData) {
            mData = new ArrayList();
        } else {
            mData.clear();
        }
        String peiSUrl = NetConfig.DAIYUYUE;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
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
                    mData.addAll(applylist);
                    tourPlanAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(getContext(), "未获取订单信息");
                }
            }
        });
    }
}

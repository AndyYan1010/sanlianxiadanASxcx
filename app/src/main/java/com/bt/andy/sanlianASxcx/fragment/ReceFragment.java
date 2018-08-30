package com.bt.andy.sanlianASxcx.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.MyPagerAdapter;
import com.bt.andy.sanlianASxcx.messegeInfo.InstAndRepInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.bt.andy.sanlianASxcx.viewmodle.MyFixedViewpager;
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
 * @创建时间 2018/8/18 10:13
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ReceFragment extends Fragment {
    private View               mRootView;
    private SmartRefreshLayout smt_refresh;
    private String[] conts = {"抢单", "排单"};
    private TabLayout         mTablayout;//导航标签
    private MyFixedViewpager  mView_pager;//自我viewpager可实现禁止滑动
    private RobOrderFragment  robFragment;
    private PlanOrderFragment planFragment;
    private String            mKind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_distri, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        smt_refresh = (SmartRefreshLayout) mRootView.findViewById(R.id.smt_refresh);
        mTablayout = mRootView.findViewById(R.id.tablayout);
        mView_pager = mRootView.findViewById(R.id.view_pager);
        mKind = getActivity().getIntent().getStringExtra("kind");
        smt_refresh.setEnableLoadMore(false);
    }

    private void initData() {
        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 装填
        //抢单界面
        robFragment = new RobOrderFragment();
        fragments.add(robFragment);
        //排界面
        planFragment = new PlanOrderFragment();
        fragments.add(planFragment);
        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        mView_pager.setAdapter(myPagerAdapter);
//        mView_pager.setOffscreenPageLimit(4);
        //设置viewpager不可滑动
        //mView_pager_space.setCanScroll(false);
        //tablayout关联tablayout和viewpager实现联动
        mTablayout.setupWithViewPager(mView_pager);
        for (int i = 0; i < conts.length; i++) {
            mTablayout.getTabAt(i).setText(conts[i]);
        }
        //获取待接单
        getPendOrder();
        smt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //获取待接单
                getPendOrder();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取待接单
        getPendOrder();
    }

    //获取待接单
    private void getPendOrder() {
        if ("0".equals(mKind)) {
            //获取配送单
            getPeiSong();
        } else if ("1".equals(mKind)) {
            //获取安装待接单
            getAzInfo();
        } else if ("2".equals(mKind)) {
            //获取维修待接单
            getWxInfo();
        }
    }

    private void getWxInfo() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        String wxUrl = NetConfig.SELECTAPPLYWX;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        HttpOkhUtils.getInstance().doGetWithParams(wxUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                InstAndRepInfo instInfo = gson.fromJson(resbody, InstAndRepInfo.class);
                int result = instInfo.getResult();
                if (result == 1) {
                    List<InstAndRepInfo.OrderazlistBean> orderazlist = instInfo.getOrderazlist();
                    robFragment.setDataList(orderazlist);
                    List<InstAndRepInfo.ApplyBean> apply = instInfo.getApply();
                    planFragment.setDataList(apply);
                } else {
                    ToastUtils.showToast(getContext(), "获取安装单失败");
                }
            }
        });
    }

    private void getAzInfo() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        String azUrl = NetConfig.SELECTAPPLY;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        HttpOkhUtils.getInstance().doGetWithParams(azUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                InstAndRepInfo instInfo = gson.fromJson(resbody, InstAndRepInfo.class);
                int result = instInfo.getResult();
                if (result == 1) {
                    List<InstAndRepInfo.OrderazlistBean> orderazlist = instInfo.getOrderazlist();
                    robFragment.setDataList(orderazlist);
                    List<InstAndRepInfo.ApplyBean> apply = instInfo.getApply();
                    planFragment.setDataList(apply);
                } else {
                    ToastUtils.showToast(getContext(), "获取安装单失败");
                }
            }
        });
    }

    private void getPeiSong() {
        ProgressDialogUtil.startShow(getContext(), "正在查询，请稍后...");
        String peiSUrl = NetConfig.SELECTAPPLY1;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", MyApplication.userID);
        params.put("psstatus", "0");
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
                    List<PeiSInfo.OrderazlistBean> orderazlist = peiSInfo.getOrderazlist();//抢单
                    robFragment.setDataList(orderazlist);
                    List<PeiSInfo.ApplyBean> apply = peiSInfo.getApply();//排单
                    planFragment.setDataList(apply);
                } else {
                    ToastUtils.showToast(getContext(), "获取配送单失败");
                }
            }
        });
    }
}

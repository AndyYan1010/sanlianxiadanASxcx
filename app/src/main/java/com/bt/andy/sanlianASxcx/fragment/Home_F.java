package com.bt.andy.sanlianASxcx.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.MyPagerAdapter;
import com.bt.andy.sanlianASxcx.viewmodle.MyFixedViewpager;

import java.util.ArrayList;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 16:41
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class Home_F extends Fragment {
    private View             mRootView;
    private TextView         mTv_title;
    private TabLayout        mTablayout;//导航标签
    private MyFixedViewpager mView_pager;//自我viewpager可实现禁止滑动
    private String[] conts = {"待接单", "待预约", "上门服务", "服务完成"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.home_f, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        mTv_title = mRootView.findViewById(R.id.tv_title);
        mTablayout = mRootView.findViewById(R.id.tablayout);
        mView_pager = mRootView.findViewById(R.id.view_pager);
    }

    private void initData() {
        mTv_title.setText("主页");
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 装填
        //待接单界面
        final ReceFragment tourPlanFragment = new ReceFragment();
        fragments.add(tourPlanFragment);
        //待预约界面
        final OrderFragment orderFragment = new OrderFragment();
        fragments.add(orderFragment);
        //上门服务界面
        final ServiceFragment serviceFragment = new ServiceFragment();
        fragments.add(serviceFragment);
        //服务完成界面
        final ComplFragment complFragment = new ComplFragment();
        fragments.add(complFragment);

        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        mView_pager.setAdapter(myPagerAdapter);
        // mView_pager.setOffscreenPageLimit(4);
        //设置viewpager不可滑动
        //mView_pager_space.setCanScroll(false);
        //tablayout关联tablayout和viewpager实现联动
        mTablayout.setupWithViewPager(mView_pager);
        for (int i = 0; i < conts.length; i++) {
            mTablayout.getTabAt(i).setText(conts[i]);
        }
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String title = String.valueOf(tab.getText());
                if ("待接单".equals(title)) {
                    if (null != tourPlanFragment && tourPlanFragment.isVisible()) {
                        tourPlanFragment.manualRefresh();
                    }
                } else if ("待预约".equals(title)) {
                    if (null != orderFragment && orderFragment.isVisible()) {
                        orderFragment.manualRefresh();
                    }
                } else if ("上门服务".equals(title)) {
                    if (null != serviceFragment && serviceFragment.isVisible()) {
                        serviceFragment.manualRefresh();
                    }
                } else if ("服务完成".equals(title)) {
                    if (null != complFragment && complFragment.isVisible()) {
                        complFragment.manualRefresh();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                String title = String.valueOf(tab.getText());
                if ("待接单".equals(title)) {
                    if (null != tourPlanFragment && tourPlanFragment.isVisible()) {
                        tourPlanFragment.manualRefresh();
                    }
                } else if ("待预约".equals(title)) {
                    if (null != orderFragment && orderFragment.isVisible()) {
                        orderFragment.manualRefresh();
                    }
                } else if ("上门服务".equals(title)) {
                    if (null != serviceFragment && serviceFragment.isVisible()) {
                        serviceFragment.manualRefresh();
                    }
                } else if ("服务完成".equals(title)) {
                    if (null != complFragment && complFragment.isVisible()) {
                        complFragment.manualRefresh();
                    }
                }
            }
        });
    }
}

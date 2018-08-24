package com.bt.andy.sanlianASxcx.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.BaseActivity;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.MyPagerAdapter;
import com.bt.andy.sanlianASxcx.fragment.TourPlanFragment;
import com.bt.andy.sanlianASxcx.viewmodle.MyFixedViewpager;

import java.util.ArrayList;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/24 13:38
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class DistriActivity extends BaseActivity {
    private TextView         mTv_title;
    private TabLayout        mTablayout;//导航标签
    private MyFixedViewpager mView_pager;//自我viewpager可实现禁止滑动
    private String[] conts = {"待接单", "待预约", "上门服务", "服务完成"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distri);
        initView();
        initData();
    }

    private void initView() {
        mTv_title = findViewById(R.id.tv_title);
        mTablayout = findViewById(R.id.tablayout);
        mView_pager = findViewById(R.id.view_pager);
    }

    private void initData() {
        mTv_title.setText("配送");
        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 装填
        //待接单界面
        TourPlanFragment tourPlanFragment = new TourPlanFragment();
        fragments.add(tourPlanFragment);
        //待预约界面
        TourPlanFragment ckOrderInfoFragment = new TourPlanFragment();
        fragments.add(ckOrderInfoFragment);
        //上门服务界面
        TourPlanFragment acceptOrderFragment = new TourPlanFragment();
        fragments.add(acceptOrderFragment);
        //服务完成界面
        TourPlanFragment accepterHistoryFragment = new TourPlanFragment();
        fragments.add(accepterHistoryFragment);

        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        mView_pager.setAdapter(myPagerAdapter);
        //设置viewpager不可滑动
        //mView_pager_space.setCanScroll(false);
        //tablayout关联tablayout和viewpager实现联动
        mTablayout.setupWithViewPager(mView_pager);
        for (int i = 0; i < conts.length; i++) {
            mTablayout.getTabAt(i).setText(conts[i]);
        }
    }
}

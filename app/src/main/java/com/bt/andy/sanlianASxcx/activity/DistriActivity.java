package com.bt.andy.sanlianASxcx.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.BaseActivity;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.MyPagerAdapter;
import com.bt.andy.sanlianASxcx.fragment.ComplFragment;
import com.bt.andy.sanlianASxcx.fragment.OrderFragment;
import com.bt.andy.sanlianASxcx.fragment.ReceFragment;
import com.bt.andy.sanlianASxcx.fragment.ServiceFragment;
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

public class DistriActivity extends BaseActivity implements View.OnClickListener {
    private ImageView        img_back;
    private TextView         mTv_title;
    private TabLayout        mTablayout;//导航标签
    private MyFixedViewpager mView_pager;//自我viewpager可实现禁止滑动
    private String[] conts = {"待接单", "待提货", "上门服务", "服务完成"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distri);
        initView();
        initData();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        mTv_title = findViewById(R.id.tv_title);
        mTablayout = findViewById(R.id.tablayout);
        mView_pager = findViewById(R.id.view_pager);
    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
        mTv_title.setText("配送");
        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 装填
        //待接单界面
        ReceFragment tourPlanFragment = new ReceFragment();
        fragments.add(tourPlanFragment);
        //待预约界面
        OrderFragment orderFragment = new OrderFragment();
        fragments.add(orderFragment);
        //上门服务界面
        ServiceFragment serviceFragment = new ServiceFragment();
        fragments.add(serviceFragment);
        //服务完成界面
        ComplFragment complFragment = new ComplFragment();
        fragments.add(complFragment);

        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        mView_pager.setAdapter(myPagerAdapter);
        mView_pager.setOffscreenPageLimit(4);
        //设置viewpager不可滑动
        //mView_pager_space.setCanScroll(false);
        //tablayout关联tablayout和viewpager实现联动
        mTablayout.setupWithViewPager(mView_pager);
        for (int i = 0; i < conts.length; i++) {
            mTablayout.getTabAt(i).setText(conts[i]);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}

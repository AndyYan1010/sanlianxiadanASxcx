package com.bt.andy.sanlianASxcx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.LvSearchOrderAdapter;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.bt.andy.sanlianASxcx.viewmodle.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/18 9:58
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class Search_F extends Fragment implements View.OnClickListener {
    private View     mRootView;
    private TextView mTv_title;
    private TextView tv_search;//查询
    private TextView tv_start_time, tv_end_time;
    private ListView lv_search;
    private List     mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        mTv_title = mRootView.findViewById(R.id.tv_title);
        tv_start_time = mRootView.findViewById(R.id.tv_start_time);
        tv_end_time = mRootView.findViewById(R.id.tv_end_time);
        tv_search = mRootView.findViewById(R.id.tv_search);
        lv_search = mRootView.findViewById(R.id.lv_search);
    }

    private void initData() {
        mTv_title.setText("查询");
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        getDate();

        mData = new ArrayList();
        LvSearchOrderAdapter orderAdapter = new LvSearchOrderAdapter(getContext(), mData);
        lv_search.setAdapter(orderAdapter);
        tv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                //获取当前日期
                String nowDate = getNowDate();
                //打开时间选择器
                CustomDatePicker dpk = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
                    @Override
                    public void handle(String time) { // 回调接口，获得选中的时间
                        tv_start_time.setText(time.substring(0, 10));
                    }
                }, "2010-01-01 00:00", nowDate); //"2090-12-31 00:00" 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                dpk.showSpecificTime(true); // 显示时和分
                dpk.setIsLoop(true); // 允许循环滚动
                dpk.show(nowDate);
                //                dpk1.show(tv_start_time.getText().toString());
                break;
            case R.id.tv_end_time:
                //获取当前日期
                String nowDate1 = getNowDate();
                //打开时间选择器
                CustomDatePicker dpk1 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
                    @Override
                    public void handle(String time) { // 回调接口，获得选中的时间
                        tv_end_time.setText(time.substring(0, 10));
                    }
                }, "2010-01-01 00:00", nowDate1);
                dpk1.showSpecificTime(true); // 显示时和分
                dpk1.setIsLoop(true); // 允许循环滚动
                dpk1.show(nowDate1);
                //                dpk.show(tv_end_time.getText().toString());
                break;
            case R.id.tv_search:
                String st_time = String.valueOf(tv_start_time.getText()).trim();
                String end_time = String.valueOf(tv_end_time.getText()).trim();
                if ("".equals(st_time) || "请选择开始日期".equals(st_time)) {
                    ToastUtils.showToast(getContext(), "请选择开始日期");
                    return;
                }
                if ("".equals(end_time) || "请选择结束日期".equals(end_time)) {
                    ToastUtils.showToast(getContext(), "请选择结束日期");
                    return;
                }
                break;
        }
    }

    //获取当前日期
    private void getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String data = simpleDateFormat.format(new Date());
        tv_start_time.setText(data);
        tv_end_time.setText(data);
    }

    private String getNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String data = simpleDateFormat.format(new Date());
        return data;
    }
}

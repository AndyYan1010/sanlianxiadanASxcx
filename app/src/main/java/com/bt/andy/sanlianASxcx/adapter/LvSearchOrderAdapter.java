package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.messegeInfo.SearchDataOrderInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/9/3 14:45
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvSearchOrderAdapter extends BaseAdapter {
    private List<SearchDataOrderInfo.OredrpaylistBean> mList;
    private Context                                    mContext;

    public LvSearchOrderAdapter(Context context, List<SearchDataOrderInfo.OredrpaylistBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewholder viewholder;
        if (null == view) {
            viewholder = new MyViewholder();
            view = View.inflate(mContext, R.layout.adpter_search_order, null);
            viewholder.rel_meal = view.findViewById(R.id.rel_meal);
            viewholder.tv_bzf = view.findViewById(R.id.tv_bzf);
            viewholder.tv_number = view.findViewById(R.id.tv_number);
            viewholder.tv_sum = view.findViewById(R.id.tv_sum);
            viewholder.tv_standard = view.findViewById(R.id.tv_standard);
            viewholder.tv_slf = view.findViewById(R.id.tv_slf);
            viewholder.tv_upstairs = view.findViewById(R.id.tv_upstairs);
            viewholder.tv_ct = view.findViewById(R.id.tv_ct);
            viewholder.tv_long_dis = view.findViewById(R.id.tv_long_dis);
            viewholder.tv_mprice = view.findViewById(R.id.tv_mprice);
            viewholder.tv_others = view.findViewById(R.id.tv_others);
            view.setTag(viewholder);
        } else {
            viewholder = (MyViewholder) view.getTag();
        }
        SearchDataOrderInfo.OredrpaylistBean oredrpaylistBean = mList.get(i);
        String pscost = oredrpaylistBean.getPscost();
        String azcost = oredrpaylistBean.getAzcost();
        String wxcost = oredrpaylistBean.getWxcost();
        viewholder.tv_number.setText(oredrpaylistBean.getFBillNo());
        viewholder.tv_sum.setText("" + oredrpaylistBean.getFAmount());
        if (!"".equals(pscost)) {//配送单
            viewholder.rel_meal.setVisibility(View.GONE);
            viewholder.tv_bzf.setText("标准运费");
            viewholder.tv_standard.setText(pscost);
            viewholder.tv_slf.setText("上楼费");
            viewholder.tv_upstairs.setText(oredrpaylistBean.getUpstairscost());
            viewholder.tv_ct.setText("长途");
            viewholder.tv_long_dis.setText(oredrpaylistBean.getDistancecost());
        } else if (!"".equals(azcost)) {//安装单
            viewholder.rel_meal.setVisibility(View.VISIBLE);
            viewholder.tv_bzf.setText("标准安装费");
            viewholder.tv_standard.setText(azcost);
            viewholder.tv_slf.setText("车费");
            viewholder.tv_upstairs.setText(oredrpaylistBean.getFarecost());
            viewholder.tv_ct.setText("垫付材料费");
            viewholder.tv_long_dis.setText(oredrpaylistBean.getMaterialcosts());
            viewholder.tv_mprice.setText(oredrpaylistBean.getMealscost());
        } else {//维修单
            viewholder.rel_meal.setVisibility(View.VISIBLE);
            viewholder.tv_bzf.setText("标准维修费");
            viewholder.tv_standard.setText(wxcost);
            viewholder.tv_slf.setText("车费");
            viewholder.tv_upstairs.setText(oredrpaylistBean.getFarecost());
            viewholder.tv_ct.setText("垫付材料费");
            viewholder.tv_long_dis.setText(oredrpaylistBean.getMaterialcosts());
            viewholder.tv_mprice.setText(oredrpaylistBean.getMealscost());
        }
        viewholder.tv_others.setText(oredrpaylistBean.getOthercosts());
        return view;
    }

    private class MyViewholder {
        RelativeLayout rel_meal;
        TextView       tv_bzf, tv_number, tv_sum, tv_slf, tv_standard, tv_upstairs, tv_ct, tv_long_dis, tv_mprice, tv_others;
    }
}

package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/27 10:07
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvComplAdapter extends BaseAdapter {
    private Context mContext;
    private List    mList;

    public LvComplAdapter(Context context, List list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        MyViewholder viewholder;
        if (null == view) {
            viewholder = new MyViewholder();
            view = View.inflate(mContext, R.layout.adpter_tour, null);
            viewholder.tv_num = view.findViewById(R.id.tv_num);
            viewholder.tv_address = view.findViewById(R.id.tv_address);
            viewholder.tv_cont = view.findViewById(R.id.tv_cont);
            viewholder.tv_contPhone = view.findViewById(R.id.tv_contPhone);
            viewholder.tv_warn = view.findViewById(R.id.tv_warn);
            viewholder.view_line = view.findViewById(R.id.view_line);
            viewholder.tv_accept = view.findViewById(R.id.tv_accept);
            viewholder.tv_call_phone = view.findViewById(R.id.tv_call_phone);
            viewholder.tv_compl = view.findViewById(R.id.tv_compl);
            view.setTag(viewholder);
        } else {
            viewholder = (MyViewholder) view.getTag();
        }
        viewholder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        viewholder.tv_accept.setVisibility(View.GONE);
        viewholder.tv_call_phone.setVisibility(View.GONE);
        viewholder.tv_compl.setVisibility(View.GONE);
        PeiSInfo.ApplyBean bean = (PeiSInfo.ApplyBean) mList.get(i);
        viewholder.tv_num.setText(bean.getForderno());
        viewholder.tv_address.setText(bean.getFaddress());
        viewholder.tv_cont.setText(bean.getFpeople());
        viewholder.tv_contPhone.setText(bean.getFtel());
        viewholder.tv_warn.setText(bean.getSpecial_note());
        return view;
    }

    private class MyViewholder {
        View     view_line;
        TextView tv_accept, tv_call_phone, tv_compl, tv_num, tv_address, tv_cont, tv_contPhone, tv_warn;
    }
}

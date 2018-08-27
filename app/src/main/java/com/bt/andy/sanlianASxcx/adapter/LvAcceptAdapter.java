package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/27 19:34
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvAcceptAdapter extends BaseAdapter {
    private Context      mContext;
    private List<String> mList;
    private String       mKind;

    public LvAcceptAdapter(Context context, List<String> list, String kind) {
        this.mContext = context;
        this.mList = list;
        this.mKind = kind;
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
            viewholder.view_line = view.findViewById(R.id.view_line);
            viewholder.tv_accept = view.findViewById(R.id.tv_accept);
            viewholder.tv_call_phone = view.findViewById(R.id.tv_call_phone);
            view.setTag(viewholder);
        } else {
            viewholder = (MyViewholder) view.getTag();
        }
        viewholder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.green_100));
        viewholder.tv_accept.setBackgroundResource(R.drawable.bg_round_green);
        viewholder.tv_call_phone.setBackgroundResource(R.drawable.bg_round_green);
        if ("0".equals(mKind)) {
            viewholder.tv_call_phone.setVisibility(View.VISIBLE);
        } else {
            viewholder.tv_call_phone.setVisibility(View.GONE);
        }
        return view;
    }

    private class MyViewholder {
        View     view_line;
        TextView tv_accept, tv_call_phone;
    }
}

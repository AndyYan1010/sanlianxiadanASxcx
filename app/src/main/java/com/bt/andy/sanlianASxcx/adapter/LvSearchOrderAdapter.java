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
 * @创建时间 2018/9/3 14:45
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvSearchOrderAdapter extends BaseAdapter {
    private List    mList;
    private Context mContext;

    public LvSearchOrderAdapter(Context context, List list) {
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
            viewholder.tv_number = view.findViewById(R.id.tv_number);
            view.setTag(viewholder);
        } else {
            viewholder = (MyViewholder) view.getTag();
        }

        return view;
    }

    private class MyViewholder {
        TextView tv_number, tv_sum, tv_standard, tv_upstairs, tv_long_dis, tv_others;
    }
}

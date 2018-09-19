package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    private Context                      mContext;
    private List<PeiSInfo.ApplylistBean> mList;

    public LvComplAdapter(Context context, List<PeiSInfo.ApplylistBean> list) {
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
            viewholder.img_kind = view.findViewById(R.id.img_kind);
            viewholder.img_type = view.findViewById(R.id.img_type);
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
        viewholder.img_type.setVisibility(View.GONE);

        PeiSInfo.ApplylistBean applylistBean = mList.get(i);
        viewholder.tv_num.setText(applylistBean.getForderno());
        viewholder.tv_address.setText(applylistBean.getFaddress());
        viewholder.tv_cont.setText(applylistBean.getFpeople());
        viewholder.tv_contPhone.setText(applylistBean.getFtel());
        viewholder.tv_warn.setText(applylistBean.getSpecial_note());

        String ordertype = applylistBean.getOrdertype();
        if (ordertype.contains("配送")) {
            viewholder.img_kind.setImageResource(R.drawable.icon_peisong);
        } else {
            if (ordertype.contains("安装")) {
                viewholder.img_kind.setImageResource(R.drawable.icon_anzhuang);
            } else {
                viewholder.img_kind.setImageResource(R.drawable.icon_weixiu);
            }

        }
        return view;
    }

    private class MyViewholder {
        View      view_line;
        ImageView img_kind, img_type;
        TextView tv_accept, tv_call_phone, tv_compl, tv_num, tv_address, tv_cont, tv_contPhone, tv_warn;
    }
}

package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.messegeInfo.AnzYuyueInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ShowCallUtil;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/27 10:04
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvOrderAdapter extends BaseAdapter {
    private Context mContext;
    private List    mList;
    private String  mKind;

    public LvOrderAdapter(Context context, List list, String kind) {
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
            viewholder.tv_num = view.findViewById(R.id.tv_num);
            viewholder.tv_address = view.findViewById(R.id.tv_address);
            viewholder.tv_cont = view.findViewById(R.id.tv_cont);
            viewholder.tv_contPhone = view.findViewById(R.id.tv_contPhone);
            viewholder.tv_warn = view.findViewById(R.id.tv_warn);
            viewholder.view_line = view.findViewById(R.id.view_line);
            viewholder.tv_accept = view.findViewById(R.id.tv_accept);
            viewholder.tv_call_phone = view.findViewById(R.id.tv_call_phone);
            view.setTag(viewholder);
        } else {
            viewholder = (MyViewholder) view.getTag();
        }
        viewholder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
        viewholder.tv_accept.setBackgroundResource(R.drawable.bg_round_ora);
        viewholder.tv_call_phone.setBackgroundResource(R.drawable.bg_round_ora);
        if ("0".equals(mKind)) {
            viewholder.tv_accept.setText("提货");
            viewholder.tv_call_phone.setText("打电话");
            final PeiSInfo.ApplyBean bean = (PeiSInfo.ApplyBean) mList.get(i);
            viewholder.tv_num.setText(bean.getForderno());
            viewholder.tv_address.setText(bean.getFaddress());
            viewholder.tv_cont.setText(bean.getFpeople());
            viewholder.tv_contPhone.setText(bean.getFtel());
            viewholder.tv_warn.setText(bean.getSpecial_note());
            viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //提货
                    getTheGoods(((PeiSInfo.ApplyBean) mList.get(i)).getId(), i);
                }
            });
            viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String ftel = ((PeiSInfo.ApplyBean) mList.get(i)).getFtel();
                    String orderID = ((PeiSInfo.ApplyBean) mList.get(i)).getId();
                    if (null == ftel || "".equals(ftel)) {
                        ToastUtils.showToast(mContext, "该订单没有留存电话");
                        return;
                    }
                    ShowCallUtil.showCallDialog(mContext, ftel,orderID);
                }
            });
        } else {
            viewholder.tv_accept.setText("扫码");
            viewholder.tv_call_phone.setText("打电话");
            if ("1".equals(mKind)) {//安装
                AnzYuyueInfo anzYuyueInfo = (AnzYuyueInfo) mList.get(i);
                viewholder.tv_num.setText(anzYuyueInfo.getForderno());
                viewholder.tv_address.setText(anzYuyueInfo.getFaddress());
                viewholder.tv_cont.setText(anzYuyueInfo.getFpeople());
                viewholder.tv_contPhone.setText(anzYuyueInfo.getFtel());
                viewholder.tv_warn.setText(anzYuyueInfo.getSpecial_note());
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO:安装预约

                    }
                });
                viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phoneNum = ((AnzYuyueInfo) mList.get(i)).getFtel();
                        String orderID = ((AnzYuyueInfo) mList.get(i)).getId();
                        //拨打电话
                        if (null == phoneNum || "".equals(phoneNum)) {
                            ToastUtils.showToast(mContext, "该订单没有留存电话");
                            return;
                        }
                        ShowCallUtil.showCallDialog(mContext, phoneNum,orderID);
                    }
                });
            } else {//维修
                //TODO:
                viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phoneNum = "";
                        //拨打电话
                        if (null == phoneNum || "".equals(phoneNum)) {
                            ToastUtils.showToast(mContext, "该订单没有留存电话");
                            return;
                        }
                        ShowCallUtil.showCallDialog(mContext, phoneNum,"");
                    }
                });
            }
        }
        return view;
    }

    private class MyViewholder {
        View     view_line;
        TextView tv_accept, tv_call_phone, tv_num, tv_address, tv_cont, tv_contPhone, tv_warn;
    }

    private void getTheGoods(String orderID, final int item) {
        ProgressDialogUtil.startShow(mContext, "正在查询，请稍后...");
        String pswcSUrl = NetConfig.PSWC1;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        params.put("psstatus", "1");
        HttpOkhUtils.getInstance().doGetWithParams(pswcSUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(mContext, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(mContext, code + "网络连接错误");
                    return;
                }
                Gson gson = new Gson();
                PeiSInfo peiSInfo = gson.fromJson(resbody, PeiSInfo.class);
                int result = peiSInfo.getResult();
                if (result == 1) {
                    ToastUtils.showToast(mContext, "提货成功");
                    mList.remove(item);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(mContext, "提货失败");
                }
            }
        });
    }
}

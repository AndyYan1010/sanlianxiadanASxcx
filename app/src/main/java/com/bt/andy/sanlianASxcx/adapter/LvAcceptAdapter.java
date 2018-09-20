package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.messegeInfo.InstAndRepInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.UpDateOrderInfo;
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
 * @创建时间 2018/5/27 19:34
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvAcceptAdapter extends BaseAdapter {
    private Context                            mContext;
    private List<InstAndRepInfo.ApplylistBean> mList;

    public LvAcceptAdapter(Context context, List<InstAndRepInfo.ApplylistBean> list) {
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
            view.setTag(viewholder);
        } else {
            viewholder = (MyViewholder) view.getTag();
        }
        viewholder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.green_100));
        viewholder.tv_accept.setBackgroundResource(R.drawable.bg_round_green);
        viewholder.tv_call_phone.setBackgroundResource(R.drawable.bg_round_green);

        InstAndRepInfo.ApplylistBean applylistBean = mList.get(i);
        String ordertype = applylistBean.getOrdertype();
        if ("安装单".equals(ordertype)) {
            viewholder.img_kind.setImageResource(R.drawable.icon_anzhuang);
            viewholder.img_type.setImageResource(R.drawable.icon_paid);
        } else if ("配送单".equals(ordertype)) {
            viewholder.img_kind.setImageResource(R.drawable.icon_peisong);
            viewholder.img_type.setImageResource(R.drawable.icon_paid);
        } else if ("维修单".equals(ordertype)) {
            viewholder.img_kind.setImageResource(R.drawable.icon_weixiu);
            viewholder.img_type.setImageResource(R.drawable.icon_paid);
        } else if ("抢单安装单".equals(ordertype)) {
            viewholder.img_kind.setImageResource(R.drawable.icon_anzhuang);
            viewholder.img_type.setImageResource(R.drawable.icon_rob);
        } else if ("抢单配送单".equals(ordertype)) {
            viewholder.img_kind.setImageResource(R.drawable.icon_peisong);
            viewholder.img_type.setImageResource(R.drawable.icon_rob);
        } else if ("抢单维修单".equals(ordertype)) {
            viewholder.img_kind.setImageResource(R.drawable.icon_weixiu);
            viewholder.img_type.setImageResource(R.drawable.icon_rob);
        }
        viewholder.tv_num.setText(applylistBean.getForderno());
        viewholder.tv_address.setText(applylistBean.getFaddress());
        viewholder.tv_cont.setText(applylistBean.getFpeople());
        viewholder.tv_contPhone.setText(applylistBean.getFtel());
        viewholder.tv_warn.setText(applylistBean.getSpecial_note());
        viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = ((InstAndRepInfo.ApplylistBean) mList.get(i)).getFtel();
                if (null == phone || "".equals(phone)) {
                    ToastUtils.showToast(mContext, "该订单没有留存电话");
                    return;
                }
                ShowCallUtil.showCallDialog(mContext, phone);
            }
        });
        viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstAndRepInfo.ApplylistBean applylistBean1 = mList.get(i);
                String ordertype1 = applylistBean1.getOrdertype();
                String orderID = applylistBean1.getId();
                if ("安装单".equals(ordertype1)) {
                    acceptOrder02(orderID,i);
                } else if ("配送单".equals(ordertype1)) {
                    acceptPsOrder(orderID,i);
                } else if ("维修单".equals(ordertype1)) {
                    acceptOrder02(orderID,i);
                } else if ("抢单安装单".equals(ordertype1)) {
                    robOrder(orderID, "安装单", i);
                } else if ("抢单配送单".equals(ordertype1)) {
                    robOrder(orderID, "配送单", i);
                } else if ("抢单维修单".equals(ordertype1)) {
                    robOrder(orderID, "维修单", i);
                }
            }
        });
        return view;
    }

    private class MyViewholder {
        View      view_line;
        ImageView img_kind, img_type;
        TextView tv_accept, tv_call_phone, tv_num, tv_address, tv_cont, tv_contPhone, tv_warn;
    }

    private void robOrder(String orderID, String type, final int item) {
        ProgressDialogUtil.startShow(mContext, "正在抢单...");
        String changeOrderUrl = NetConfig.UPDATEORDER;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        params.put("ordertype", type);
        params.put("username", MyApplication.userName);
        HttpOkhUtils.getInstance().doGetWithParams(changeOrderUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(mContext, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(mContext, "抢单失败，网络错误");
                    return;
                }
                Gson gson = new Gson();
                UpDateOrderInfo upDateOrderInfo = gson.fromJson(resbody, UpDateOrderInfo.class);
                int result = upDateOrderInfo.getResult();
                if (result == 1) {
                    ToastUtils.showToast(mContext, "抢单成功");
                    mList.remove(item);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(mContext, "抢单失败");
                }
            }
        });
    }

    //安装、维修接单
    private void acceptOrder02(String orderID, final int item) {
        ProgressDialogUtil.startShow(mContext, "正在接单...");
        String yySUrl = NetConfig.YUYUE;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        HttpOkhUtils.getInstance().doGetWithParams(yySUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(mContext, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(mContext, "网络错误");
                    return;
                }
                Gson gson = new Gson();
                UpDateOrderInfo upDateOrderInfo = gson.fromJson(resbody, UpDateOrderInfo.class);
                int result = upDateOrderInfo.getResult();
                if (result == 1) {
                    ToastUtils.showToast(mContext, "接单成功");
                    mList.remove(item);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(mContext, "接单失败");
                }
            }
        });
    }

    //配送接单
    private void acceptPsOrder(String orderID, final int item) {
        ProgressDialogUtil.startShow(mContext, "正在查询，请稍后...");
        String pswcSUrl = NetConfig.PSWC1;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        params.put("psstatus", "0");
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
                    ToastUtils.showToast(mContext, code + "网络错误");
                    return;
                }
                Gson gson = new Gson();
                PeiSInfo peiSInfo = gson.fromJson(resbody, PeiSInfo.class);
                int result = peiSInfo.getResult();
                if (result == 1) {
                    ToastUtils.showToast(mContext, "接单成功");
                    mList.remove(item);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(mContext, "接单失败");
                }
            }
        });
    }
}

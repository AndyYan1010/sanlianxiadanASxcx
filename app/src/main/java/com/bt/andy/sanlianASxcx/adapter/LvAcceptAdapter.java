package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    private Context mContext;
    private List    mList;
    private String  mKind;//0是配送，1是安装，2是维修
    private String  mType;//类别：rob抢单，plan排单

    public LvAcceptAdapter(Context context, List list, String kind, String type) {
        this.mContext = context;
        this.mList = list;
        this.mKind = kind;
        this.mType = type;
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
        viewholder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.green_100));
        viewholder.tv_accept.setBackgroundResource(R.drawable.bg_round_green);
        viewholder.tv_call_phone.setBackgroundResource(R.drawable.bg_round_green);
        if ("0".equals(mKind)) {
            viewholder.tv_call_phone.setVisibility(View.VISIBLE);
            if ("rob".equals(mType)) {//抢单
                final PeiSInfo.OrderazlistBean bean = (PeiSInfo.OrderazlistBean) mList.get(i);
                viewholder.tv_num.setText(bean.getOrderno());
                viewholder.tv_address.setText(bean.getFDeliveryAddress());
                viewholder.tv_cont.setText(bean.getFcontact());
                viewholder.tv_contPhone.setText(bean.getFmobile());
                viewholder.tv_warn.setText(bean.getNote());
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //抢单
                        robOrder(i);
                    }
                });
                //打电话
                viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = ((PeiSInfo.OrderazlistBean) mList.get(i)).getFmobile();
                        if (null == phone || "".equals(phone)) {
                            ToastUtils.showToast(mContext, "该订单没有留存电话");
                            return;
                        }
                        ShowCallUtil.showCallDialog(mContext, phone);
                    }
                });
            } else {//配送分配排单
                final PeiSInfo.ApplyBean bean = (PeiSInfo.ApplyBean) mList.get(i);
                viewholder.tv_num.setText(bean.getForderno());
                viewholder.tv_address.setText(bean.getFaddress());
                viewholder.tv_cont.setText(bean.getFpeople());
                viewholder.tv_contPhone.setText(bean.getFtel());
                viewholder.tv_warn.setText(bean.getSpecial_note());
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //接单
                        acceptPsOrder(((PeiSInfo.ApplyBean) mList.get(i)).getId(), i);
                    }
                });
                //打电话
                viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = ((PeiSInfo.ApplyBean) mList.get(i)).getFtel();
                        if (null == phone || "".equals(phone)) {
                            ToastUtils.showToast(mContext, "该订单没有留存电话");
                            return;
                        }
                        ShowCallUtil.showCallDialog(mContext, phone);
                    }
                });
            }
        } else {//安装、配送
            viewholder.tv_call_phone.setVisibility(View.GONE);
            if ("rob".equals(mType)) {
                final InstAndRepInfo.OrderazlistBean orderInfo = (InstAndRepInfo.OrderazlistBean) mList.get(i);
                viewholder.tv_num.setText(orderInfo.getOrderno());
                viewholder.tv_address.setText(orderInfo.getFDeliveryAddress());
                viewholder.tv_cont.setText(orderInfo.getFcontact());
                viewholder.tv_contPhone.setText(orderInfo.getFmobile());
                viewholder.tv_warn.setText(orderInfo.getNote());
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //抢单
                        robOrder(i);
                    }
                });
            } else {//排单
                final InstAndRepInfo.ApplyBean applyInfo = (InstAndRepInfo.ApplyBean) mList.get(i);
                viewholder.tv_num.setText(applyInfo.getForderno());
                viewholder.tv_address.setText(applyInfo.getFaddress());
                viewholder.tv_cont.setText(applyInfo.getFpeople());
                viewholder.tv_contPhone.setText(applyInfo.getFtel());
                viewholder.tv_warn.setText(applyInfo.getSpecial_note());
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //接单
                        acceptOrder02(((InstAndRepInfo.OrderazlistBean) mList.get(i)).getId(), i);
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

    private void robOrder(final int item) {
        String orderID;
        String type;
        String status;
        if ("0".equals(mKind)) {
            PeiSInfo.OrderazlistBean orderazlistBean = (PeiSInfo.OrderazlistBean) mList.get(item);
            orderID = orderazlistBean.getId();
            type = "配送单";
            status = orderazlistBean.getBpmstatus();
        } else if ("1".equals(mKind)) {
            InstAndRepInfo.OrderazlistBean bean = (InstAndRepInfo.OrderazlistBean) mList.get(item);
            orderID = bean.getId();
            type = "安装单";
            status = bean.getPsstatus();
        } else {
            InstAndRepInfo.OrderazlistBean bean = (InstAndRepInfo.OrderazlistBean) mList.get(item);
            orderID = bean.getId();
            type = "维修单";
            status = bean.getBpmstatus();
        }
        ProgressDialogUtil.startShow(mContext, "正在抢单...");
        String changeOrderUrl = NetConfig.UPDATEORDER;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        params.put("ordertype", type);
        params.put("username", MyApplication.userName);
        params.put("status", status);
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
    
    private void acceptOrder02(String orderID, final int item) {
        ProgressDialogUtil.startShow(mContext, "正在抢单...");
        String yySUrl = NetConfig.YUYUE;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        params.setUseJsonStreamer(true);
        HttpOkhUtils.getInstance().doPost(yySUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(mContext, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(mContext, "网络连接错误");
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

    //接单
    private void acceptPsOrder(String orderID, final int item) {
        if (null == orderID || "".equals(orderID)) {
            ToastUtils.showToast(mContext, "该订单没有获取到id");
            return;
        }
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

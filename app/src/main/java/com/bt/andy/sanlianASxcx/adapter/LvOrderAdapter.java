package com.bt.andy.sanlianASxcx.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.activity.SaomiaoUIActivity;
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
    private Context                      mContext;
    private List<PeiSInfo.ApplylistBean> mList;
    private int                          which;

    public LvOrderAdapter(Context context, List<PeiSInfo.ApplylistBean> list) {
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
        viewholder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
        viewholder.tv_accept.setBackgroundResource(R.drawable.bg_round_ora);
        viewholder.tv_call_phone.setBackgroundResource(R.drawable.bg_round_ora);
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
            viewholder.tv_accept.setText("提货");
            viewholder.tv_call_phone.setText("打电话");
            viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //提货
                    getPsGoods(mList.get(i).getId(), i);
                }
            });
            viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String ftel = mList.get(i).getFtel();
                    if (null == ftel || "".equals(ftel)) {
                        ToastUtils.showToast(mContext, "该订单没有留存电话");
                        return;
                    }
                    ShowCallUtil.showCallDialog(mContext, ftel);
                }
            });
        } else {
            if (ordertype.contains("安装")) {
                viewholder.img_kind.setImageResource(R.drawable.icon_anzhuang);
            } else {
                viewholder.img_kind.setImageResource(R.drawable.icon_weixiu);
            }
            viewholder.tv_accept.setText("打电话");
            viewholder.tv_call_phone.setText("预约");
            String fbstatus = applylistBean.getFbstatus();
            viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNum = mList.get(i).getFtel();
                    String orderID = mList.get(i).getId();
                    //拨打电话
                    if (null == phoneNum || "".equals(phoneNum)) {
                        ToastUtils.showToast(mContext, "该订单没有留存电话");
                        return;
                    }
                    ShowCallUtil.showCallDialog(mContext, phoneNum, orderID);
                }
            });
            viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //预约更改成上门服务状态
                    upToService(mList.get(i).getId(), i);

                    //                    which = i;
                    //                    //扫描二维码
                    //                    //动态申请照相机权限,开启照相机
                    //                    scanningCode();
                }
            });
//            if ("4".equals(fbstatus)) {//打过电话
//                viewholder.tv_call_phone.setText("预约");
//                viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //预约更改成上门服务状态
//                        upToService(mList.get(i).getId(), i);
//                    }
//                });
//            }
        }
        return view;
    }

    private class MyViewholder {
        View      view_line;
        ImageView img_kind, img_type;
        TextView tv_accept, tv_call_phone, tv_num, tv_address, tv_cont, tv_contPhone, tv_warn;
    }

    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;//申请照相机权限结果
    private int REQUEST_CODE                       = 1002;//接收扫描结果

    private void scanningCode() {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            Intent intent = new Intent(mContext, SaomiaoUIActivity.class);//这是一个自定义的扫描界面，扫描UI框放大了。
            ((Activity) mContext).startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private void upToService(String orderID, final int item) {
        ProgressDialogUtil.startShow(mContext, "正在提交，请稍后...");
        String upToServiceUrl = NetConfig.UPDATETYPE1;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        HttpOkhUtils.getInstance().doGetWithParams(upToServiceUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(mContext, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(mContext, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                PeiSInfo peiSInfo = gson.fromJson(resbody, PeiSInfo.class);
                int result = peiSInfo.getResult();
                if (result == 1) {
                    ToastUtils.showToast(mContext, "预约成功");
                    mList.remove(item);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(mContext, "预约失败");
                }
            }
        });
    }

    private void getPsGoods(String orderID, final int item) {
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
                    ToastUtils.showToast(mContext, "网络错误" + code);
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

    public int getWhich() {
        return which;
    }
}

package com.bt.andy.sanlianASxcx.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.messegeInfo.DetailOrderInfo;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/9/6 16:04
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class GetOrderDetailInfoUtil {
    private Context mContext;
    private String  mKind;

    public GetOrderDetailInfoUtil(Context context, String kind) {
        this.mContext = context;
        this.mKind = kind;
    }

    public void showMoreInfo(String orderID) {
        //获取订单详情
        getMoreOrderInfo(orderID);
    }

    //搜索订单详情
    private void getMoreOrderInfo(String orderID) {
        ProgressDialogUtil.startShow(mContext, "正在搜索");
        String detailUrl = NetConfig.DETAIL;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        HttpOkhUtils.getInstance().doGetWithParams(detailUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                try {
                    JSONArray jsonArray = new JSONArray(resbody);
                    DetailOrderInfo detailInfo = gson.fromJson(jsonArray.get(0).toString(), DetailOrderInfo.class);
                    //展示订单详情
                    showMoreInfo(detailInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showToast(mContext, "获取订单详情失败");
                }
            }
        });
    }

    private void showMoreInfo(DetailOrderInfo detailInfo) {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.alert_ps_jd, null);
        TextView tv_pm = v.findViewById(R.id.tv_pm);
        TextView tv_num = v.findViewById(R.id.tv_num);
        TextView tv_address = v.findViewById(R.id.tv_address);
        TextView tv_date = v.findViewById(R.id.tv_date);
        TextView tv_jdate = v.findViewById(R.id.tv_jdate);
        TextView tv_azdate = v.findViewById(R.id.tv_azdate);
        TextView tv_task = v.findViewById(R.id.tv_task);
        TextView tv_person = v.findViewById(R.id.tv_person);
        TextView tv_close = v.findViewById(R.id.tv_close);
        tv_pm.setText(detailInfo.getPinming());
        tv_num.setText(detailInfo.getQty());
        tv_address.setText(detailInfo.getFaddress());
        tv_date.setText(detailInfo.getFgodate());
        tv_jdate.setText(detailInfo.getFdate());
        tv_azdate.setText(detailInfo.getAzfdate());
        tv_task.setText(detailInfo.getNotes());
        if ("0".equals(mKind)) {
            tv_person.setText(detailInfo.getPsshifu());
        } else {
            tv_person.setText(detailInfo.getFshifu());
        }
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        dialog.setTitle("详细信息");
        TextView title = new TextView(mContext);
        title.setGravity(Gravity.CENTER);
        title.setText("详细信息");
        dialog.setCustomTitle(title);
        dialog.setView(v);
        dialog.show();
    }
}

package com.bt.andy.sanlianASxcx.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.messegeInfo.DetailOrderInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.LoginInfo;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.bt.andy.sanlianASxcx.viewmodle.CustomDatePicker;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private boolean mCanChange;

    public GetOrderDetailInfoUtil(Context context, String kind, boolean canChange) {
        this.mContext = context;
        this.mKind = kind;
        this.mCanChange = canChange;
    }

    public void showMoreInfo(String orderID) {
        if (null == mKind || "".equals(mKind)) {
            ToastUtils.showToast(mContext, "该订单未填写类别");
            return;
        }
        //获取订单详情
        getMoreOrderInfo(orderID);
    }

    //搜索订单详情
    private void getMoreOrderInfo(String orderID) {
        ProgressDialogUtil.startShow(mContext, "正在获取详情...");
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
                    ToastUtils.showToast(mContext, "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                try {
                    DetailOrderInfo detailInfo = gson.fromJson(resbody, DetailOrderInfo.class);
                    DetailOrderInfo.ApplylistBean applylistBean = detailInfo.getApplylist().get(0);
                    //展示订单详情
                    showMoreInfo(applylistBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast(mContext, "获取订单详情失败");
                }
            }
        });
    }

    private void showMoreInfo(final DetailOrderInfo.ApplylistBean detailInfo) {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.alert_ps_jd, null);
        TextView tv_pm = v.findViewById(R.id.tv_pm);
        TextView tv_num = v.findViewById(R.id.tv_num);
        TextView tv_address = v.findViewById(R.id.tv_address);
        final TextView tv_date = v.findViewById(R.id.tv_date);
        TextView tv_jdate = v.findViewById(R.id.tv_jdate);
        TextView tv_azdate = v.findViewById(R.id.tv_azdate);
        TextView tv_task = v.findViewById(R.id.tv_task);
        TextView tv_person = v.findViewById(R.id.tv_person);
        TextView tv_close = v.findViewById(R.id.tv_close);
        LinearLayout linear_godate = v.findViewById(R.id.linear_godate);
        LinearLayout linear_save = v.findViewById(R.id.linear_save);
        if (mCanChange) {
            linear_save.setVisibility(View.VISIBLE);
            linear_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String date = String.valueOf(tv_date.getText()).trim();
                    if (null == date || "--".equals(date)) {
                        date = "";
                    }
                    //提交修改信息
                    sendSaveData(dialog, detailInfo.getId(), date);
                }
            });
            linear_godate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取当前日期
                    String nowDate = getNowDate();
                    //打开时间选择器
                    CustomDatePicker dpk = new CustomDatePicker(mContext, new CustomDatePicker.ResultHandler() {
                        @Override
                        public void handle(String time) { // 回调接口，获得选中的时间
                            tv_date.setText(time);
                        }
                    }, "2010-01-01 00:00", "2090-12-31 00:00"); //"2090-12-31 00:00" 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                    dpk.showSpecificTime(true); // 显示时和分
                    dpk.setIsLoop(true); // 允许循环滚动
                    dpk.show(nowDate);
                }
            });
        }
        tv_pm.setText(detailInfo.getPinming());
        tv_num.setText(detailInfo.getQty());//数量
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

    private void sendSaveData(final AlertDialog dialog, String orderID, String date) {
        ProgressDialogUtil.startShow(mContext, "正在提交修改信息");
        String updateUrl = NetConfig.UPDATEDATE;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        params.put("fgodate", date);
        HttpOkhUtils.getInstance().doGetWithParams(updateUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                LoginInfo loginInfo = gson.fromJson(resbody, LoginInfo.class);
                int result = loginInfo.getResult();
                if (result == 1) {
                    ToastUtils.showToast(mContext, "保存修改成功");
                    dialog.dismiss();
                } else {
                    ToastUtils.showToast(mContext, "网络错误，修改失败");
                }
            }
        });
    }

    private String getNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String data = simpleDateFormat.format(new Date());
        return data;
    }
}

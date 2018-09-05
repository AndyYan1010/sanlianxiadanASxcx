package com.bt.andy.sanlianASxcx.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.activity.UploadPicActivity;
import com.bt.andy.sanlianASxcx.messegeInfo.AnzYuyueInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.LoginInfo;
import com.bt.andy.sanlianASxcx.messegeInfo.PeiSInfo;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/27 10:05
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvServiceAdapter extends BaseAdapter {
    private Context mContext;
    private List    mList;
    private String  mKind;

    public LvServiceAdapter(Context context, List<String> list, String kind) {
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
        final MyViewholder viewholder;
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
        viewholder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.brown));
        //        viewholder.tv_compl.setVisibility(View.VISIBLE);
        viewholder.tv_accept.setBackgroundResource(R.drawable.bg_round_brown);
        viewholder.tv_call_phone.setBackgroundResource(R.drawable.bg_round_brown);
        //        viewholder.tv_compl.setBackgroundResource(R.drawable.bg_round_brown);
        //        viewholder.tv_accept.setText("上传");

        //填充信息
        if ("0".equals(mKind)) {
            final PeiSInfo.ApplyBean bean = (PeiSInfo.ApplyBean) mList.get(i);
            viewholder.tv_num.setText(bean.getForderno());
            viewholder.tv_address.setText(bean.getFaddress());
            viewholder.tv_cont.setText(bean.getFpeople());
            viewholder.tv_contPhone.setText(bean.getFtel());
            viewholder.tv_warn.setText(bean.getSpecial_note());

            String fbstatus = bean.getFbstatus();
            if ("5".equals(fbstatus)) {
                viewholder.tv_accept.setText("签到");
                viewholder.tv_call_phone.setVisibility(View.GONE);
                viewholder.tv_compl.setVisibility(View.GONE);
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PeiSInfo.ApplyBean bean = (PeiSInfo.ApplyBean) mList.get(i);
                        signLocal(bean.getId());
                    }
                });
            } else {
                viewholder.tv_accept.setText("上传");
                viewholder.tv_call_phone.setVisibility(View.VISIBLE);
                viewholder.tv_call_phone.setText("签退");
                viewholder.tv_compl.setVisibility(View.GONE);
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String orderID = ((PeiSInfo.ApplyBean) mList.get(i)).getId();
                        if (null == orderID || "".equals(orderID)) {
                            ToastUtils.showToast(mContext, "该订单没有获取到id");
                            return;
                        }
                        //上传图片
                        upPic(orderID, ((PeiSInfo.ApplyBean) mList.get(i)).getFbstatus());
                    }
                });
                viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//签退
                        String orderID = ((PeiSInfo.ApplyBean) mList.get(i)).getId();
                        openPopupWindow(viewholder.tv_call_phone, i, orderID);
                    }
                });
            }

            //            viewholder.tv_compl.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View view) {
            //                    //确认完成
            //                    completeOrder(((PeiSInfo.ApplyBean) mList.get(i)).getId(), i);
            //                }
            //            });
            //            viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View view) {
            //                    String phoneNum = ((PeiSInfo.ApplyBean) mList.get(i)).getFtel();
            //                    if (null == phoneNum || "".equals(phoneNum)) {
            //                        ToastUtils.showToast(mContext, "该订单没有留存电话");
            //                        return;
            //                    }
            //                    ShowCallUtil.showCallDialog(mContext, phoneNum);
            //                }
            //            });
        } else {
            final AnzYuyueInfo info = (AnzYuyueInfo) mList.get(i);
            viewholder.tv_num.setText(info.getForderno());
            viewholder.tv_address.setText(info.getFaddress());
            viewholder.tv_cont.setText(info.getFpeople());
            viewholder.tv_contPhone.setText(info.getFtel());
            viewholder.tv_warn.setText(info.getSpecial_note());

            String fbstatus = info.getFbstatus();
            if ("5".equals(fbstatus)) {
                viewholder.tv_compl.setVisibility(View.GONE);
                viewholder.tv_call_phone.setVisibility(View.GONE);
                viewholder.tv_accept.setText("签到");
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AnzYuyueInfo info = (AnzYuyueInfo) mList.get(i);
                        signLocal(info.getId());
                    }
                });
            } else {
                viewholder.tv_accept.setText("上传");
                viewholder.tv_call_phone.setVisibility(View.VISIBLE);
                viewholder.tv_call_phone.setText("签退");
                viewholder.tv_compl.setVisibility(View.GONE);
                viewholder.tv_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String orderID = ((AnzYuyueInfo) mList.get(i)).getId();
                        if (null == orderID || "".equals(orderID)) {
                            ToastUtils.showToast(mContext, "该订单没有获取到id");
                            return;
                        }
                        //上传图片
                        upPic(orderID, ((AnzYuyueInfo) mList.get(i)).getFbstatus());
                    }
                });
                viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//签退
                        String orderID = ((AnzYuyueInfo) mList.get(i)).getId();
                        openPopupWindow(viewholder.tv_call_phone, i, orderID);
                    }
                });
            }

            //            viewholder.tv_compl.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View view) {
            //                    //TODO:确认完成
            //                    complAzOrder(i);
            //                }
            //            });
            //            viewholder.tv_call_phone.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View view) {
            //                    String phoneNum = ((AnzYuyueInfo) mList.get(i)).getFtel();
            //                    if (null == phoneNum || "".equals(phoneNum)) {
            //                        ToastUtils.showToast(mContext, "该订单没有留存电话");
            //                        return;
            //                    }
            //                    ShowCallUtil.showCallDialog(mContext, phoneNum);
            //                }
            //            });
        }
        return view;
    }

    private class MyViewholder {

        View     view_line;
        TextView tv_accept, tv_call_phone, tv_compl, tv_num, tv_address, tv_cont, tv_contPhone, tv_warn;
    }

    private void signLocal(String orderID) {
        String lngAndLat = getLngAndLat(mContext);
        String[] split = lngAndLat.split(",");
        String lng = split[0];
        String lat = split[1];
        ProgressDialogUtil.startShow(mContext, "正在签到，请稍等...");
        String signUrl = NetConfig.SHANGMEN;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        params.put("lng", null == lng ? "" : lng);
        params.put("lat", null == lat ? "" : lat);
        HttpOkhUtils.getInstance().doGetWithParams(signUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                    ToastUtils.showToast(mContext, "签到成功");
                } else {
                    ToastUtils.showToast(mContext, "签到失败，请重新签到");
                }
            }
        });
    }

    private PopupWindow popupWindow;

    private void openPopupWindow(View v, int item, String orderID) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_popupwindow, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view, item, orderID);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    //设置PopupWindow的View点击事件
    private void setOnPopupViewClick(View view, final int item, final String orderID) {
        TextView tv_kp, tv_wait, tv_back, tv_compl, tv_cancel;
        tv_kp = (TextView) view.findViewById(R.id.tv_kp);//空跑
        tv_wait = (TextView) view.findViewById(R.id.tv_wait);//等通知
        tv_back = (TextView) view.findViewById(R.id.tv_back);//返工
        tv_compl = (TextView) view.findViewById(R.id.tv_compl);//完工
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);//取消
        tv_kp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complAzOrder(item, orderID, "kongpao");
            }
        });
        tv_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complAzOrder(item, orderID, "dengtongzhi");
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complAzOrder(item, orderID, "fangong");
            }
        });
        tv_compl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complAzOrder(item, orderID, "9");
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != popupWindow) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    private void complAzOrder(final int item, String orderID, String status) {
        ProgressDialogUtil.startShow(mContext, "正在签退，请稍后...");
        String wcUrl = NetConfig.WANCHENG;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        params.put("status", status);
        HttpOkhUtils.getInstance().doPost(wcUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                    ToastUtils.showToast(mContext, "签退成功");
                    mList.remove(item);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(mContext, "签退失败");
                }
            }
        });
    }

    private void upPic(String orderID, String times) {
        //TODO:还需要哪些提交信息?
        Intent intent = new Intent(mContext, UploadPicActivity.class);
        intent.putExtra("kind", mKind);//kind 0配送、1.2安装维修
        intent.putExtra("orderID", orderID);
        intent.putExtra("subtimes", times);
        mContext.startActivity(intent);
    }

    private void completeOrder(String orderID, final int item) {
        ProgressDialogUtil.startShow(mContext, "正在查询，请稍后...");
        String pswcUrl = NetConfig.PSWC;
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        HttpOkhUtils.getInstance().doGetWithParams(pswcUrl, params, new HttpOkhUtils.HttpCallBack() {
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
                    ToastUtils.showToast(mContext, "提交失败");
                }
            }
        });
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    private String getLngAndLat(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork();
            }
        } else {    //从网络获取经纬度
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        return longitude + "," + latitude;
    }

    //从网络获取经纬度
    public String getLngAndLatWithNetwork() {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        return longitude + "," + latitude;
    }

    LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {

        }
    };
}

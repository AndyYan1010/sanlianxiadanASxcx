package com.bt.andy.sanlianASxcx.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bt.andy.sanlianASxcx.MainActivity;
import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.messegeInfo.LoginInfo;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.SpUtils;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 9:05
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private Button   bt_driver;//司机登录按钮
    private EditText edit_num, edit_psd;
    private CheckBox ck_remPas;
    private boolean isRem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_actiivty);
        MyApplication.flag = 0;
        getView();
        setData();
    }

    private void getView() {
        edit_num = (EditText) findViewById(R.id.edit_num);
        edit_psd = (EditText) findViewById(R.id.edit_psd);
        ck_remPas = (CheckBox) findViewById(R.id.ck_remPas);
        bt_driver = (Button) findViewById(R.id.bt_driver);
    }

    private void setData() {
        Boolean isRemem = SpUtils.getBoolean(LoginActivity.this, "isRem", false);
        if (isRemem) {
            isRem = true;
            ck_remPas.setChecked(true);
            String name = SpUtils.getString(LoginActivity.this, "name");
            String psd = SpUtils.getString(LoginActivity.this, "psd");
            edit_num.setText(name);
            edit_psd.setText(psd);
        }
        ck_remPas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRem = b;
            }
        });
        bt_driver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_driver:
                String name = String.valueOf(edit_num.getText()).trim();
                String psd = String.valueOf(edit_psd.getText()).trim();
                if ("".equals(name) || "请输入账号".equals(name)) {
                    ToastUtils.showToast(LoginActivity.this, "请输入账号");
                    return;
                }
                if ("".equals(psd) || "请输入密码".equals(psd)) {
                    ToastUtils.showToast(LoginActivity.this, "请输入密码");
                    return;
                }
                //是否记住账号密码
                isNeedRem(name, psd);
                //登录
                loginToService(name, psd);
                break;
        }
    }

    private void isNeedRem(String name, String psd) {
        SpUtils.putBoolean(LoginActivity.this, "isRem", isRem);
        if (isRem) {
            SpUtils.putString(LoginActivity.this, "name", name);
            SpUtils.putString(LoginActivity.this, "psd", psd);
        }
    }

    private void loginToService(String name, String psd) {
        ProgressDialogUtil.startShow(LoginActivity.this, "正在登录请稍后");
        String loginUrl = NetConfig.LOGINURL;
        RequestParamsFM params = new RequestParamsFM();
        params.put("username", name);
        params.put("password", psd);
        HttpOkhUtils.getInstance().doGetWithParams(loginUrl, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(LoginActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                if (code != 200) {
                    ToastUtils.showToast(LoginActivity.this, code + "网络错误");
                    return;
                }
                Gson gson = new Gson();
                LoginInfo loginInfo = gson.fromJson(resbody, LoginInfo.class);
                int result = loginInfo.getResult();
                if (result == 1) {
                    MyApplication.isLogin = 1;
                    MyApplication.userName = loginInfo.getUsername();
                    MyApplication.userID = loginInfo.getId();
                    String isAlias = SpUtils.getString(LoginActivity.this, "IsAlias", "");
                    //判断是否设置过别名
                    if ("".equals(isAlias) || !"1".equals(isAlias)) {
                        //推送设置别名
                        setAlias(loginInfo.getUsername());
                    } else {
                        String aliasName = SpUtils.getString(LoginActivity.this, "AliasID", "");
                        if (!aliasName.equals(loginInfo.getUsername())) {
                            //推送设置别名
                            setAlias(loginInfo.getUsername());
                        }
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (result == 2) {
                    ToastUtils.showToast(LoginActivity.this, "密码错误");
                } else {
                    ToastUtils.showToast(LoginActivity.this, "登录失败");
                }
            }
        });
    }

    private void setAlias(String name) {
        String alias = name;//用户名
        //        String alias = "9527";
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final        TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    ToastUtils.showToast(LoginActivity.this, "success");
                    SpUtils.putString(LoginActivity.this, "IsAlias", "1");
                    SpUtils.putString(LoginActivity.this, "AliasID", MyApplication.userName);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    ToastUtils.showToast(LoginActivity.this, "延迟 60 秒");
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };
    private static final int              MSG_SET_ALIAS  = 1001;
    private final        Handler          mHandler       = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
            }
        }
    };
}

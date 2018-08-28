package com.bt.andy.sanlianASxcx;

import android.app.Activity;
import android.app.Application;

import com.bt.andy.sanlianASxcx.utils.ExceptionUtil;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 8:51
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyApplication extends Application {
    public static boolean             isRelease    = false;//判断程序是否异常
    public static ArrayList<Activity> listActivity = new ArrayList<Activity>();//用来装载activity
    public static int                 flag         = -1;//判断是否被回收
    public static String userID;
    public static String userName;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static void exit() {
        try {
            for (Activity activity : listActivity) {
                activity.finish();
            }
            // 结束进程
            System.exit(0);
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
    }
}

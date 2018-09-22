package com.bt.andy.sanlianASxcx.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;

import com.bt.andy.sanlianASxcx.MainActivity;
import com.bt.andy.sanlianASxcx.MyApplication;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.activity.DistriActivity;
import com.bt.andy.sanlianASxcx.activity.InstallActivity;
import com.bt.andy.sanlianASxcx.activity.RepairActivity;
import com.bt.andy.sanlianASxcx.messegeInfo.TuiSongInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * @创建者 AndyYan
 * @创建时间 2018/9/2 10:53
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyJPushDefineReceIver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    private NotificationManager nm;
    private SoundPool           mSoundPool;
    private int                 mYuluSound;
    private int                 markExamine;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        //        Logger.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            //            Logger.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //            Logger.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //            Logger.d(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //            Logger.d(TAG, "用户点击打开了通知");

            //            openNotification(context, bundle);

        } else {
            //            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        //        Logger.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        //        Logger.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        //        Logger.d(TAG, "extras : " + extras);

        if (MyApplication.isLogin != 1) {
            return;
        }

        //解析extra
        Gson gson = new Gson();
        TuiSongInfo tuiSongInfo = gson.fromJson(extras, TuiSongInfo.class);
        String fshifuid = tuiSongInfo.getFshifuid();
        if ("all".equals(fshifuid) || MyApplication.userID.equals(fshifuid)) {
            String show_msg = "有" + tuiSongInfo.getNumber() + "笔新的" + tuiSongInfo.getOrdertype() + "待查看";
            //振动
            Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
            vibrator.vibrate(3000);
            //配置声音
            //        initSoundPool(context);
            //        mSoundPool.play(mYuluSound, 1, 1, 0, 0, 1);
            //显示通知
            sendNotification(context, show_msg);
        }
    }

    public void initSoundPool(Context context) {
        if (null == mSoundPool) {
            mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }
        mYuluSound = mSoundPool.load(context, R.raw.yulu, 1);
    }

    private void sendNotification(Context context, String message) {//kind=0 在后台，kind=1在前台
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        //延时意图
        /**
         * 参数2：请求码 大于1
         */
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (message.contains("配送")) {
            markExamine = 1;
        } else if (message.contains("安装")) {
            markExamine = 2;
        } else {
            markExamine = 3;
        }
        //        Intent chatIntent;
        //        if (message.contains("配送")) {
        //            chatIntent = new Intent(context, DistriActivity.class);
        //            chatIntent.putExtra("kind", "0");
        //            markExamine = 1;
        //        } else if (message.contains("安装")) {
        //            chatIntent = new Intent(context, InstallActivity.class);
        //            chatIntent.putExtra("kind", "1");
        //            markExamine = 2;
        //        } else {
        //            chatIntent = new Intent(context, RepairActivity.class);
        //            chatIntent.putExtra("kind", "2");
        //            markExamine = 3;
        //        }
        //        chatIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //        Intent[] intents = {mainIntent, chatIntent};
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Intent[] intents = {mainIntent};
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 1, intents, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true) //当点击后自动删除
                .setSmallIcon(R.drawable.icon_01) //必须设置
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("您有一条新消息")
                .setContentText(message)
                .setContentInfo("")
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(uri)
                .build();
        notificationManager.notify(markExamine, notification);
    }

    private boolean isRuninBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
        if (runningTaskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
            return false;
        } else {
            return true;
        }
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        //        String extras = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("ordertype");
        } catch (Exception e) {
            //            Logger.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
        if ("配送".equals(myValue) || myValue.contains("配送")) {
            Intent mIntent = new Intent(context, DistriActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mIntent.putExtra("kind", "0");
            context.startActivity(mIntent);
        } else if ("安装".equals(myValue) || myValue.contains("安装")) {
            Intent mIntent = new Intent(context, InstallActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mIntent.putExtra("kind", "1");
            context.startActivity(mIntent);
        } else {
            Intent mIntent = new Intent(context, RepairActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mIntent.putExtra("kind", "2");
            context.startActivity(mIntent);
        }
    }
}

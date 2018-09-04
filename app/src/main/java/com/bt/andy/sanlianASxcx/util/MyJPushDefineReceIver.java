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
import android.media.SoundPool;
import android.os.Bundle;

import com.bt.andy.sanlianASxcx.MainActivity;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.activity.DistriActivity;
import com.bt.andy.sanlianASxcx.activity.InstallActivity;
import com.bt.andy.sanlianASxcx.activity.RepairActivity;

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
    private Context             mContext;
    private SoundPool           mSoundPool;
    private int                 mDuanSound;
    private int                 mYuluSound;
    private int                 markExamine;

    public MyJPushDefineReceIver(Context context) {
        this.mContext = context;
    }

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

            openNotification(context, bundle);

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
        /**
         * 1. 判断当前应用是否在后台运行
         * 2. 如果是在后台运行，则发出通知栏
         * 3. 如果是在后台发出长声音
         * 4. 如果在前台发出短声音
         */
        if (isRuninBackground()) {
            sendNotification(message);
            //发出长声音
            //参数2/3：左右喇叭声音的大小
            mSoundPool.play(mYuluSound, 1, 1, 0, 0, 1);
        } else {
            //发出短声音
            mSoundPool.play(mDuanSound, 1, 1, 0, 0, 1);
        }
    }

    private boolean isRuninBackground() {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
        if (runningTaskInfo.topActivity.getPackageName().equals(mContext.getPackageName())) {
            return false;
        } else {
            return true;
        }
    }

    public void initSoundPool() {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mDuanSound = mSoundPool.load(mContext, R.raw.yulu, 1);
        mYuluSound = mSoundPool.load(mContext, R.raw.yulu, 1);
    }

    private void sendNotification(String message) {//kind=0 在后台，kind=1在前台
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        //延时意图
        /**
         * 参数2：请求码 大于1
         */
        Intent mainIntent = new Intent(mContext, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent chatIntent;
        if (message.contains("配送")) {
            chatIntent = new Intent(mContext, DistriActivity.class);
            markExamine = 1;
        } else if (message.contains("安装")) {
            chatIntent = new Intent(mContext, InstallActivity.class);
            markExamine = 2;
        } else {
            chatIntent = new Intent(mContext, RepairActivity.class);
            markExamine = 3;
        }
        chatIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent[] intents = {mainIntent, chatIntent};
        PendingIntent pendingIntent = PendingIntent.getActivities(mContext, 1, intents, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(mContext)
                .setAutoCancel(true) //当点击后自动删除
                .setSmallIcon(R.drawable.message) //必须设置
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("您有一条新消息")
                .setContentText(message)
                .setContentInfo("")
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        notificationManager.notify(markExamine, notification);
    }

    private void openNotification(Context context, Bundle bundle) {
        //        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String extras = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            //            Logger.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
        if ("配送".equals(myValue)) {
            Intent mIntent = new Intent(context, DistriActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        } else if ("安装".equals(myValue)) {
            Intent mIntent = new Intent(context, InstallActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        } else {
            Intent mIntent = new Intent(context, RepairActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }
    }
}

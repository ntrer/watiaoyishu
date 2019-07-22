package com.watiao.yishuproject.receiver;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.watiao.yishuproject.MainActivity;
import com.watiao.yishuproject.activity.DingDanDetailActivity;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "JIGUANG";
    public static String regId;
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            Bundle bundle = intent.getExtras();

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息(内容为): " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

                // 自定义消息不是通知，默认不会被SDK展示到通知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
                // 自定义消息主要用于应用的内部业务逻辑和特殊展示需求
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//                mNotificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                Notification.Builder builder3=new Notification.Builder(context);
//                if(isAppRunning(context,"com.watiao.yishuproject")){
//                    Intent mainIntent = new Intent(context, MainActivity.class); //打开主界面
//                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Intent detailIntent = new Intent(context, DingDanDetailActivity.class);
//                    Intent[] intents = {mainIntent, detailIntent};
//                    PendingIntent pendingIntent3=PendingIntent.getActivities(context,0,intents,0);
//                    builder3.setContentIntent(pendingIntent3);
//                }
//                else {
//                    Intent launchIntent = new Intent(context, SplashActivity.class); //打开主界面
//                    launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                    Bundle args = new Bundle();
//                    args.putString("name", "打开通知Activity");
//                    launchIntent.putExtra("消息", args);
//                    PendingIntent pendingIntent3=PendingIntent.getActivity(context,0,launchIntent,0);
//                    builder3.setContentIntent(pendingIntent3);
//                }
//                builder3.setSmallIcon(R.mipmap.ic_launcher);
//                builder3.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
//                builder3.setAutoCancel(true);
//                builder3.setContentTitle("悬挂通知");
//
//                Intent XuanIntent=new Intent();
//                XuanIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                XuanIntent.setClass(context,MainActivity.class);
//
//                PendingIntent xuanpengdIntent=PendingIntent.getActivity(context,0,XuanIntent,PendingIntent.FLAG_CANCEL_CURRENT);
//                builder3.setFullScreenIntent(xuanpengdIntent,true);
//                mNotificationManager.notify(2,builder3.build());
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(5000);//五秒后悬挂式通知消失
//                            mNotificationManager.cancel(2);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
                //获取推送消息的方法
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                // 在这里可以自己写代码去定义用户点击后的行为
                if(content != null){
                    if(isAppRunning(context,"com.watiao.yishuproject")){
                        //例如 如果推送内容以【消息】开头 则点击后跳转到消息的Activity 否则跳转到主页面
                        Intent mainIntent = new Intent(context, MainActivity.class); //打开主界面
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Intent detailIntent = new Intent(context, DingDanDetailActivity.class);
                        Intent[] intents = {mainIntent, detailIntent};
                        context.startActivities(intents);

                    }
                    else {
                        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.watiao.yishuproject");
                        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        Bundle args = new Bundle();
                        args.putString("name", "打开通知Activity");
                        launchIntent.putExtra("消息", args);
                        context.startActivity(launchIntent);
                    }
                }else{
                    Log.d("-------","null");
                }

                // 在这里根据 JPushInterface.EXTRA_EXTRA(附加字段) 的内容处理代码，
                // 比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }


}

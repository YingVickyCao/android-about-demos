package com.hades.example.android.app_component.service.unbounservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hades.example.android.R;
import com.hades.utility.android.utils.VersionUtil;
import com.hades.example.java.lib.ThreadUtils;

import static com.hades.example.android.app_component.service.unbounservice.StartServiceTest1Activity.KEY_COUNT;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.Executor;

public class FirstService extends Service {
    private static final String TAG = FirstService.class.getSimpleName();
    private int mNum = 0;
    private int MAX_NUM = 1000;
    //    private int MAX_NUM = 100;
    private boolean mIsForceStop;

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    // Service被创建时回调该方法
    @Override
    public void onCreate() {
//        Log.d(TAG, "onCreate");
        Log.d(TAG, "onCreate: " + ThreadUtils.getThreadInfo());

        setNotificationChannel();
    }

    private void setNotificationChannel() {
        if (VersionUtil.isAndroid8()) {
//            NotificationChannel channel = new NotificationChannel(FIRST_SERVICE_CHANNEL_ID, "Test Notification", NotificationManager.IMPORTANCE_HIGH);
//            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            NotificationChannelCompat channel = new NotificationChannelCompat.Builder(getChannelId(), NotificationManagerCompat.IMPORTANCE_LOW)
                    .setName("First Service")
                    .build();
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.createNotificationChannel(channel);
        }
    }

    private NotificationCompat.Builder createBuilder() {
        return new NotificationCompat
                .Builder(this, getChannelId())
                .setOngoing(true)// let user cannot swipe away from the notification banner
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentTitle("First Service is a counter");
    }

    private void setNotification(int num) {
        NotificationCompat.Builder builder = createBuilder();
        builder.setContentText(String.valueOf(num));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(getNotificationId(), builder.build());
    }

    private int getNotificationId() {
        return getResources().getInteger(R.integer.NOTIFICATION_ID_OF_FIRST_SERVICE);
    }

    private String getChannelId() {
        return getResources().getString(R.string.CHANNEL_ID_OF_FIRST_SERVICE);
    }

    /**
     * Background Execution Limits
     */
    private void startForegroundWhenAndroid8() {
        if (!VersionUtil.isAndroid8()) {
            return;
        }
//        Notification.Builder builder = new Notification
//                .Builder(getApplicationContext(), FIRST_SERVICE_CHANNEL_ID)
//                .setOngoing(true)
//                .setSmallIcon(R.drawable.ic_launcher_round)
//                .setContentTitle("Count");

        NotificationCompat.Builder builder = createBuilder();
        /*
        FIXED_ERROR: java.lang.SecurityException: Permission Denial: startForeground from pid=20342, uid=10228 requires android.permission.FOREGROUND_SERVICE

        Add normal permission:
        targetSdkVersion >= 28
            <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
         */
        startForeground(getNotificationId(), builder.build());
    }

//    // Service被启动时回调该方法
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(TAG, "onStartCommand");
////        LogHelper.printThread(TAG, "onStartCommand");
//        return START_STICKY;
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        Log.d(TAG, "onStartCommand: " + ThreadUtils.getThreadInfo());
        int num = intent.getIntExtra(KEY_COUNT, 0);
        Log.d(TAG, "onStartCommand," + num);
        mNum = num;
        startForegroundWhenAndroid8();
        mockHeavyWorkInThread();
        ////        mockHeavyWorkInUIThread();
//        mockHeavyWorkInThread4CheckStop();
        return START_STICKY;
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
////        Log.d(TAG, "onStartCommand");
//        LogHelper.printThread(TAG, "onStartCommand");

//        return START_STICKY;
//    }

    private void mockHeavyWorkInThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = mNum; i < MAX_NUM; i++) {
                    Log.d(TAG, "mockHeavyWork" + ",i=" + i);
                    Log.d(TAG, "run: " + ThreadUtils.getThreadInfo());
                    try {
                        setNotification(i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();
    }

    private void mockHeavyWorkInThread4CheckStop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = mNum; i < MAX_NUM; i++) {
                    if (mIsForceStop) {
                        Log.d(TAG, "mockHeavyWork,force stop" + ",i=" + i);
                        return;
                    }
                    setNotification(i);
                    Log.d(TAG, "mockHeavyWork" + ",i=" + i);
                    Log.d(TAG, "run: " + ThreadUtils.getThreadInfo());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();
    }

    private void mockHeavyWorkInUIThread() {
        for (int i = mNum; i < MAX_NUM; i++) {
            Log.d(TAG, "mockHeavyWork" + ",i=" + i);
            Log.d(TAG, "mockHeavyWorkInUIThread: " + ThreadUtils.getThreadInfo());
            try {
                setNotification(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    // Service被关闭之前回调
//    @Override
//    public void onDestroy() {
////        Log.d(TAG, "onDestroy");
////        stopSelf();
//        LogHelper.printThread(TAG, "onDestroy");
//    }

    // Service被关闭之前回调
    @Override
    public void onDestroy() {
//        Log.d(TAG, "onDestroy");
//        mIsForceStop = true;
        Log.d(TAG, "onDestroy: " + ThreadUtils.getThreadInfo());

        if (VersionUtil.isAndroid8()) {
            stopForeground(true);
        }
    }
}
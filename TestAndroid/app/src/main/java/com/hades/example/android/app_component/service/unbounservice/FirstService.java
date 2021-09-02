package com.hades.example.android.app_component.service.unbounservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hades.example.android.R;
import com.hades.example.android.lib.utils.VersionUtil;
import com.hades.example.java.lib.ThreadUtils;

import static com.hades.example.android.app_component.service.unbounservice.StartServiceTest1Activity.KEY_COUNT;

public class FirstService extends Service {
    private static final String TAG = FirstService.class.getSimpleName();
    private int mNum = 0;
    private int MAX_NUM = 1000;
    //    private int MAX_NUM = 100;
    private boolean mIsForceStop;
    private final static String FIRST_SERVICE_CHANNEL_ID = "FIRST_SERVICE_CHANNEL_ID";

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

        if (VersionUtil.isAndroid8()) {
            NotificationChannel channel = new NotificationChannel(FIRST_SERVICE_CHANNEL_ID, "Test Notification", NotificationManager.IMPORTANCE_HIGH);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);
        }
    }

    /**
     * Background Execution Limits
     */
    private void startForegroundWhenAndroid8() {
        if (!VersionUtil.isAndroid8()) {
            return;
        }
        Notification.Builder builder = new Notification.Builder(getApplicationContext(), FIRST_SERVICE_CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher_round);
        /*
        FIXED_ERROR: java.lang.SecurityException: Permission Denial: startForeground from pid=20342, uid=10228 requires android.permission.FOREGROUND_SERVICE

        Add normal permission:
        targetSdkVersion >= 28
            <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
         */
        startForeground(1000, builder.build());
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
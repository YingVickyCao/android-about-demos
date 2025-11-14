package com.hades.example.android.app_component.service.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.hades.utility.jvm.ThreadUtils;

public class LocalBoundedService extends Service {
    private static final String TAG = LocalBoundedService.class.getSimpleName();
    private int mCount;
    private boolean mQuit;
    private boolean mIsBounded = false;
    private OnCountCompleteListener listener;

    // 定义onBinder方法所返回的
    private MyBinder binder = new MyBinder();

    // 通过继承Binder来实现IBinder类
    public class MyBinder extends Binder {
        public int getCount() {
            // 获取Service的运行状态：mCount
            return mCount;
        }

        public void setOnCountCompleteListener(OnCountCompleteListener listener) {
            LocalBoundedService.this.listener = listener;
        }

        public boolean isBounded() {
            return mIsBounded;
        }
    }

    public interface OnCountCompleteListener {
        void onCountComplete(int finalCount);

        void onProgress(int count);
    }

    // Service被创建时回调该方法
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: " + ThreadUtils.getThreadInfo());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: " + ThreadUtils.getThreadInfo());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: " + ThreadUtils.getThreadInfo());
        mIsBounded = true;
        super.onRebind(intent);
    }

    // 必须实现的方法，绑定该Service时回调该方法
    @Override
    public IBinder onBind(Intent intent) {
        /**
         * onBind: [thread =2,main]
         * 无论Activity 从主线程还是子线程 bindService(),onBind() 都是运行在主线程。
         */
        Log.d(TAG, "onBind: " + ThreadUtils.getThreadInfo());
        mIsBounded = true;

        // 返回IBinder对象
        // 启动一条线程，动态地修改count状态值
        new Thread() {
            @Override
            public void run() {
                while (!mQuit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    mCount++;
                    Log.d(TAG, "run: count=" + mCount);
                    if (mCount == 10){
                        Log.e(TAG, "run: stopSelf() invoked");
                        stopSelf();
                    }
                    // 计算结束：
                    if (null != listener) {
                        listener.onProgress(mCount);
                    }
                }

                // 计算结束：
                if (null != listener) {
                    listener.onCountComplete(mCount);
                }
            }
        }.start();
        return binder;
    }

    // Service被断开连接时回调该方法
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: " + ThreadUtils.getThreadInfo());
        mIsBounded = false;
        return true;
    }

    // Service被关闭之前回调该方法
    @Override
    public void onDestroy() {
        this.mQuit = true;
        Log.d(TAG, "onDestroy: " + ThreadUtils.getThreadInfo());
    }
}
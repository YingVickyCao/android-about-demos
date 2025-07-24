package com.hades.example.android.b.bound_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hades.example.android.b.IRemoteService;

public class RemoteBoundedService2 extends Service {
    private static final String TAG = "RemoteBoundedService2";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface.
        Log.e(TAG, "onBind: ");
        return binder;
    }

    private final IRemoteService.Stub binder = new IRemoteService.Stub() {
        public int getPid() {
            // getPid: thread name=binder:8580_1,thread id=46
            Log.e(TAG, "getPid: thread name=" + Thread.currentThread().getName() + ",thread id=" + Thread.currentThread().getId());
            return (int) System.currentTimeMillis();
        }

        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {
            // Does nothing.
        }
    };
}

package com.hades.example.android.b.bound_service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.hades.example.android.b.IRemoteService;
import com.hades.example.android.b.LogHelper;

public class RemoteBoundedService2 extends Service {
    private static final String TAG = "RemoteBoundedService2";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
    }

    @SuppressLint("BinderGetCallingInMainThread")
    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface.
        // E  onBind: [thread =2,main]
        /**
         * onBind: [thread =2,main]
         * 无论Activity 从主线程还是子线程 bindService(),onBind() 都是运行在主线程。
         */
        Log.e(TAG, "onBind: " + LogHelper.getThreadInfo());
//        Log.e(TAG, "onBind: Binder.getCallingPid=" + Binder.getCallingPid());
//        Log.e(TAG, "onBind: Binder.getCallingWorkSourceUid=" + Binder.getCallingWorkSourceUid());
//        Log.e(TAG, "onBind: Binder.getCallingUid=" + Binder.getCallingUid());
//        Log.e(TAG, "onBind: Binder.getCallingUserHandle=" + Binder.getCallingUserHandle());
//        Log.e(TAG, "onBind: Binder.getCallingWorkSourceUid=" + Binder.getCallingWorkSourceUid());
//        Log.e(TAG, "onBind: intent=" + intent.toString());
//
//        try {
//            int callingUid = Binder.getCallingUid();
            // TODO:com.hades.example.android.b
//            // 不起作用，因为得不到调用者的package，得到的始终是当前service的package
//            String[] packages = getPackageManager().getPackagesForUid(callingUid);
//
//            if (packages != null && packages.length > 0) {
//                // Check the signature of the first package found for this UID
//                String callingPackageName = packages[0];
//                PackageInfo packageInfo = getPackageManager().getPackageInfo(callingPackageName, PackageManager.GET_SIGNATURES);
//                Signature[] signatures = packageInfo.signatures;
//
//                // Compare with your application's signatures
//                // You'll need to store your app's signatures for comparison
//                if (!hasMatchingSignature(signatures)) {
//                    throw new SecurityException("Caller has an invalid signature.");
//                }
//            } else {
//                throw new SecurityException("Could not retrieve caller package information.");
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            throw new SecurityException("Could not find package for caller.");
//        }
        return binder;
    }

//    private boolean hasMatchingSignature(Signature[] callerSignatures) {
//        // Load your application's signature(s) here
//        // For example, by loading them from resources or hardcoding them (less recommended)
//        // Then compare callerSignatures with your own signatures
//        // This part requires careful implementation
//        return true; // Placeholder
//    }

    private final IRemoteService.Stub binder = new IRemoteService.Stub() {
        public int getPid() {
            //  Stub的方法始终运行在子线程，不用新键子线程来执行任务
            Log.e(TAG, "getPid: " + LogHelper.getThreadInfo());
            // getPid: thread name=binder:8580_1,thread id=46
            return (int) System.currentTimeMillis();
        }

        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {
            // Does nothing.
        }
    };
}

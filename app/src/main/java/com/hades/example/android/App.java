package com.hades.example.android;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        detect_if_new_install_or_updated_version();
    }

    // detect if new install or updated version
    private void detect_if_new_install_or_updated_version() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            long firstInstallTime = packageInfo.firstInstallTime;
            long lastUpdateTime = packageInfo.lastUpdateTime;
            boolean flag = (firstInstallTime == lastUpdateTime);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            Log.d(TAG, "isFirstInstalled: flag=" + flag);
            Log.d(TAG, "isFirstInstalled: firstInstallTime=" + firstInstallTime);
            Log.d(TAG, "isFirstInstalled: lastUpdateTime= " + lastUpdateTime);
            Log.d(TAG, "isFirstInstalled: versionCode= " + versionCode);
            Log.d(TAG, "isFirstInstalled: versionName= " + versionName);
            // cached_versionCode vs versionCode
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
            Log.d(TAG, "isFirstInstalled: ex:" + ex.getMessage());
        }
    }
}

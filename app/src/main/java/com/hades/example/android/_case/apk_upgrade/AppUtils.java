package com.hades.example.android._case.apk_upgrade;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.util.Log;

public class AppUtils {
    private static final String TAG = "AppUtils";

    public static long getVersionCode(Context context) {
        if (null == context) {
            return -1;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return packageInfo.getLongVersionCode();
            } else {
                return packageInfo.versionCode;
            }
        } catch (Exception exception) {
            Log.e(TAG, "getVersionCode: ", exception);
            return -1;
        }
    }
}

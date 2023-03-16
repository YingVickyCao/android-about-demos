package com.hades.example.android.test.apk_upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class AppUtils {
    private static final String TAG = "AppUtils";
    public static final int INSTALL_PACKAGES_REQUEST_CODE = 1000;
    public static final int GET_UNKNOWN_APP_SOURCES = 10012;

    public static File getApkFile(Activity activity) {
        return new File(activity.getCacheDir(), "test-debug.apk");
    }

    public static String getFileMD5(File targetFile) {
        if (null == targetFile || !targetFile.isFile()) {
            return null;
        }

        MessageDigest digest = null;
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            digest = MessageDigest.getInstance("MD5");
            try (FileInputStream in = new FileInputStream(targetFile);) {
                while ((len = in.read(buffer)) != -1) {
                    digest.update(buffer, 0, len);
                }
            }
            // 2 -> 16
            byte[] result = digest.digest();
            BigInteger bigInteger = new BigInteger(1, result);
            return bigInteger.toString(16);
        } catch (Exception exception) {
            Log.e(TAG, "getFileMD5: ", exception);
            return null;
        }
    }

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


    /**
     * (1) Since N, use N FieProvider
     * (2)Since O,request  install permission
     */
    public static void checkInstallApk(Activity activity, File apkFile) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (activity.getPackageManager().canRequestPackageInstalls()) {
                Log.d(TAG, "checkInstallApk: installApk");
                installApk(activity, apkFile);
            } else {
                Log.d(TAG, "checkInstallApk: openInstallPermission");
                openInstallPermission(activity);
            }
        } else {
            installApk(activity, apkFile);
        }
    }

    // jump 设置 - 允许安装未知来源
    private static void openInstallPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, AppUtils.GET_UNKNOWN_APP_SOURCES);
        } else {
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, AppUtils.GET_UNKNOWN_APP_SOURCES);
        }
    }

    public static void installApk(Activity activity, File apkFile) {
        Uri uri;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // ERROR:  android.os.FileUriExposedException: file:///data/user/0/com.hades.example.android/cache/target.apk exposed beyond app through Intent.getData()
        // Reason : Since N, Android don't allow expose file:// to other app
        // Fix : (1) Since N, use N FieProvider
//        uri = Uri.fromFile(apkFile);
        // start
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apkFile);
        }
        // end
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        activity.startActivity(intent);
        // Error: Requesting uid 10155 needs to declare permission android.permission.REQUEST_INSTALL_PACKAGES
        // Reason : Since O,request  install permission
        // Fix:<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
    }
}
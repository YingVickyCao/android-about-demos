package com.hades.example.android._feature.apk_upgrade;

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

public class AppUtils {
    private static final String TAG = "AppUtils";
    public static final int INSTALL_PACKAGES_REQUEST_CODE = 1000;
    public static final int GET_UNKNOWN_APP_SOURCES = 10012;

    public static File getApkFile(Activity activity) {
        return new File(activity.getCacheDir(), "target.apk");
    }

    public static File getApkFile_test(Activity activity) {
        return new File(activity.getCacheDir(), "app-release.apk");
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
                installApk(activity, apkFile);
            } else {
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
        // ERROR:  android.os.FileUriExposedException: file:///data/user/0/com.hades.example.android/cache/target.apk exposed beyond app through Intent.getData()
        // Reason : Since N, Android don't allow expose file:// to other app
        // Fix : (1) Since N, use N FieProvider
//        uri = Uri.fromFile(apkFile);
        // start
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apkFile);
        }
        // end
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo resolveInfo : resInfoList) {
//            activity.grantUriPermission(resolveInfo.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
        activity.startActivity(intent);

        // Error: Requesting uid 10155 needs to declare permission android.permission.REQUEST_INSTALL_PACKAGES
        // Reason : Since O,request  install permission
        // Fix:<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
    }
}

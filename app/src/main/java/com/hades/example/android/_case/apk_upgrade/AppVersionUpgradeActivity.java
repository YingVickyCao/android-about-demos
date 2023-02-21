package com.hades.example.android._case.apk_upgrade;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

import java.io.File;

public class AppVersionUpgradeActivity extends AppCompatActivity {
    private static final String TAG = AppVersionUpgradeActivity.class.getSimpleName();
    /*
        {
            "title":"4.5.0更新啦！",
            "content":"1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
            "url":"http://59.110.162.30/v450_imooc_updater.apk",
            "md5":"14480fc08932105d55b9217c6d2fb90b",
            "versionCode":"450"
        }
     */
    public static String GET_APP_VERSION_URL = "http://59.110.162.30/app_updater_version.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version_upgrade);
        findViewById(R.id.checkVersionUpdate).setOnClickListener(v -> checkVersionUpdate());
    }

    private void checkVersionUpdate() {
        Log.d(TAG, "checkVersionUpdate: ");
        AppVersionUpgrade.getInstance().getNetManager().get(GET_APP_VERSION_URL, new INetCallback() {
            @Override
            public void success(String response) {
                Log.d(TAG, "success: " + response);

                Toast.makeText(AppVersionUpgradeActivity.this, "success. ", Toast.LENGTH_SHORT).show();

                // 1 解析json
                // 2 做版本匹配
                // 3 做版本更新： 如果需要更新，则弹窗
                // 4 点击下载


                AppVersionBean bean = AppVersionBean.parse(response);
                if (!bean.isValid()) {
                    fail();
                    return;
                }
                if (!isNeedUpdateApp(response)) {
                    return;
                }

                try {
                    long versionCode = Long.parseLong(bean.getVersionCode());
                    if (versionCode <= AppUtils.getVersionCode(AppVersionUpgradeActivity.this)) {
                        return;
                    }
                    UpdateVersionDialog.show(AppVersionUpgradeActivity.this, bean);

                } catch (Exception exception) {
                    Log.d(TAG, "success: " + exception);
                    Toast.makeText(AppVersionUpgradeActivity.this, "Version code is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                showVersionUpdateDialog();

//                File targetFile = new File(getCacheDir(), "target.apk");
//                AppVersionUpgrade.getInstance().getNetManager().download(GET_APP_VERSION_URL, targetFile, new INetDownloadCallBack() {
//                    @Override
//                    public void success(File apkFile) {
//                        // 安装代码
//                    }
//
//                    @Override
//                    public void progress(int progress) {
//                        // 更新界面的代码
//                        Log.d(TAG, "progress: " + progress);
//                    }
//
//                    @Override
//                    public void fail() {
//                        Toast.makeText(AppVersionUpgradeActivity.this, "", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public void fail() {
                Log.e(TAG, "fail: ");
                Toast.makeText(AppVersionUpgradeActivity.this, "failed. ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNeedUpdateApp(String versionData) {
        return true;
    }

    private void showVersionUpdateDialog() {

    }

    private static final int GET_UNKNOWN_APP_SOURCES = 10012;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppUtils.INSTALL_PACKAGES_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {  //如果已经有这个权限 则直接安装 否则跳转到授权界面
                    AppUtils.installApk(this, new File("target.apk"));
                } else {
                    Uri packageURI = Uri.parse("package:" + getPackageName());   //获取包名，直接跳转到对应App授权界面
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //8.0 以上系统 强更新授权 界面
        switch (requestCode) {
            case GET_UNKNOWN_APP_SOURCES:
                AppUtils.checkInstallApk(this, new File("target.apk"));
                break;
            default:
                break;
        }

    }
}
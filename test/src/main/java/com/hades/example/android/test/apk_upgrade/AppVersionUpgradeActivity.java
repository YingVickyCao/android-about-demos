package com.hades.example.android.test.apk_upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.test.R;

public class AppVersionUpgradeActivity extends AppCompatActivity {
    private static final String TAG = AppVersionUpgradeActivity.class.getSimpleName();

    /*
         {
           "title": "2.0",
           "content": "1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
           "url": "http://192.168.71.62:8080/app_updater_version/test-debug.apk",
           "md5": "cfcde2bec44e2ce3e4770686768ae3cb",
           "versionCode": "450"
           }
          */

    //    public static String GET_APP_VERSION_URL = "http://192.168.71.62:8080/app_updater_version/version.json";
    public static String GET_APP_VERSION_URL = "http://59.110.162.30/app_updater_version.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version_upgrade);
        findViewById(R.id.checkVersionUpdate).setOnClickListener(v -> checkVersionUpdate());
        findViewById(R.id.installApk).setOnClickListener(v -> test_installApk());
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
            }

            @Override
            public void fail() {
                Log.e(TAG, "fail: ");
                Toast.makeText(AppVersionUpgradeActivity.this, "failed. ", Toast.LENGTH_SHORT).show();
            }
        }, AppVersionUpgradeActivity.this);
    }

    private boolean isNeedUpdateApp(String versionData) {
        return true;
    }

    private void test_installApk() {
        AppUtils.checkInstallApk(this, AppUtils.getApkFile(this));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        //8.0 以上系统 强更新授权 界面
        switch (requestCode) {
            case AppUtils.GET_UNKNOWN_APP_SOURCES:
                AppUtils.installApk(this, AppUtils.getApkFile(this));
                break;
            default:
                break;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    Log.d(TAG, "onRequestPermissionsResult: ");
//        switch (requestCode) {
//            case AppUtils.INSTALL_PACKAGES_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {  //如果已经有这个权限 则直接安装 否则跳转到授权界面
//                    AppUtils.installApk(this, new File("target.apk"));
//                } else {
//                    Uri packageURI = Uri.parse("package:" + getPackageName());   //获取包名，直接跳转到对应App授权界面
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
//                    startActivityForResult(intent, AppUtils.GET_UNKNOWN_APP_SOURCES);
//                }
//                break;
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppVersionUpgrade.getInstance().getNetManager().cancel(AppVersionUpgradeActivity.this);
    }

    // TODO: 2023/2/23 check  downloaded？
    // 1 断点续下，利用多线程下载apk
    // 首先：http请求支持这个功能。返回中RANGE属性指定一个文件的开始字节、终止字节
    // 线程 1 ： 0，100
    // 线程 2 ： 101，200
    // 线程 3 ： 201，300
    // 其次：合并 RandomeAccessFile - seek

    // 2 增量更新
    // apk很大，如何让下载的内容尽可能小
    // apk1 本地
    // apk2 server
    // apk diff apk2 -> patch
    // download patch, apk1 -> apk2
    // 开源的bsdiff算法

    // 1 做一个独立功能的时候，提供一个模块类 AppVersionUpgrade
    // 2 实现模块：网络模块。通过接口屏蔽具体的实现 INetManager
    // 3 https 接口，okhttp证书的配置，okhttp
    // 4 DialogFragment instead of Dialog
    // 5 apk 安装。 N ： FileProvider ， O： Install Permission
    // 6 Android P 访问 http
    // 7 cancel

}
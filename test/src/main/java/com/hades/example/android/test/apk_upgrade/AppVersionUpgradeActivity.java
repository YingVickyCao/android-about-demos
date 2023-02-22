package com.hades.example.android.test.apk_upgrade;

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
    public static String GET_APP_VERSION_URL = "http://192.168.71.62:8080/app_updater_version/version.json";

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
        });
    }

    private boolean isNeedUpdateApp(String versionData) {
        return true;
    }

    private void test_installApk() {
        AppUtils.checkInstallApk(this, AppUtils.getApkFile_test(this));
    }
}
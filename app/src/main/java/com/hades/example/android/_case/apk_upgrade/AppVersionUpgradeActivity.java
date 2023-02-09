package com.hades.example.android._case.apk_upgrade;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
            public void success(String versionData) {
                Log.d(TAG, "success: " + versionData);

                Toast.makeText(AppVersionUpgradeActivity.this, "check app version success. ", Toast.LENGTH_SHORT).show();

                // 1 解析json
                // 2 做版本匹配
                // 3 做版本更新： 如果需要更新，则弹窗
                // 4 点击下载

                if (isNeedUpdateVersion(versionData)) {
                    showVersionUpdateDialog();
                    AppVersionUpgrade.getInstance().getNetManager().download(GET_APP_VERSION_URL, null, new INetDownloadCallBack() {
                        @Override
                        public void success(File apkFile) {

                        }

                        @Override
                        public void progress(int progress) {

                        }

                        @Override
                        public void fail() {

                        }
                    });
                }
            }

            @Override
            public void fail() {
                Log.e(TAG, "fail: ");
                Toast.makeText(AppVersionUpgradeActivity.this, "check app version failed. ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNeedUpdateVersion(String versionData) {
        return false;
    }

    private void showVersionUpdateDialog() {

    }
}
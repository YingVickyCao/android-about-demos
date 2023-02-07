package com.example.receive_app_links;

import android.content.Context;
import android.content.Intent;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
     1 domain approved？Android 12 中，if approved，点击Link直接可以跳转到该app中。

     2 如何 approved domain？
        方法 1 ：Domain has passed Android App Links verification.
      // （1）只要Domain has passed Android App Links verification，就会记录在系统中。更新app，不会清除该记录。
      // （2）删除app，然后重新安装app，verified信息将从系统记录中清除。
     https://developer.android.google.cn/training/app-links/verify-android-applinks#check-app-already-approved
      $ adb shell pm get-app-links --user cur com.example.receive_app_links
        com.example.receive_app_links:
          ID: 514b99af-1376-47ed-9120-c2bb3fd7ad45
          Signatures: [64:FA:15:14:12:B3:C0:77:B2:78:14:B3:1D:4E:1D:C5:55:2F:00:34:49:EF:AC:90:4C:CE:6D:DE:72:A0:7C:24]
          Domain verification state:
            6c04-114-87-148-43.ngrok.io: verified
          User 0:
            Verification link handling allowed: true
            Selection state:
              Disabled:
                6c04-114-87-148-43.ngrok.io

     // 删除app 后，$ adb shell pm get-app-links --user cur com.example.receive_app_links
      com.example.receive_app_links:
        ID: cd29237f-d421-4c4b-8087-7e32f264030b
        Signatures: [64:FA:15:14:12:B3:C0:77:B2:78:14:B3:1D:4E:1D:C5:55:2F:00:34:49:EF:AC:90:4C:CE:6D:DE:72:A0:7C:24]
        Domain verification state:
          6c04-114-87-148-43.ngrok.io: none
        User 0:
          Verification link handling allowed: true
          Selection state:
            Disabled:
              6c04-114-87-148-43.ngrok.io

        方法 2 ：Open by default Dialog ： 户选择open supported links in app by default

     3 、 check approved domain ?
        方法 1 ：adb shell pm get-app-links --user cur com.example.receive_app_links
        方法 2 ：DomainVerificationManager
     */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.checkAppApprovedDomain).setOnClickListener(v -> checkAppApprovedDomain());
        findViewById(R.id.openByDefault).setOnClickListener(v -> openByDefault());
    }

    private void checkAppApprovedDomain() {
        if (!isAtLessAndroid12()) {
            return;
        }
        try {
            Context context = this;
            DomainVerificationManager manager = context.getSystemService(DomainVerificationManager.class);
            DomainVerificationUserState userState = manager.getDomainVerificationUserState(context.getPackageName());

            Map<String, Integer> hostToStateMap = userState.getHostToStateMap();
            // 6c04-114-87-148-43.ngrok.io
            List<String> verifiedDomains = new ArrayList<>();
            List<String> selectedDomains = new ArrayList<>();
            List<String> unapprovedDomains = new ArrayList<>();
            for (String key : hostToStateMap.keySet()) {
                Integer stateValue = hostToStateMap.get(key);
                if (stateValue == DomainVerificationUserState.DOMAIN_STATE_VERIFIED) {
                    // Domain has passed Android App Links verification.
                    // Android 12 中，点击Link直接跳转到该app中
                    verifiedDomains.add(key);
                } else if (stateValue == DomainVerificationUserState.DOMAIN_STATE_SELECTED) {
                    // Domain hasn't passed Android App Links verification, but the user has associated it with an app.
                    selectedDomains.add(key);
                } else {
                    // All other domains.
                    unapprovedDomains.add(key);
                }
            }
        } catch (Exception ex) {
            Log.d(TAG, "onCreate: " + ex.getMessage());
        }
    }

    private boolean isAtLessAndroid12() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S;
    }

    private void openByDefault() {
        Intent intent = new Intent(Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS, Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}

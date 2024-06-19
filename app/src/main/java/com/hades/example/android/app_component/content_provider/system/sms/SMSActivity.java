package com.hades.example.android.app_component.content_provider.system.sms;


import android.Manifest;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.PermissionsTool;

public class SMSActivity extends AppCompatActivity {
    private static final String TAG = SMSActivity.class.getSimpleName();

    private PermissionsTool permissionsTool;
    private View mRoot;
    private SmsContentObserver mSmsContentObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.content_provider_sms);

        checkPermission();
        permissionsTool = new PermissionsTool(this);
        mRoot = findViewById(R.id.root);

        mSmsContentObserver = new SmsContentObserver(this, new Handler());
        // 为content://sms的数据改变注册监听器
        getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, mSmsContentObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mSmsContentObserver) {
            getContentResolver().unregisterContentObserver(mSmsContentObserver);
            mSmsContentObserver = null;
        }
    }

    private void checkPermission() {
        permissionsTool.request(new String[]{Manifest.permission.READ_SMS}, new OnPermissionResultCallback() {
            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(mRoot, "request read sms permission",
                                Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok, view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }

            @Override
            public void onPermissionGranted() {
                Toast.makeText(SMSActivity.this, "permission available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(SMSActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionError(String message) {

            }
        });
    }

}
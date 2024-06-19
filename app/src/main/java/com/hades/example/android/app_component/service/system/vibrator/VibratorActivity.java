package com.hades.example.android.app_component.service.system.vibrator;

import android.Manifest;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.PermissionsTool;

/*
 <!-- 授予程序访问振动器的权限 -->
 <uses-permission android:name="android.permission.VIBRATE"/>
 */
public class VibratorActivity extends AppCompatActivity {
    Vibrator vibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_system_vibrator_service);

        // 获取系统的Vibrator服务
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        requestPermission();
    }

    private void requestPermission() {
        // FIXED_ERROR:java.io.FileNotFoundException: /sdcard/bg004.JPG: open failed: EACCES (Permission denied)
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{Manifest.permission.VIBRATE}, new OnPermissionResultCallback() {

            @Override
            public void onPermissionGranted() {
                Toast.makeText(VibratorActivity.this, "Vibrate permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(VibratorActivity.this, "Vibrate permission denied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionError(String message) {

            }

            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request Vibrate permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, "手机振动", Toast.LENGTH_SHORT).show();
        vibrator.vibrate(500);
        return super.onTouchEvent(event);
    }
}
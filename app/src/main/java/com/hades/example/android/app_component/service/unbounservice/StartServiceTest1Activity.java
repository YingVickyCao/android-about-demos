package com.hades.example.android.app_component.service.unbounservice;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnResultCallback;
import com.hades.utility.permission.PermissionsTool;

import java.util.List;

public class StartServiceTest1Activity extends AppCompatActivity {
    private static final String TAG = StartServiceTest1Activity.class.getSimpleName();
    private int mStartCount = 0;
    public final static String KEY_COUNT = "COUNT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_start_service_test);

        findViewById(R.id.request_notification_permission).setOnClickListener(v -> requestNotificationPermission());
        findViewById(R.id.start).setOnClickListener(v -> start());
        findViewById(R.id.stopRecord).setOnClickListener(v -> stop());
        findViewById(R.id.jump).setOnClickListener(v -> jump());
    }

    private void requestNotificationPermission() {
        PermissionsTool tools = new PermissionsTool(this);
        tools.request(new String[]{Manifest.permission.POST_NOTIFICATIONS}, new OnResultCallback() {

            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Log.d(TAG, "showRationaleContextUI: ");
                callback.ok();
            }

            @Override
            public void granted() {
                Toast.makeText(StartServiceTest1Activity.this, "Granted the notification permission", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void denied() {
                Toast.makeText(StartServiceTest1Activity.this, "Deny the notification permission", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void start() {
        final Intent intent = new Intent(this, FirstService.class);
        Log.d(TAG, "start: startService");
        mStartCount++;
        intent.putExtra(KEY_COUNT, mStartCount);
//        startService(intent);
        startForegroundService(intent);
    }

    private void stop() {
        final Intent intent = new Intent(this, FirstService.class);
        Log.d(TAG, "stop: stopService, context = StartServiceTest1Activity");
        //  When stopService, Intent can be a new one. No need to keep intent when startService();
        stopService(intent);
    }

    //    private void start() {
//        final Intent intent = new Intent(this.getApplicationContext(), FirstService.class);
//        Log.d(TAG, "start: startService, context = getApplicationContext()");
//        startService(intent);
//    }

//    private void stop() {
//        final Intent intent = new Intent(this.getApplicationContext(), FirstService.class);
//        Log.d(TAG, "stop: stopService");
//        //  When stopService, Intent can be a new one. No need to keep intent when startService();
//        stopService(intent);
//    }

    private void jump() {
//        final Intent intent = new Intent(this, StartServiceTest2Activity.class);
        final Intent intent = new Intent(this.getApplicationContext(), FirstService.class);
        Log.d(TAG, "jump: startActivity");
        startActivity(intent);
    }
}
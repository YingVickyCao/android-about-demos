package com.hades.example.android.app_component.assist;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.app_component.assist.brodcast.CrossAppBroadcastReceiver;
import com.hades.example.android.app_component.broadcast.common.CrossBroadcastCommon;

public class CrossBroadcastActivity extends AppCompatActivity {

    private static final String TAG = "CrossBroadcastActivity";
    private CrossAppBroadcastReceiver myReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cross_brodcast_receiver_activity);

        // 初始化 Receiver 和 IntentFilter
        myReceiver = new CrossAppBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(CrossBroadcastCommon.ACTION);

        Log.e(TAG, "onCreate: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(myReceiver, intentFilter, RECEIVER_EXPORTED);
        } else {
            registerReceiver(myReceiver, intentFilter);
        }
        Log.d(TAG, "Receiver registered.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在 Activity 可见时注册 Receiver
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            registerReceiver(myReceiver, intentFilter, RECEIVER_EXPORTED);
//        } else {
//            registerReceiver(myReceiver, intentFilter);
//        }
//        Log.d(TAG, "Receiver registered.");
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        // 在 Activity 不可见时取消注册 Receiver，避免内存泄漏
//        try {
//            unregisterReceiver(myReceiver);
//        } catch (IllegalArgumentException e) {
//            Log.e(TAG, "Unregister receiver: ", e);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在 Activity 不可见时取消注册 Receiver，避免内存泄漏
        try {
            unregisterReceiver(myReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Unregister receiver: ", e);
        }
    }
}
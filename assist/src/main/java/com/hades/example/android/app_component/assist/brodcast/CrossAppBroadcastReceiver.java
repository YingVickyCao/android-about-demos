package com.hades.example.android.app_component.assist.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.hades.example.android.app_component.broadcast.common.CrossBroadcastCommon;

/**
 * 测试发现：
 * 只有静态注册的receiver 可以收到夸app 的自定义广播。
 * 发送自定义广播时，要用显式发送，否则静态注册的receiver收不到。
 * 一个app只能在运行时静态注册receiver和动态注册receiver可以接收少部分系统隐式广播。
 * 一个app只能在运行时只有静态注册receiver可以接收自定义显式广播。
 */
public class CrossAppBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: " + intent.getAction());
        String action = intent.getAction();
        if (CrossBroadcastCommon.ACTION.equals(action)) {
            Log.d(TAG, "Received custom broadcast in Activity!");
            String message = intent.getStringExtra(CrossBroadcastCommon.KEY);
            Toast.makeText(context, "Received in Activity: " + message, Toast.LENGTH_SHORT).show();
        }
    }
}

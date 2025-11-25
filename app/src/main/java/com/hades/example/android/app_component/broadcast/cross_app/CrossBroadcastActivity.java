package com.hades.example.android.app_component.broadcast.cross_app;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.app_component.broadcast.common.CrossBroadcastCommon;

public class CrossBroadcastActivity extends AppCompatActivity {
    private static final String TAG = "CrossBroadcastActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_croass_app);

        findViewById(R.id.sendImplicitBroadcast).setOnClickListener(v -> sendImplicitBroadcast());
        findViewById(R.id.sendExplicitBroadcast).setOnClickListener(v -> sendExplicitBroadcast());
    }

    private void sendImplicitBroadcast() {
        Log.e(TAG, "sendImplicitBroadcast: ");
        // 发送隐式广播，B 不能收到该广播。
        // 一个app接收外部广播时，只能接收少部分系统允许的系统隐式广播。
        Intent intent = new Intent();
        intent.setAction(CrossBroadcastCommon.ACTION);
        intent.putExtra(CrossBroadcastCommon.KEY, "cross app Implicit Broadcast " + System.currentTimeMillis());
        sendBroadcast(intent);
    }

    private void sendExplicitBroadcast() {
        Log.e(TAG, "sendExplicitBroadcast: ");
        // 发送显式广播，B 可以 收到该广播
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(CrossBroadcastCommon.PACKAGE, CrossBroadcastCommon.CLASS)); // 指定目标包名和类名
        intent.setAction(CrossBroadcastCommon.ACTION);
        intent.putExtra(CrossBroadcastCommon.KEY, "cross app Explicit Broadcast " + System.currentTimeMillis());
        sendBroadcast(intent);
    }
}

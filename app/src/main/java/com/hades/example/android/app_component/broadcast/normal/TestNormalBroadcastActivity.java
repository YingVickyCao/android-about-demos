package com.hades.example.android.app_component.broadcast.normal;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.hades.example.android.R;

import static com.hades.example.android.app_component.broadcast.normal.SimpleReceiver.ACTION_ONE;
import static com.hades.example.android.app_component.broadcast.normal.SimpleReceiver.KEY_ONE;


public class TestNormalBroadcastActivity extends Activity {
    private SimpleReceiver mSimpleReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_normal);

        mSimpleReceiver = new SimpleReceiver();

        findViewById(R.id.sendBroadcast).setOnClickListener(v -> sendImplicitBroadcast());
    }

    @Override
    protected void onResume() {
        super.onResume();

        receivingBroadcast();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mSimpleReceiver);
    }

    // QA：Receiving an Implicit broadcast
    private void receivingBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ONE);
        ContextCompat.registerReceiver(this, mSimpleReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);
    }

    private void sendImplicitBroadcast() {
        Intent intent = new Intent();
        intent.setAction(ACTION_ONE);
        // // This makes the intent explicit.
        intent.setPackage(getPackageName());
        intent.putExtra(KEY_ONE, "normal Broadcast " + System.currentTimeMillis());
        sendBroadcast(intent);
    }
}
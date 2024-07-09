package com.hades.example.android.app_component.broadcast.ordered;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.hades.example.android.R;


public class TestOrderedBroadcastActivity extends Activity {

    public static final String ACTION_TWO = "com.hades.example.android.app_component.broadcast.normal.BootCompletedReceiver.TWO";

    private OrderMsgReceiver1 mReceiver1;
    private OrderMsgReceiver2 mReceiver2;
    private OrderMsgReceiver3 mReceiver3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_ordered);

        mReceiver1 = new OrderMsgReceiver1();
        mReceiver2 = new OrderMsgReceiver2();
        mReceiver3 = new OrderMsgReceiver3();

        findViewById(R.id.sendOrderedBroadcast).setOnClickListener(v -> sendOrderedBroadcast());
    }

    @Override
    protected void onResume() {
        super.onResume();


//        test1();
        test2();
    }

    private void test1() {
        // If no action, can not receive anything
        IntentFilter intentFilter = new IntentFilter(ACTION_TWO);
        ContextCompat.registerReceiver(this, mReceiver1, intentFilter, ContextCompat.RECEIVER_NOT_EXPORTED);

        IntentFilter intentFilter2 = new IntentFilter(ACTION_TWO);
        ContextCompat.registerReceiver(this, mReceiver2, intentFilter2, ContextCompat.RECEIVER_NOT_EXPORTED);

        IntentFilter intentFilter3 = new IntentFilter(ACTION_TWO);
        ContextCompat.registerReceiver(this, mReceiver3, intentFilter3, ContextCompat.RECEIVER_NOT_EXPORTED);
    }

    private void test2() {
        // 1 -> 3 -> 2
        IntentFilter intentFilter = new IntentFilter(ACTION_TWO);
        intentFilter.setPriority(3);
        ContextCompat.registerReceiver(this, mReceiver1, intentFilter, ContextCompat.RECEIVER_NOT_EXPORTED);

        IntentFilter intentFilter2 = new IntentFilter(ACTION_TWO);
        intentFilter2.setPriority(1);
        ContextCompat.registerReceiver(this, mReceiver2, intentFilter2, ContextCompat.RECEIVER_NOT_EXPORTED);

        IntentFilter intentFilter3 = new IntentFilter(ACTION_TWO);
        intentFilter3.setPriority(2);
        ContextCompat.registerReceiver(this, mReceiver3, intentFilter3, ContextCompat.RECEIVER_NOT_EXPORTED);

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mReceiver1);
        unregisterReceiver(mReceiver2);
        unregisterReceiver(mReceiver3);
    }

    private void sendOrderedBroadcast() {
        sendOrderedBroadcast(new Intent(ACTION_TWO).setPackage(getPackageName()), null);
    }
}
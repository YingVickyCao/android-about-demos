package com.hades.example.android.app_component.broadcast.ordered;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OrderMsgReceiver3 extends BroadcastReceiver {
    private static final String TAG = OrderMsgReceiver3.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        Toast.makeText(context, "OrderMsgReceiver3", Toast.LENGTH_SHORT).show();
    }
}
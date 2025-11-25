package com.hades.example.android.app_component.broadcast.ordered;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OrderMsgReceiver1 extends BroadcastReceiver {
    private static final String TAG = OrderMsgReceiver1.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // onReceive: com.hades.example.android.app_component.broadcast.normal.BootCompletedReceiver.TWO
        Log.d(TAG, "onReceive: " + intent.getAction());
        // 有序广播：无论使用sendOrderedBroadcast 在哪个线程发送广播，广播的接收始终在主线程。
        // onReceive: thread id= 2,thread name=main
        Log.e(TAG, "onReceive: thread id= " + Thread.currentThread().getId() + ",thread name=" + Thread.currentThread().getName());

        //  1 -> 3 -> 2 => abort = 1
        Toast.makeText(context, "OrderMsgReceiver1", Toast.LENGTH_SHORT).show();
        if (isOrderedBroadcast()) {
            abortBroadcast();
        }
    }
}
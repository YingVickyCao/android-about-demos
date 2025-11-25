package com.hades.example.android.app_component.broadcast._static;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Android 14: 虽然TimezoneChangedReceiver是静态注册，但只有app运行时（可以在后台），才能收到 android.intent.action.TIMEZONE_CHANGED
 */
public class TimezoneChangedReceiver extends BroadcastReceiver {
    private static final String TAG = TimezoneChangedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // BootCompletedReceiver
        Log.e(TAG, "onReceive: " + intent.getAction());
    }
}
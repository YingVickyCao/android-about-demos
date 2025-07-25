package com.hades.example.android._process_and_thread.workmanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hades.example.android.R

class MyLongRunningForegroundService : Service() {

    companion object {
        private const val TAG = "MyForegroundService"
        private const val NOTIFICATION_ID = 1001 // Unique ID for your notification
        const val FOREGROUND_SERVICE_TYPE_KEY = "foreground_service_type" // Key to pass the type

        // Define the types you'll pass from the worker
        const val TYPE_DATA_SYNC = "dataSync"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand received. Flags: $flags, StartId: $startId")
        setNotificationChannel()
        startForegroundWhenAndroid8()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // This is not a bound service
    }

    private fun setNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(getChannelId(), NotificationManagerCompat.IMPORTANCE_LOW)
            .setName(getString(R.string.work_manager_name))
            .build()
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.createNotificationChannel(channel)
    }

    private fun createBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, getChannelId())
            .setOngoing(true) // let user cannot swipe away from the notification banner
            .setSmallIcon(R.drawable.ic_launcher_round)
            .setContentTitle(getResources().getString(R.string.work_manager_notification_title))
    }

    private fun getNotificationId(): Int {
        return getResources().getInteger(R.integer.work_manager_notification_id)
    }

    private fun getChannelId(): String {
        return getResources().getString(R.string.work_manager_notification_channel_id)
    }

    private fun startForegroundWhenAndroid8() {
        val builder = createBuilder()
        startForeground(getNotificationId(), builder.build())
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy: Foreground service destroyed")
        super.onDestroy()
    }
}
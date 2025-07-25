package com.hades.example.android._process_and_thread.workmanager

import android.Manifest
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hades.example.android.R

private const val TAG = "MyLongRunningWorker"

//class MyLongRunningWorker(context: Context, parameters: WorkerParameters) : CoroutineWorker(context, parameters) {
class MyLongRunningWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    var progress: Int = 0

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
        Log.e(TAG, "doWork: ")
        progress = 0
        try {
            // 1. Create an Intent for your foreground service
            val serviceIntent = Intent(applicationContext, MyLongRunningForegroundService::class.java).apply {
                // 2. Add the foreground service type as an extra
                putExtra(MyLongRunningForegroundService.FOREGROUND_SERVICE_TYPE_KEY, MyLongRunningForegroundService.TYPE_DATA_SYNC)
            }
            ContextCompat.startForegroundService(applicationContext, serviceIntent)

            val inputUrl = inputData.getString(KEY_INPUT_URL) ?: return Result.failure()
            val outputFile = inputData.getString(KEY_OUTPUT_FILE_NAME) ?: return Result.failure()
            // Mark the Worker as important
            download(inputUrl, outputFile)
            val stopIntent = Intent(applicationContext, MyLongRunningForegroundService::class.java)
            applicationContext.stopService(stopIntent)
            return Result.success()
        } catch (ex: Exception) {
            val stopIntent = Intent(applicationContext, MyLongRunningForegroundService::class.java)
            applicationContext.stopService(stopIntent)
            return Result.failure()
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun download(inputUrl: String, outputFile: String) {
        // Downloads a file and updates bytes read
        // Calls setForeground() periodically when it needs to update
        // the ongoing Notification
        try {
            Log.e(TAG, "download: $progress")
            while (progress <= 100) {
                Thread.sleep(1_000)
                progress++
                Log.e(TAG, "download: $progress")
                val progress = "Starting Download $progress %"
                setNotification(progress)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun setNotification(progress: String) {
        // Create a Notification channel if necessary
        setNotificationChannel()
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(getNotificationId(), createBuilder(progress).build())
    }

    private fun createBuilder(progress: String): NotificationCompat.Builder {
        val title = applicationContext.getString(R.string.work_manager_notification_title)
        val cancel = applicationContext.getString(R.string.work_manager_cancel_download)
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(getId())

        return NotificationCompat.Builder(applicationContext, getChannelId())
            .setOngoing(true) // let user cannot swipe away from the notification banner
            .setSmallIcon(R.drawable.ic_launcher_round)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_launcher_2)
            .setOngoing(true)
            // TODO:Add the cancel action to the notification which can be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .setContentTitle(applicationContext.getString(R.string.work_manager_notification_title))
    }

    private fun getNotificationId(): Int {
        return applicationContext.getResources().getInteger(R.integer.work_manager_notification_id)
    }

    private fun getChannelId(): String {
        return applicationContext.getResources().getString(R.string.work_manager_notification_channel_id)
    }


    private fun setNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(getChannelId(), NotificationManagerCompat.IMPORTANCE_LOW)
            .setName(applicationContext.getString(R.string.work_manager_name))
            .build()
        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
        notificationManagerCompat.createNotificationChannel(channel)
    }

    companion object {
        const val KEY_INPUT_URL = "KEY_INPUT_URL"
        const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"
    }
}
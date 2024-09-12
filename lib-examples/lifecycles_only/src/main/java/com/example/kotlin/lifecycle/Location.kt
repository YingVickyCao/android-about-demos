package com.example.kotlin.lifecycle

import android.util.Log

private const val TAG = "Location"

internal class Location {

    private var thread: Thread? = null
    private var position: Int = 0
    private var isExist = false
    private var callback: LocationCallback? = null

    fun position(callback: LocationCallback) {
        Log.e(TAG, "position")
        this.callback = callback
    }

    fun start() {
        Log.e(TAG, "start")
        if (isExist) {
            return
        }
        if (null == thread) {
            thread = Thread {
                while (!isExist) {
                    position += 1
                    Log.e(TAG, "update: position=$position")
                    callback?.update(position)
                    Thread.sleep(2_000L)
                }
                Log.e(TAG, "isExist=$isExist")
            }
            thread?.start()
        }
    }

    fun stop() {
        Log.e(TAG, "stop")
        isExist = true
    }

    interface LocationCallback {
        fun update(position: Int);
    }
}
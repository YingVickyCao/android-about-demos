package com.example.kotlin.lifecycle

import android.content.Context

internal class LocationListener(private val context: Context, private val callback: (Location) -> Unit) {
    private var localtion: Location? = null
    fun start() {
        if (localtion == null) {
            localtion = Location()
        }
        // connect to location service
        localtion?.start()
        callback.invoke(localtion!!)
    }

    fun stop() {
        // disconnect from location service
        localtion?.stop()
    }
}
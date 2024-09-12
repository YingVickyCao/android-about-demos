package com.example.kotlin.lifecycle.v2

import android.content.Context
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

private const val TAG = "LocationListener"

internal class LocationListener(
    private val context: Context,
    private val lifecycle: Lifecycle,
    private val callback: (Location) -> Unit
) : DefaultLifecycleObserver {
    private var localtion: Location? = null
    private var enabled: Boolean = false

    init {

        lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "onStart: ")
        if (enabled) {
            // connect
            connect()
        }
    }

    fun enabled() {
        Log.d(TAG, "enabled: ")
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
            connect()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(TAG, "onStop: ")
//        // disconnect if connected
        if (enabled) {
            disconnect()
        }
    }

    private fun connect() {
        // connect
        if (localtion == null) {
            localtion = Location()
        }
        // connect to location service
        localtion?.start()
        callback.invoke(localtion!!)
    }

    private fun disconnect() {
        localtion?.stop()
        localtion = null
    }
}
package com.example.kotlin.lifecycle.v2

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

internal class LocationListener(
    private val context: Context, private val lifecycle: Lifecycle,
    private val callback: (Location) -> Unit
) : DefaultLifecycleObserver {
    private var localtion: Location? = null
    private var enabled: Boolean = false
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

    fun enabled() {
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
            if (localtion == null) {
                localtion = Location()
            }
            // connect to location service
            localtion?.start()
            callback.invoke(localtion!!)
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (enabled) {
            // connect
            if (localtion == null) {
                localtion = Location()
            }
            // connect to location service
            localtion?.start()
            callback.invoke(localtion!!)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        // disconnect if connected
        if (enabled) {
            localtion?.stop()
            localtion = null
        }
    }
}
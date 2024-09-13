package com.example.android.v2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

private const val TAG = "TimestampRepository"

class TimestampRepository(private val network: TimestampNetwork, private val preference: CounterPreference) {
    val timestamp: LiveData<Long> = preference.timestampData

    suspend fun refreshTimestamp() {
        withContext(Dispatchers.IO) {
            try {
                val timestamp: Long = network.getTimestamp()
                if (isActive) {
                    preference.setTimestamp(timestamp)
                    Log.e(TAG, "refreshTimestamp: " + timestamp.absoluteValue)
                } else {
                    throw Exception("Unable to refresh timestamp")
                }
            } catch (ex: Exception) {
                Log.e(TAG, "refreshTimestamp: exception occurred", ex)
                throw Exception("Unable to refresh timestamp")
            }
        }
    }

    suspend fun loadDefaultTimestamp() {
        withContext(Dispatchers.IO) {
            try {
                preference.loadDefaultTimestamp()
            } catch (ex: Exception) {
                Log.e(TAG, "loadDefaultTimestamp: exception occurred", ex)
                throw Exception("Unable to load default timestamp")
            }
        }
    }
}
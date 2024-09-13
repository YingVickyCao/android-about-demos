package com.example.android.v2

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private const val TAG = "TimestampPreference"

interface CounterPreference {
    suspend fun setTimestamp(timestamp: Long)
    suspend fun loadDefaultTimestamp()
    val timestampData: MutableLiveData<Long>
}

class CounterPreferenceImpl(context: Context) : CounterPreference {
    private val key = "timestamp"
    private val pref: SharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)

    private val _timestampData: MutableLiveData<Long> = MutableLiveData<Long>(0L)

    override val timestampData: MutableLiveData<Long>
        get() = _timestampData

    override suspend fun setTimestamp(timestamp: Long) {
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putLong(key, timestamp)
        editor.apply()

//        runBlocking(Dispatchers.Main) { // ok
//            launch {
//                timestampData.value = timestamp
//                Log.e(TAG, "setTimestamp: timestamp=$timestamp")
//                Log.e(TAG, "setTimestamp: timestampData.value=" + timestampData.value)
//            }
//        }
        withContext(Dispatchers.Main) { // ok
            timestampData.value = timestamp
            Log.e(TAG, "setTimestamp: timestamp=$timestamp")
            Log.e(TAG, "setTimestamp: timestampData.value=" + timestampData.value)
        }
    }

    override suspend fun loadDefaultTimestamp() {
        runBlocking(Dispatchers.Main) {
            launch {
                timestampData.value = pref.getLong(key, 0)
                Log.e(TAG, "loadDefaultTimestamp: timestampData.value=" + timestampData.value)
            }
        }
    }
}

private lateinit var instance: CounterPreference

fun getCounterPreference(context: Context): CounterPreference {
    synchronized(CounterPreference::class) {
        // TODO: if (!::instance.isInitialized) {
        if (!::instance.isInitialized) {
            instance = CounterPreferenceImpl(context)
        }
    }
    return instance
}
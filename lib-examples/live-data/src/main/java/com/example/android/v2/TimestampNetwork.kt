package com.example.android.v2

interface TimestampNetwork {
    suspend fun getTimestamp(): Long
}

private val service: TimestampNetwork by lazy {
    object : TimestampNetwork {
        override suspend fun getTimestamp(): Long {
            // Mock the network request
            Thread.sleep(2000)
            return System.currentTimeMillis()
        }
    }
}

fun getTimestampService() = service
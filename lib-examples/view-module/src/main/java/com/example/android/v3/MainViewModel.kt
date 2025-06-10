package com.example.android.v3

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val seconds = MutableLiveData<Int>()
    private val isFinished = MutableLiveData<Boolean>()

    //  post second value
    fun secondsValue(): LiveData<Int> {
        return seconds
    }

    // post is finished value
    fun isFinishedValue(): LiveData<Boolean> {
        return isFinished
    }

    fun startCounter() {
        // set this value to disable the start button
        isFinished.value = false
        object : CountDownTimer(5000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished / 1000
                // set second value
                seconds.value = time.toInt()
            }

            override fun onFinish() {
                // set the counter is finished to enable the start button
                isFinished.value = true
            }
        }.start()

//        for (i in 1..5) {
//            Thread(Runnable {
//                Thread.sleep(1000)
//                // set second value
//                seconds.value = i // java.lang.IllegalStateException: Cannot invoke setValue on a background thread
//            }).start()
//        }
        // set the counter is finished to enable the start button
        isFinished.value = true

    }
}
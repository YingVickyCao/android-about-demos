package com.example.android.v1

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CounterViewModule : ViewModel() {
    private val count: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    private val finished: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CounterViewModule() as T
            }
        }
    }


    fun count(): LiveData<Int> {
        return count
    }

    fun finished(): LiveData<Boolean> {
        return finished
    }

    fun plusCount() {
        count.value = count.value?.minus(100)
    }

    fun startCounter() {
        object : CountDownTimer(500000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished / 100
                count.value = time.toInt()
            }

            override fun onFinish() {
                finished.value = true
            }

        }.start()
    }
}
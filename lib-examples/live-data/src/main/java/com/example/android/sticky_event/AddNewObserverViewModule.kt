package com.example.android.sticky_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.io.Serializable

// https://developer.android.google.cn/topic/libraries/architecture/viewmodel/viewmodel-savedstate?hl=zh-cn#direct
class AddNewObserverViewModule(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _n1: MutableLiveData<Long> = MutableLiveData()
    val n1: LiveData<Long> = _n1

    fun increase() {
        val v = System.currentTimeMillis()
        _n1.value = v
    }
}
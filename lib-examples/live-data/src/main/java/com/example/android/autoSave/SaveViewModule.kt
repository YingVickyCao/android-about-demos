package com.example.android.autoSave

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.io.Serializable

// https://developer.android.google.cn/topic/libraries/architecture/viewmodel/viewmodel-savedstate?hl=zh-cn#direct
class SaveViewModule(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // 转动屏幕后，可以自动保存
    private val k1 = "K1"
    private val _n1: MutableLiveData<Long> = MutableLiveData(savedStateHandle[k1] ?: 0)
    val n1: LiveData<Long> = _n1

    private val k2 = "K2"
    private val _stu: MutableLiveData<Stu> = MutableLiveData(savedStateHandle[k2] ?: Stu("Stu " + System.currentTimeMillis()))
    val stu: LiveData<Stu> = _stu

    fun increase() {
        val v = System.currentTimeMillis()
        _n1.value = v
        savedStateHandle[k1] = v
    }

    fun setStu() {
        val v = Stu("Stu " + System.currentTimeMillis())
        _stu.value = v
        savedStateHandle[k2] = v
    }
}

class Stu(val name: String) : Serializable {
}
package com.example.android.v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CountViewModule : ViewModel() {
    var count = 0

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CountViewModule() as T
            }
        }
    }
}
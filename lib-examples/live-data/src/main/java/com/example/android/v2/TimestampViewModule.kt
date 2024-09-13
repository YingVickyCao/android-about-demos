package com.example.android.v2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private const val TAG = "TimestampViewModule"
class TimestampViewModule(private val repository: TimestampRepository) : ViewModel() {

    val timestamp = repository.timestamp

    // Show toast
    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    // Show a loading if true
    private val _loader = MutableLiveData<Boolean>(false)
    val loader: LiveData<Boolean>
        get() = _loader

    fun buttonClicked() {
        refreshTimestamp()
    }

    private fun refreshTimestamp() {
        loadTimestamp {
            repository.refreshTimestamp()
        }
    }

    private fun loadTimestamp(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _loader.value = true
                block()
                Log.e(TAG, "loadTimestamp: " + timestamp.value)
            } catch (error: Exception) {
                _toast.value = error.message
            } finally {
                _loader.value = false
            }
        }
    }
}

class TimestampViewModuleFactory(private val repository: TimestampRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TimestampViewModule(repository) as T
    }
}
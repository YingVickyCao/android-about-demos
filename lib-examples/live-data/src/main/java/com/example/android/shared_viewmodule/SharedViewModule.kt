package com.example.android.shared_viewmodule

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

//class SharedViewModuleFactory(private val saveState: SavedStateHandle) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//        if (modelClass.isAssignableFrom(SharedViewModule::class.java)) {
//            val saved = extras.createSavedStateHandle()
//            return SharedViewModule(saveState) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
//}

// 只有一个参数SavedStateHandle， 不需要用ViewModelProvider.Factory
class SharedViewModule(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _shared: MutableLiveData<Long> = MutableLiveData()
    val shared: LiveData<Long> = _shared

    // 用于解决粘性事件：用于发送一次性事件，如提示信息
    private val _one_time_event_shared: MutableLiveData<SingleEvent<Long>> = MutableLiveData()
    val one_time_event_shared: LiveData<SingleEvent<Long>> = _one_time_event_shared
    val key_one_time_event_shared = "KEY_ONE_TIME_EVENT_SHARED"

    // 用于解决粘性事件：用于发送一次性事件，如提示信息
    private val _one_time_event_shared2: SingleMutableLiveData<Long> = SingleMutableLiveData()
    val one_time_event_shared2: LiveData<Long> = _one_time_event_shared2
    val key_one_time_event_shared2 = "KEY_ONE_TIME_EVENT_SHARED2"

    fun restore() {
        // 转动屏幕后，数据也能记住。
        // 在 ViewModel 初始化时，尝试从 SavedStateHandle 恢复数据
        if (savedStateHandle.contains(key_one_time_event_shared)) {
            val shared = savedStateHandle.get<Long>(key_one_time_event_shared)
            shared?.let {
                _one_time_event_shared.postValue(SingleEvent<Long>(it))
            }
        }
    }

    fun updateTimestamp() {
//        updateTimestamp1()
//        updateTimestamp2()
        updateTimestamp2_2()
    }


    fun updateTimestamp1() {
        /**
         * Fragment A 和 Fragment B 共享 SharedViewModule。 当 Fragment A 更新了 shared后，希望 Fragment A 能收到新数据，Fragment B 不能收到新数据
         * 测试：
         * 错误的实现：Fragment A 更新了 shared，Fragment A 能收到新数据，Fragment B 也能收到新数据
         */
        viewModelScope.launch(Dispatchers.IO) {
            _shared.postValue(System.currentTimeMillis())
        }
    }

    fun updateTimestamp2() {
        /**
         * Fragment A 和 Fragment B 共享 SharedViewModule。 当 Fragment A 更新了 shared后，希望 Fragment A 能收到新数据，Fragment B 不能收到新数据
         * 测试：
         * 正确的实现：Fragment A 更新了 shared，Fragment A 能收到新数据，Fragment B 不能收到新数据
         */
        viewModelScope.launch(Dispatchers.IO) {
            val value = System.currentTimeMillis()
            _one_time_event_shared.postValue(SingleEvent<Long>(value))

            // 系统销毁activity后，数据也能被恢复
            savedStateHandle[key_one_time_event_shared] = value
        }
    }

    fun updateTimestamp2_2() {
        /**
         * Fragment A 和 Fragment B 共享 SharedViewModule。 当 Fragment A 更新了 shared后，希望 Fragment A 能收到新数据，Fragment B 不能收到新数据
         * 测试：
         * 正确的实现：Fragment A 更新了 shared，Fragment A 能收到新数据，Fragment B 不能收到新数据
         */
        viewModelScope.launch(Dispatchers.IO) {
            val value = System.currentTimeMillis()
            _one_time_event_shared2.postValue(value)

            // 系统销毁activity后，数据也能被恢复
            savedStateHandle[key_one_time_event_shared2] = value
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}

class SingleEvent<T>(val content: T) {
    var consumed: AtomicBoolean = AtomicBoolean() // 标记事件是否已被处理

    // callback lambda ： 只有在事件未被消耗时才会执行
    fun consume(callback: (T) -> Unit) { // 消耗事件的方法
        if (!consumed.get()) {
            consumed.getAndSet(true) // 标记为已处理
            callback(content) // 执行实际的处理逻辑
        }
    }
}

class SingleMutableLiveData<T> : MutableLiveData<T>() {
    //  标记数据是否已被处理
    var consumed: AtomicBoolean = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, object : Observer<T> {
            override fun onChanged(value: T) {
                //  只有当值未被处理时，才调用onChanged通知观察者,并标记为已经处理
                if (!consumed.get()) {
                    consumed.getAndSet(true)
                    observer.onChanged(value)
                }
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        // 当值更新时，标记为没有被处理
        consumed.getAndSet(false)
        super.setValue(value)
    }
}
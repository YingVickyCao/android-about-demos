package com.example.android.repeat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepeatUpdateViewModule : ViewModel() {
    private val _count: MutableLiveData<Int> = MutableLiveData(0)
    val count: LiveData<Int> = _count

    val subject: BehaviorSubject<Int> = BehaviorSubject.create()
    val disposables = CompositeDisposable()

    private val _shared: MutableLiveData<Long> = MutableLiveData(0)
    val shared: LiveData<Long> = _shared

    companion object Companion {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RepeatUpdateViewModule() as T
            }
        }
    }


    fun updateByFastRepeat() {
        updateByFastRepeat4()
    }

    fun updateByFastRepeat1() {
        viewModelScope.launch(Dispatchers.Main) {

            /**
             * 在UI可见时，调用
             * 	liveData.postValue(1);
             * 	liveData.setValue(2);
             * 	会先收到2，后收到1
             *
             * log:
            E  observe: 2
            E  observe: 1

            在UI不可见时，调用
            liveData.postValue(1);
            liveData.setValue(2);
            当UI可见，只会收到1，因为setValue先执行，之后被postValue更新掉
             *
             */
            _count.postValue(1)
            _count.value = 2
        }
    }


    fun updateByFastRepeat2() {
        viewModelScope.launch(Dispatchers.Main) {
            /**
             * 在UI可见时，调用
             * liveData.setValue(1)
             * liveData.setValue(2)
             * liveData.setValue(3)
             * liveData.setValue(4)
             * liveData.setValue(5)
             * 会按顺序收到1，2，3，4，5
             *
             * log:
            E  observe: 1
            E  observe: 2
            E  observe: 3
            E  observe: 4
            E  observe: 5

             * 在UI不可见时，调用
             * liveData.setValue(1)
             * liveData.setValue(2)
             * liveData.setValue(3)
             * liveData.setValue(4)
             * liveData.setValue(5)
             * 当UI可见之后，只会收到5
             *
             * log:
             * E  observe: 5
             *
             */
            for (i in 1..5) {
                _count.value = i
            }
        }
    }

    fun updateByFastRepeat3() {
        viewModelScope.launch(Dispatchers.IO) {
            /**
             *  在UI可见时，调用
             * liveData.postValue(1)
             * liveData.postValue(2)
             * liveData.postValue(3)
             * liveData.postValue(4)
             * liveData.postValue(5)
             * 只会收到5
             *
             * log:
            E  observe: 5

             *  在UI不可见时，调用
             * liveData.postValue(1)
             * liveData.postValue(2)
             * liveData.postValue(3)
             * liveData.postValue(4)
             * liveData.postValue(5)
             * 当UI可见之后，只会收到5
             */
            for (i in 1..5) {
                _count.postValue(i)
            }
        }
    }

    fun updateByFastRepeat4() {
        viewModelScope.launch(Dispatchers.IO) {
            /**
             * 不管UI可见不可见，只要发送，就能接收。
             * log:
             * E  subscribe: 1
             *  E  subscribe: 2
             *  E  subscribe: 3
             *  E  subscribe: 4
             *  E  subscribe: 5
             */
            for (i in 1..5) {
//                _count.postValue(i)
                subject.onNext(i)
            }
        }
    }

    fun updateTimestamp() {
        viewModelScope.launch(Dispatchers.IO) {
            _shared.postValue(System.currentTimeMillis())
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear() // 取消所有订阅
    }
}
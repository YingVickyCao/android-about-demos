package com.hades.example.android._process_and_thread.replace_asynctask.coroutine

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

private const val TAG = "CoroutineAsyncTask"

abstract class CoroutineAsyncTask<Params, Progress, Result> protected constructor() {
    private val mCancelled = AtomicBoolean()
    private val mainScope = CoroutineScope(Dispatchers.Main)

    @MainThread
    protected open fun onPreExecute() {
    }

    @WorkerThread
    protected abstract fun doInBackground(vararg params: Params?): Result

    @MainThread
    protected open fun onPostExecute(result: Result) {
    }


    @MainThread
    protected open fun onProgressUpdate(vararg values: Progress) {
    }

    fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        mCancelled.set(true)
        if (mayInterruptIfRunning) {
            if (!mainScope.isActive) {
                mainScope.cancel()
            }
        }
        return mCancelled.get()
    }

    @MainThread
    protected open fun onCancelled(result: Result) {
        Log.d(TAG, "onCancelled: ")
        onCancelled()
    }

    @MainThread
    protected open fun onCancelled() {
    }

    @MainThread
    protected open fun onError(exception: Exception) {
    }

    val isCancelled: Boolean
        get() = mCancelled.get() || !mainScope.isActive


    @MainThread
    protected open fun execute(vararg params: Params) {
        executeParam(*params)
    }

    private fun executeParam(vararg params: Params?) {
        mainScope.launch {
            if (!isCancelled) {
                onPreExecute()
            }
            launch(Dispatchers.IO) {
                try {
                    val result = doInBackground(*params)
                    if (isCancelled) {
                        launch(Dispatchers.Main) {
                            onCancelled(result)
                        }
                    } else {
                        if (!isCancelled) {
                            launch(Dispatchers.Main) {
                                onPostExecute(result)
                            }
                        }
                    }
                } catch (ex: Exception) {
                    launch(Dispatchers.Main) {
                        onError(ex)
                        cancel(true)
                    }
                }
            }

        }
    }

    //    @MainThread
    //    public static void execute(Runnable runnable) {
    //        sDefaultExecutor.execute(runnable);
    //    }

    //    @MainThread
    //    public static void execute() {
    //        execute(null);
    //    }
    @MainThread
    suspend fun execute() {
        executeParam(null)
    }

    //    @MainThread
    //    public final AsyncTaskExecutorService<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
    //        return this;
    //    }
    @SafeVarargs
    @WorkerThread
    protected fun publishProgress(vararg values: Progress) {
        Log.d(TAG, "publishProgress: " + ",thread id=" + Thread.currentThread().id + ",thread name=" + Thread.currentThread().name)
        if (isCancelled) {
            return
        }
        mainScope.launch {
            onProgressUpdate(*values)
        }
    }
}

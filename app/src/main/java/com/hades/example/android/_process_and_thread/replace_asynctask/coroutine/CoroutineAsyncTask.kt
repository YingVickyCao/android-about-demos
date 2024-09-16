package com.hades.example.android._process_and_thread.replace_asynctask.coroutine

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.log

private const val TAG = "CoroutineAsyncTask"

abstract class CoroutineAsyncTask<Params, Progress, Result> protected constructor() {
    private val mCancelled = AtomicBoolean()
    private var mainScope: CoroutineScope? = null
    private var ioJob: Job? = null
    private var mainLunchJob1: Job? = null
    private var mainLunchJob2: Job? = null
    private var mainLunchJob3: Job? = null
    private var mainLunchJob4: Job? = null
    private var mainLunchJob5: Job? = null

    @MainThread
    protected open fun onPreExecute() {
        Log.e(TAG, "onPreExecute: ")
    }

    @WorkerThread
    protected abstract fun doInBackground(vararg params: Params?): Result

    @MainThread
    protected open fun onPostExecute(result: Result) {
        Log.e(TAG, "onPostExecute: ")
    }


    @MainThread
    protected open fun onProgressUpdate(vararg values: Progress) {
        Log.e(TAG, "onProgressUpdate: ")
    }


    private fun cancel(mayInterruptIfRunning: Boolean, cancelByClient: Boolean): Boolean {
        if (mCancelled.get()) {
            Log.e(TAG, "cancel: [0]" + mCancelled.get())
            return mCancelled.get()
        }
        mCancelled.set(true)
        if (cancelByClient) {
            mainScope?.launch {
                interrupt(mayInterruptIfRunning)
                onCancelled()
                Log.e(TAG, "cancel: [1]" + mCancelled.get())
                return@launch
            }
            Log.e(TAG, "cancel: [2]" + mCancelled.get())
            return mCancelled.get()
        } else {
            interrupt(mayInterruptIfRunning)
            Log.e(TAG, "cancel: [3]" + mCancelled.get())
            return mCancelled.get()
        }
    }

    private fun interrupt(mayInterruptIfRunning: Boolean) {
        if (mayInterruptIfRunning) {
            mainScope?.let {
                if (it.isActive) {
                    Log.e(TAG, "cancel: mainScope is cancelled ")
                    it.cancel()
                }
            }
            mainScope = null
        }
    }

    fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        Log.e(TAG, "cancel: ")
        cancel(mayInterruptIfRunning, true)
        return mCancelled.get()
    }

    @MainThread
    protected open fun onCancelled(result: Result) {
        Log.e(TAG, "onCancelled: ")
        onCancelled()
    }

    @MainThread
    protected open fun onCancelled() {
        Log.e(TAG, "onCancelled: ")
    }

    @MainThread
    protected open fun onError(exception: Exception) {
        Log.e(TAG, "onError: ")
    }

    val isCancelled: Boolean
        get() = mCancelled.get() || (null != mainScope && !(mainScope?.isActive)!!)


    @MainThread
    protected open fun execute(vararg params: Params) {
        executeParam(*params)
    }

    private fun executeParam(vararg params: Params?) {
        Log.e(TAG, "executeParam: ")
        if (mainScope == null) {
            mainScope = CoroutineScope(Dispatchers.Main)
        }

        mainLunchJob5 = mainScope?.launch {
            if (!isCancelled) {
                onPreExecute()
            }
            ioJob = launch(Dispatchers.IO) {
                try {
                    val result = doInBackground(*params)
                    if (isCancelled) {
                        Log.e(TAG, "executeParam: isCancelled=true")
                        mainLunchJob1 = launch(Dispatchers.Main) {
                            Log.e(TAG, "executeParam: invoke onCancelled")
                            onCancelled(result)
                        }
                    } else {
                        if (!isCancelled) {
                            Log.e(TAG, "executeParam: isCancelled=false")
                            mainLunchJob2 = launch(Dispatchers.Main) {
                                Log.e(TAG, "executeParam: invoke onPostExecute")
                                onPostExecute(result)
                                cancel(true, false)
                            }
                        }
                    }
                } catch (ex: Exception) {
                    mainLunchJob3 = launch(Dispatchers.Main) {
                        onError(ex)
                        cancel(true, false)
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
        if (isCancelled) {
            return
        }
        Log.e(TAG, "publishProgress: " + ",thread id=" + Thread.currentThread().id + ",thread name=" + Thread.currentThread().name)
        mainLunchJob4 = mainScope?.launch {
            onProgressUpdate(*values)
        }
    }
}

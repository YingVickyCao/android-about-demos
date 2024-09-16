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
    private val mainScope = CoroutineScope(Dispatchers.Main)
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

    fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        mCancelled.set(true)
        Log.e(TAG, "cancel[1]:mainScope,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[1]:ioJob,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[1]:mainLunchJob1,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[1]:mainLunchJob1,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[1]:mainLunchJob2,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[1]:mainLunchJob3,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[1]:mainLunchJob4,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[1]:mainLunchJob5,isActive=" + mainScope.isActive)
        if (mayInterruptIfRunning) {
            if (mainScope.isActive) {
                Log.e(TAG, "cancel: ")
                mainScope.cancel()
            }
        }
        Log.e(TAG, "cancel[2]:mainScope,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[2]:ioJob,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[2]:mainLunchJob1,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[2]:mainLunchJob1,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[2]:mainLunchJob2,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[2]:mainLunchJob3,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[2]:mainLunchJob4,isActive=" + mainScope.isActive)
        Log.e(TAG, "cancel[2]:mainLunchJob5,isActive=" + mainScope.isActive)
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
        get() = mCancelled.get() || !mainScope.isActive


    @MainThread
    protected open fun execute(vararg params: Params) {
        executeParam(*params)
    }

    private fun executeParam(vararg params: Params?) {
        Log.e(TAG, "executeParam: ")
        mainLunchJob5 = mainScope.launch {
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
                                cancel(true)
                            }
                        }
                    }
                } catch (ex: Exception) {
                    mainLunchJob3 = launch(Dispatchers.Main) {
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
        Log.e(TAG, "publishProgress: " + ",thread id=" + Thread.currentThread().id + ",thread name=" + Thread.currentThread().name)
        if (isCancelled) {
            return
        }
        mainLunchJob4 = mainScope.launch {
            onProgressUpdate(*values)
        }
    }
}

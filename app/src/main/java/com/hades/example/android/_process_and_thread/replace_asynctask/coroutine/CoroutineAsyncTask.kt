package com.hades.example.android._process_and_thread.replace_asynctask.coroutine

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

abstract class CoroutineAsyncTask<Params, Progress, Result> protected constructor() {
    // ExecutorService / Executor : ok
    //        executor = Executors.newSingleThreadExecutor(r -> {
//            Thread thread = new Thread(r);
//            thread.setDaemon(true); //
//            return thread;
//        });
    val executor: ExecutorService? = Executors.newSingleThreadExecutor()
    var handler: Handler? = null
        get() {
            if (null == field) {
                synchronized(CoroutineAsyncTask::class.java) {
                    field = Handler(Looper.getMainLooper())
                }
            }
            return field
        }
        private set
    private val mCancelled = AtomicBoolean()

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
            if (null != executor) {
                if (!executor.isShutdown && !executor.isTerminated) {
                    executor.shutdown()
                }
            }
        }
        return mCancelled.get()
    }

    @MainThread
    protected open fun onCancelled(result: Result) {
        onCancelled()
    }

    @MainThread
    protected open fun onCancelled() {
    }

    val isCancelled: Boolean
        get() = mCancelled.get() || null == executor || executor.isTerminated || executor.isShutdown

    //    public final AsyncTaskExecutorService<Params, Progress, Result> execute(Params... params) {
    //        return executeOnExecutor(sDefaultExecutor, params);
    //    }
    @SafeVarargs
    @MainThread
    fun execute(vararg params: Params) {
        handler!!.post {
            if (!isCancelled) {
                onPreExecute()
            }
            executor!!.execute {
                val result = doInBackground(*params)
                if (isCancelled) {
                    handler!!.post { onCancelled(result) }
                } else {
                    if (!isCancelled) {
                        handler!!.post { onPostExecute(result) }
                    }
                }
            }
        }
    }

    //    @MainThread
    //    public static void execute(Runnable runnable) {
    //        sDefaultExecutor.execute(runnable);
    //    }
    @MainThread
    fun execute(runnable: Runnable?) {
        executor?.execute(runnable)
    }


    //    @MainThread
    //    public static void execute() {
    //        execute(null);
    //    }
    @MainThread
    fun execute() {
        handler!!.post {
            if (!isCancelled) {
                onPreExecute()
            }
            executor!!.execute {
                val result = doInBackground(null)
                if (isCancelled) {
                    handler!!.post { onCancelled(result) }
                } else {
                    if (!isCancelled) {
                        handler!!.post { onPostExecute(result) }
                    }
                }
            }
        }
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
        handler!!.post { onProgressUpdate(*values) }
    }

    companion object {
        private const val TAG = "AsyncTaskExecutorService"
    }
}

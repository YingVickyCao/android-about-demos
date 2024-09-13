package com.hades.example.android._process_and_thread.replace_asynctask.concurrent;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AsyncTaskExecutorService<Params, Progress, Result> {
    // ExecutorService / Executor : ok
    private ExecutorService executor;
    private Handler handler;
    private final AtomicBoolean mCancelled = new AtomicBoolean();


    protected AsyncTaskExecutorService() {
        executor = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true); //
            return thread;
        });
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public Handler getHandler() {
        if (null == handler) {
            synchronized (AsyncTaskExecutorService.class) {
                handler = new Handler(Looper.getMainLooper());
            }
        }
        return handler;
    }

    @MainThread
    protected void onPreExecute() {
    }

    @WorkerThread
    protected abstract Result doInBackground(Params... params);

    @MainThread
    protected void onPostExecute(Result result) {
    }


    @MainThread
    protected void onProgressUpdate(Progress... values) {
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        mCancelled.set(true);
        if (mayInterruptIfRunning) {
            if (null != executor) {
                if (!executor.isShutdown() && !executor.isTerminated()) {
                    executor.shutdown();
                }
            }
        }
        return mCancelled.get();
    }

    @MainThread
    protected void onCancelled(Result result) {

    }

    @MainThread
    protected void onCancelled() {
    }


    public final boolean isCancelled() {
        return mCancelled.get() || null == executor || executor.isTerminated() || executor.isShutdown();
    }

    @MainThread
//    public final AsyncTaskExecutorService<Params, Progress, Result> execute(Params... params) {
//        return executeOnExecutor(sDefaultExecutor, params);
//    }

    public final void execute(Params... params) {
        getHandler().post(() -> {
            if (!isCancelled()) {
                onPreExecute();
            }
            executor.execute(() -> {
                Result result = doInBackground(params);
                if (!isCancelled()) {
                    getHandler().post(() -> onPostExecute(result));
                }
            });
        });
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
    public void execute() {
        execute(null);
    }

//    @MainThread
//    public final AsyncTaskExecutorService<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
//
//    }

    @WorkerThread
    protected final void publishProgress(@NonNull Progress... values) {
        if (isCancelled()) {
            return;
        }
        getHandler().post(() -> onProgressUpdate(values));
    }

    public void shutDown() {
        if (executor != null) {
            mCancelled.set(true);
            executor.shutdown();
        }
    }
}

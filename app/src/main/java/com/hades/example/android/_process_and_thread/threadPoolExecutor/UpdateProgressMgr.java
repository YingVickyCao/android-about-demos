package com.hades.example.android._process_and_thread.threadPoolExecutor;

import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class UpdateProgressMgr {
    private static final String TAG = UpdateProgressMgr.class.getSimpleName();
    private ScheduledExecutorService mExecutorService;
    private ScheduledFuture mFuture;
    private Runnable mRunnable;

    private IProgressListener mProgressListener;
    private final int PERIOD_IN_SECONDS = 1;

    public UpdateProgressMgr() {
    }

    public void setProgressListener(IProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    void init(IProgressListener progressListener) {
        setProgressListener(progressListener);

        if (null == mExecutorService) {
            mExecutorService = Executors.newSingleThreadScheduledExecutor();
            Log.d(TAG, "start: ExecutorService@" + mExecutorService.hashCode());
        }

        if (null == mRunnable) {
            mRunnable = () -> {
                if (null != mProgressListener) {
                    mProgressListener.update();
                }
            };
        }
    }

    public void start() {
        if (null == mProgressListener || null == mExecutorService) {
            return;
        }
        checkFuture();
    }

    private void checkFuture() {
        if (null == mFuture || mFuture.isCancelled()) {
            mFuture = mExecutorService.scheduleAtFixedRate(mRunnable, 0, PERIOD_IN_SECONDS, TimeUnit.SECONDS);
        }
    }

    public void stop() {
        destroyFuture();
    }

    void destroy() {
        destroyFuture();
        destroyExecutorService();
        destroyListener();
        destroyRunnable();
    }

    private void destroyRunnable() {
        if (null != mRunnable) {
            mRunnable = null;
            Log.d(TAG, "destroy: Runnable-[1] set null");
        } else {
            Log.d(TAG, "destroy: Runnable-[2] is already null");
        }
    }

    private void destroyExecutorService() {
        if (null != mExecutorService) {
            // TODO: 2019/3/7 Not work: not destroy, I don not know why.
//            mExecutorService.shutdownNow();
            if (!mExecutorService.isShutdown()) {
                mExecutorService.shutdownNow();
                Log.d(TAG, "destroy: ExecutorService-[1] shutdownNow && null");
            } else {
                Log.d(TAG, "destroy: ExecutorService-[2] set null");
            }
            mExecutorService = null;
        } else {
            Log.d(TAG, "destroy: ExecutorService-[3] is already null");
        }
    }

    private void destroyListener() {
        if (null != mProgressListener) {
            mProgressListener = null;
        }
    }

    private void destroyFuture() {
        if (null != mFuture) {
            if (!mFuture.isCancelled()) {
                mFuture.cancel(true);
                Log.d(TAG, "destroy: Future-[1] canceled && null");
            } else {
                Log.d(TAG, "destroy: Future-[2] set null");
            }
            mFuture = null;

        } else {
            Log.d(TAG, "destroy: Future [3] is already null");
        }
    }
}
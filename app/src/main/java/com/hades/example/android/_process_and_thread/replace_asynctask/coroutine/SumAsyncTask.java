package com.hades.example.android._process_and_thread.replace_asynctask.coroutine;

import android.util.Log;

import androidx.annotation.NonNull;

public class SumAsyncTask extends CoroutineAsyncTask<Integer, Integer, Long> {
    private static final String TAG = SumAsyncTask.class.getSimpleName();

    private ISum mISum;

    SumAsyncTask() {
    }

    void setISum(ISum sum) {
        mISum = sum;
    }

//    @Override
//    protected void execute(Integer... integers) {
//        super.execute(integers);
//    }

    @Override
    protected void onPreExecute() {// UI Thread
        super.onPreExecute();
        if (null != mISum) {
            mISum.onPreExecute("Start work");
        }
        Log.d(TAG, "onPreExecute: thread id=" + Thread.currentThread().getId() + ",thread name=" + Thread.currentThread().getName());
    }

    @Override
    protected Long doInBackground(Integer... params) {//work thread
        Log.e(TAG, "doInBackground: ");
        int max = params[0];
        long result = 0;
        for (int i = 1; i <= max; i++) {
            if (isCancelled()) {
                Log.d(TAG, "doInBackground: isCancelled");
                return result;
            }

            if (i == 5) {
                int test = i / 0; // Test exception
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int progress = (int) ((i / (float) max) * 100);
            Log.d(TAG, "doInBackground,SumAsyncTask@" + hashCode() + ",progress=" + progress + ",thread id=" + Thread.currentThread().getId() + ",thread name=" + Thread.currentThread().getName() + ",result=" + result);
            publishProgress(progress);
            result += i;
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {// UI Thread
        Log.e(TAG, "onProgressUpdate: ");
        if (null != mISum) {
            mISum.setProgress(values[0]);
        }
        Log.d(TAG, "onProgressUpdate: progress=" + values[0] + ",thread id=" + Thread.currentThread().getId() + ",thread name=" + Thread.currentThread().getName());
    }

    @Override
    protected void onPostExecute(Long result) {// UI Thread
        super.onPostExecute(result);
        if (null != mISum) {
            mISum.setResult(result);
        }
        Log.d(TAG, "onPostExecute: result=" + result + ",thread id=" + Thread.currentThread().getId() + ",thread name=" + Thread.currentThread().getName());
    }

    @Override
    protected void onCancelled() {// UI Thread
        super.onCancelled();
        Log.d(TAG, "onCancelled: thread id=" + Thread.currentThread().getId() + ",thread name=" + Thread.currentThread().getName());
        if (null != mISum) {
            mISum.onCancelled();
        }
    }

    @Override
    protected void onCancelled(Long result) {// UI Thread
        super.onCancelled(result);
        Log.d(TAG, "onCancelled: result=" + result + ",thread id=" + Thread.currentThread().getId() + ",thread name=" + Thread.currentThread().getName());
        if (null != mISum) {
            mISum.onCancelled(result);
        }
    }

    @Override
    protected void onError(@NonNull Exception exception) {
        super.onError(exception);
        Log.d(TAG, "onError: ");
        if (null != mISum) {
            mISum.onError(exception);
        }
    }
}

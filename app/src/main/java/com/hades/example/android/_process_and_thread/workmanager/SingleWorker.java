package com.hades.example.android._process_and_thread.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Map;

/**
 * 单独的task
 */
public class SingleWorker extends Worker {
    private static final String TAG = SingleWorker.class.getSimpleName();

    public SingleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        // thread id:2,thread name:main
        Log.d(TAG, "doWork:id:" + getId() + ",tags:" + getTags().toString() + ",hashCode:" + hashCode());
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork:id:" + getId() + ",tags:" + getTags().toString() + ",hashCode:" + hashCode());
        // TODO:TODO:default runs on Executor specified in Configuration
        // Worker的doWork用来执行后台任务，运行在子线程 - WorkerManager自带线程池中的线程。
        // WorkerManager自带线程池。每次执行任务时，优先使用线程池的thread，若没有，则创建并使用新thread，并放入线程池。
        // thread id:1990,thread name:pool-2-thread-1
        // thread id:1992,thread name:pool-2-thread-2
        // thread id:1993,thread name:pool-2-thread-3
        // thread id:1994,thread name:pool-2-thread-4
        // thread id:1990,thread name:pool-2-thread-1
        // thread id:1992,thread name:pool-2-thread-2
        if (WorkManagerFragment.ms != 0) {
            long duration = System.currentTimeMillis() - WorkManagerFragment.ms;
            Log.e(TAG, "doWork: retry interval seconds is :" + duration / 1000);
        } else {
            Log.e(TAG, "doWork: ");
        }

        WorkManagerFragment.ms = System.currentTimeMillis();
        Log.d(TAG, "doWork: thread id:" + Thread.currentThread().getId() + ",thread name:" + Thread.currentThread().getName());

        Data inputData = getInputData();
        Map<String, Object> keyValueMapOfData = inputData.getKeyValueMap();
        Log.d(TAG, "doWork: all data:" + keyValueMapOfData.toString());
        Log.d(TAG, "doWork: key:k1,value:" + inputData.getString("k1"));
        WorkManagerFragment.ms = System.currentTimeMillis();

//        Data.Builder outputDataBuilder = new Data.Builder();
//        outputDataBuilder.putBoolean("count_task_is_success", true);
//        return Result.failure();
//        return Result.failure(outputDataBuilder.build());
//        return Result.success();
//        return Result.success(outputDataBuilder.build());
//        return Result.retry();

        try {
            // Do the heavy work
            for (int i = 0; i < 3; i++) {
                Log.d(TAG, "doWork: i=" + i);
                Thread.sleep(1000);
            }
            Log.d(TAG, "doWork:success ");

            // Result 有3种。
            // return Result.success();

            Data.Builder outputDataBuilder = new Data.Builder();
            outputDataBuilder.putBoolean("count_task_is_success", true);
            return Result.success(outputDataBuilder.build());
//        return Result.retry();
        } catch (Exception exception) {
            Log.d(TAG, "doWork:fail ");
            return Result.failure();
        }
    }

    @Override
    public void onStopped() {
        super.onStopped();
        // clear up resources
        Log.d(TAG, "onStopped:id:" + getId() + ",tags:" + getTags().toString() + ",hashCode:" + hashCode());
    }
}

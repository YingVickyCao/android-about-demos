package com.hades.example.android._process_and_thread.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
public class Chaining_2_3_Worker extends Worker {
    private static final String TAG = Chaining_2_3_Worker.class.getSimpleName();

    public Chaining_2_3_Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "doWork: start:" + getInputData());
//        Data inputData = getInputData();
//        int num = inputData.getInt(MainActivity.KEY_CHAINING_1, 0);
//        int sum = inputData.getInt(MainActivity.KEY_PLANT_NAME_1, 0);
//        int result = sum + num;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "doWork: end");
        Data outputData = new Data.Builder()
                .putString(WorkManagerFragment.KEY_PLANT_NAME_1, "C")
                .build();
        return Result.success(outputData);
    }
}

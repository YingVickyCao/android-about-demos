package com.example.kotlin.test;

import android.app.Application;

import com.example.kotlin.test.db.AppDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        // 程序创建的时候执行
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        //  // 程序终止的时候执行
        super.onTerminate();
        AppDatabase.getDatabase(this).close();
    }
}

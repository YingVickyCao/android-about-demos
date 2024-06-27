package com.hades.example.android;


import android.util.Log;

public class MyLog {
    private static final String TAG = "MyLog";

    public void test() {
        Log.v(TAG, "test,v log");
//        Log.v(TAG, "test,v log", new Exception("Null"));

        Log.d(TAG, "test,d log");
//        Log.d(TAG, "test,d log", new Exception("Null"));
//
        Log.i(TAG, "test,i log");
//        Log.i(TAG, "test,i log", new Exception("Null"));
//
        Log.w(TAG, "test,w log");
//        Log.w(TAG, new Exception("Null"));
//        Log.w(TAG, "test,w log", new Exception("Null"));
//
        Log.e(TAG, "test,e log");
        /**
         * ```
         * 2021-12-28 19:53:27.037 27424-27424/com.hades.example.android E/AppLog: test,e log
         *     java.lang.Exception: Null
         *         at com.hades.example.android.MyLog.test(MyLog.java:24)
         *         at com.hades.example.android.qa.QAActivity.temp(QAActivity.java:40)
         *         at com.hades.example.android.qa.QAActivity.showCurrentTest(QAActivity.java:35)
         *         at com.hades.example.android.base.BaseActivity.initViews(BaseActivity.java:62)
         *         at com.hades.example.android.qa.QAActivity.onCreate(QAActivity.java:19)
         *         at android.app.Activity.performCreate(Activity.java:8000)
         *         at android.app.Activity.performCreate(Activity.java:7984)
         *         at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1309)
         *         at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3422)
         *         at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3601)
         *         at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:85)
         *         at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
         *         at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
         *         at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2066)
         *         at android.os.Handler.dispatchMessage(Handler.java:106)
         *         at android.os.Looper.loop(Looper.java:223)
         *         at android.app.ActivityThread.main(ActivityThread.java:7656)
         *         at java.lang.reflect.Method.invoke(Native Method)
         *         at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:592)
         *         at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:947)
         * ```
         */
//        Log.e(TAG, "test,e log", new Exception("Null"));
        /**
         * 2021-12-28 19:55:57.139 27675-27675/com.hades.example.android E/AppLog: test,e log:Null
         */
//        Log.e(TAG, "test,e log:" + new Exception("Null").getMessage());
//
//        //
        Log.wtf(TAG, "test,assert log");
//        Log.wtf(TAG, new Exception("Null"));
//        Log.wtf(TAG, "test,assert log", new Exception("Null"));
//
//        Log.println(Log.ASSERT, TAG, "message");
//        Log.i(TAG, "v log", new Exception("Null"));

        Log.d(TAG, "test: " + Log.isLoggable(TAG, Log.INFO)); // true
        Log.d(TAG, "test: " + Log.isLoggable(TAG, Log.DEBUG)); // false
    }
}

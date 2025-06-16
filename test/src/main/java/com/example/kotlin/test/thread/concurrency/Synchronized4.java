package com.example.kotlin.test.thread.concurrency;

import android.util.Log;

// https://zhuanlan.zhihu.com/p/665862642
// synchronized block
public class Synchronized4 {
    private static final String TAG = "Synchronized1";
    ILoadingViewStatus viewStatus;

    private static class Counter {
        public final Object lock = new Object();
        public static int count = 0;

        public void increment() {
            // 同步的代码块 : 锁定的是当前实例对象（this）。这意味着同一实例的不同方法调用会相互排斥，但不同实例之间的方法调用不会相互排斥。
            synchronized (this) {
                count++;
            }
        }
    }

    public Synchronized4(ILoadingViewStatus viewStatus) {
        this.viewStatus = viewStatus;
    }

    //  E  run: Counter = 20000
    // E  run: Completed 100*200 actions in 2512 ms
    public void test() {
        viewStatus.showLoading();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                Counter test = new Counter();
//        int n = 5;
                int n = 100;
                int k = 200;
//        int k = 5;
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= k; j++) {
                        int finalI = i;
                        int finalJ = j;
//                        Log.e(TAG, "i = " + i + ",j = " + j);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                test.increment();
//                        Log.e(TAG, "finalI = " + finalI + ",finalK = " + finalJ);
//                        Log.e(TAG, "run: " + "Counter = " + test.count);
                                if (finalI == n && finalJ == k) {
                                    long end = System.currentTimeMillis();
                                    long time = end - start;
                                    Log.e(TAG, "run: " + "Counter = " + test.count);
                                    Log.e(TAG, "run: " + "Completed " + finalI + "*" + finalJ + " actions in " + time + " ms");
                                    viewStatus.hideLoading();
                                }
                            }
                        }).start();
                    }
                }
            }
        }).start();
    }
}



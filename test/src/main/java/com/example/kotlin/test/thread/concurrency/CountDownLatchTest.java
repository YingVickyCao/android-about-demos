package com.example.kotlin.test.thread.concurrency;

import android.util.Log;

import java.util.concurrent.CountDownLatch;

// https://cloud.tencent.com/developer/article/2528687
// CountDownLatch
//  E  run: Counter = 20000
// E  run: Completed 100*200 actions in 1856 ms
public class CountDownLatchTest {
    private static final String TAG = "Synchronized1";
    ILoadingViewStatus viewStatus;

    private static class Counter {
        public static int count = 0;

        public void increment() {
            count++;
        }

        public void reset() {
            count = 0;
        }
    }

    public CountDownLatchTest(ILoadingViewStatus viewStatus) {
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
                test.reset();
//        int n = 5;
                int n = 100;
                int k = 100;
//        int k = 5;
                CountDownLatch countDownLatch = new CountDownLatch(n * k);
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= k; j++) {
                        int finalI = i;
                        int finalJ = j;
//                        Log.e(TAG, "i = " + i + ",j = " + j);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                test.increment();
//                                Log.e(TAG, "run: " + "thread name:" + Thread.currentThread().getName() + "，开始执行！");
//                        Log.e(TAG, "finalI = " + finalI + ",finalK = " + finalJ);
//                        Log.e(TAG, "run: " + "Counter = " + test.count);
                                if (finalI == n && finalJ == k) {
                                    long end = System.currentTimeMillis();
                                    long time = end - start;
                                    Log.e(TAG, "run: " + "Counter = " + test.count);
                                    Log.e(TAG, "run: " + "Completed " + finalI + "*" + finalJ + " actions in " + time + " ms");
                                    viewStatus.hideLoading();
                                }
                                Log.e(TAG, "run: getCount=" + countDownLatch.getCount() + ",countDown = " + test.count);
                                countDownLatch.countDown();
                            }
                        }).start();
                    }
                }

                try {
                    countDownLatch.await();
                    Log.e(TAG, "所有任务线程已执行完毕，准备进行结果汇总");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}



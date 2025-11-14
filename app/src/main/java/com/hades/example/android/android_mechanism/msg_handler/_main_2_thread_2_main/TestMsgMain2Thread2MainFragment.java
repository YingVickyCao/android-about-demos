package com.hades.example.android.android_mechanism.msg_handler._main_2_thread_2_main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hades.utility.jvm.MockHeavyWork;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;
import com.hades.utility.jvm.ThreadUtils;

import java.lang.reflect.Field;

/**
 * main -> thread -> main
 */

/*
 log:
 TestMsgMain2Thread2MainFragment: sum(),msg=1000,thread =1,main
 TestMsgMain2Thread2MainFragment: SumThread -> handleMessage(),msg=1000,thread =4487,Thread-7
 TestMsgMain2Thread2MainFragment: updateResult(),msg=499500,thread =1,main
 */

public class TestMsgMain2Thread2MainFragment extends BaseFragment {
    private static final String TAG = TestMsgMain2Thread2MainFragment.class.getSimpleName();

    static final String UPPER_NUM = "upper";
    private int num = 1000;

    SumThread calThread;
    private TextView result;

    private final int HANDLER_MSG_KEY_1 = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_handler_main_2_thread_2_main, container, false);

        view.findViewById(R.id.sum).setOnClickListener(this::sum);
        view.findViewById(R.id.stopLooper).setOnClickListener(v -> stopLooper());
        view.findViewById(R.id.isActive).setOnClickListener(v -> isActive());
        result = view.findViewById(R.id.result);

        calThread = new SumThread();
        calThread.start();
        return view;
    }

    public void sum(View source) {
        Log.d(TAG, "num: " + num + "," + ThreadUtils.getThreadInfo());
        /**
         * main -> thread
         */
        calThread.mHandlerOfThread.sendMessage(createMessage());
    }

    private Message createMessage() {
//        Message msg = new Message();
        /**
         * Message:
         * private static int sPoolSize = 0;
         * private static final int MAX_POOL_SIZE = 50;
         */
        Message msg = calThread.mHandlerOfThread.obtainMessage();
        msg.what = HANDLER_MSG_KEY_1;
        Bundle bundle = new Bundle();
        bundle.putInt(UPPER_NUM, num++);
        msg.setData(bundle);
        return msg;
    }

    class SumThread extends Thread {
        public Handler mHandlerOfThread;

        public void run() {
            Looper.prepare();

            mHandlerOfThread = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    /**
                     * 在子线程中执行
                     */
                    if (msg.what == HANDLER_MSG_KEY_1) {
                        int upper = msg.getData().getInt(UPPER_NUM);
                        long sum = MockHeavyWork.sum(upper);
                        Log.d(TAG, "handleMessage: " + upper + ",sum=" + sum + "," + ThreadUtils.getThreadInfo());

                        /**
                         * thread -> main
                         */
                        updateResult(sum);

                        //  执行异步后，结束Looper，退出run()
//                        getLooper().quitSafely();
                    }

                }
            };

            // 阻塞，开始处理消息.Looper.loop() 使得该线程专注于处理其关联的 MessageQueue
            Looper.loop();
            Log.e(TAG, "<------run"); // Looper.loop() 返回后，会执行到这里
        }
    }

    private void stopLooper() {
        calThread.mHandlerOfThread.getLooper().quitSafely();
    }

    private void isActive() {
        Log.e(TAG, "isAlive: " + calThread.isAlive());
    }


    private void updateResult(long sum) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "updateResult()," + String.valueOf(sum) + ThreadUtils.getThreadInfo());
                result.setText(String.valueOf(sum));
            }
        });
    }
}


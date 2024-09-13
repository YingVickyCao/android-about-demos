package com.hades.example.android._process_and_thread;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android._process_and_thread._asynctask.TestAsyncTaskTestFragment;
import com.hades.example.android._process_and_thread.replace_asynctask.concurrent.TestConcurrentReplaceAsyncTaskFragment;
import com.hades.example.android._process_and_thread.replace_asynctask.coroutine.TestCoroutineReplaceAsyncTaskFragment;
import com.hades.example.android._process_and_thread.replace_asynctask.rxjava.TestRxJavaReplaceAsyncTaskFragment;
import com.hades.example.android._process_and_thread.replace_asynctask.thread.TestThreadReplaceAsyncTaskFragment;
import com.hades.example.android._process_and_thread.threadPoolExecutor.ThreadPoolExecutorFragment;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.tools.FragmentUtils;

public class TestBackgroundTasksActivity extends AppCompatActivity {
    ViewsVisibilityHelper visibilityHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bckground_tasks_layout);

        findViewById(R.id.pageThreadPoolExecutor).setOnClickListener(v -> pageThreadPoolExecutor());
        findViewById(R.id.pageAsyncTask).setOnClickListener(v -> pageAsyncTask());
        findViewById(R.id.page_use_Concurrent_to_replace_AsyncTask).setOnClickListener(v -> page_use_Concurrent_to_replace_AsyncTask());
        findViewById(R.id.page_use_Coroutine_to_replace_AsyncTask).setOnClickListener(v -> page_use_Coroutine_to_replace_AsyncTask());
        findViewById(R.id.page_use_RxJava_to_replace_AsyncTask).setOnClickListener(v -> page_use_RxJava_to_replace_AsyncTask());
        findViewById(R.id.page_use_Thread_to_replace_AsyncTask).setOnClickListener(v -> page_use_Thread_to_replace_AsyncTask());

        setPageSwitch();
    }

    private void pageThreadPoolExecutor() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new ThreadPoolExecutorFragment());
    }

    private void pageAsyncTask() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestAsyncTaskTestFragment());
    }

    private void page_use_Concurrent_to_replace_AsyncTask() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestConcurrentReplaceAsyncTaskFragment());
    }

    private void page_use_Coroutine_to_replace_AsyncTask() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestCoroutineReplaceAsyncTaskFragment());
    }

    private void page_use_RxJava_to_replace_AsyncTask() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestRxJavaReplaceAsyncTaskFragment());
    }

    private void page_use_Thread_to_replace_AsyncTask() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestThreadReplaceAsyncTaskFragment());
    }

    private void setPageSwitch() {
        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }
        //  set enabled = false, handleOnBackPressed not invoked and directly back to previous activity.
        //  set enabled = true, handleOnBackPressed invoked
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (visibilityHelper.isBtnsHiden()) {
                    visibilityHelper.showBtns();
                    FragmentUtils.remove(TestBackgroundTasksActivity.this, R.id.fragmentRoot);
                } else {
                    finish();
                }
            }
        });
    }
}
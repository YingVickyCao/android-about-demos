package com.hades.example.android.test_libs.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class TestOkHttpActivity extends Activity implements IOkHttpExampleView{
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_okhttp_activity);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.sync_get).setOnClickListener(v -> sync_get());
        findViewById(R.id.async_get).setOnClickListener(v -> async_get());
        findViewById(R.id.header).setOnClickListener(v -> header());
    }

    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void sync_get() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new TestSyncGet().run(TestOkHttpActivity.this);
            }
        }).start();
    }

    private void async_get() {
        new TestAsyncGet().run(this);
    }
    private void header() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new TestHeader().run(TestOkHttpActivity.this);
            }
        }).start();
    }
}

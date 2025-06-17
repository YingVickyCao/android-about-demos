package com.example.kotlin.test.thread.concurrency;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kotlin.test.R;

public class TestThreadConcurrencyActivity extends AppCompatActivity implements ILoadingViewStatus {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thread_concurrency);

        findViewById(R.id.test).setOnClickListener(v -> test());
        progressBar = findViewById(R.id.loadingView);
    }

    private void test() {
        CountDownLatchTest test = new CountDownLatchTest(this);
        test.test();
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> progressBar.setVisibility(View.GONE));
    }
}

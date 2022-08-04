package com.hades.example.android._faq;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

/**
 * 如何实现穿透点击
 */
public class FaqPenetrateClickActivity extends AppCompatActivity {
    private static final String TAG = FaqPenetrateClickActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_penetrate_click);

        findViewById(R.id.parent).setOnClickListener(v -> Log.d(TAG, "Click Parent"));
        findViewById(R.id.btn_in_top).setOnClickListener(v -> Log.d(TAG, "Click Btn in Top"));
        findViewById(R.id.l2).setOnClickListener(v -> Log.d(TAG, "Click L2"));
    }
}

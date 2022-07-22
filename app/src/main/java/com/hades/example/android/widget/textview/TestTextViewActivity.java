package com.hades.example.android.widget.textview;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;

public class TestTextViewActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_textview_activity);
        initViews();

        findViewById(R.id.pageTextView).setOnClickListener(v -> pageTextView());
        findViewById(R.id.pageHighlightDigitalClock).setOnClickListener(v -> pageHighlightDigitalClock());
    }

    @Override
    protected void showCurrentTest() {
        pageTextView();
    }

    private void pageTextView() {
        showFragment(new TestTextViewFragment());
    }

    private void pageHighlightDigitalClock() {
        showFragment(new TestHighlightDigitalClockFragment());
    }
}
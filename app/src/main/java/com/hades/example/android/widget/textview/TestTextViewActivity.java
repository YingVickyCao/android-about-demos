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
        findViewById(R.id.page_HighlightDigitalClock).setOnClickListener(v -> pageHighlightDigitalClock());
        findViewById(R.id.page_link_in_text).setOnClickListener(v -> page_link_in_text());
    }

    @Override
    protected void showCurrentTest() {
        page_link_in_text();
    }

    private void pageTextView() {
        showFragment(new TestTextViewFragment());
    }

    private void pageHighlightDigitalClock() {
        showFragment(new TestHighlightDigitalClockFragment());
    }

    private void page_link_in_text() {
        showFragment(new LinkInTextViewFragment());
    }
}
package com.hades.example.android.widget.textview;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.tools.FragmentUtils;

public class TestTextViewActivity extends AppCompatActivity {
    ViewsVisibilityHelper visibilityHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_textview_activity);

        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }

        findViewById(R.id.pageTextView).setOnClickListener(v -> pageTextView());
        findViewById(R.id.page_HighlightDigitalClock).setOnClickListener(v -> pageHighlightDigitalClock());
        findViewById(R.id.page_link_in_text).setOnClickListener(v -> page_link_in_text());

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (visibilityHelper.isBtnsHiden()) {
                    FragmentUtils.remove(TestTextViewActivity.this, R.id.fragmentRoot);
                    visibilityHelper.showBtns();
                } else {
                    finish();
                }
            }
        });
    }

    private void pageTextView() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestTextViewFragment(), TestTextViewFragment.class.getSimpleName());
    }

    private void pageHighlightDigitalClock() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestHighlightDigitalClockFragment(), TestHighlightDigitalClockFragment.class.getSimpleName());
    }

    private void page_link_in_text() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new LinkInTextViewFragment(), LinkInTextViewFragment.class.getSimpleName());
    }
}
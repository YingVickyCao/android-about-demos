package com.hades.example.android.widget.list.scrollview.qa_swipe_whole_page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.R;

public class WrongSwipeWhoPageActivity extends BaseActivity implements View.OnClickListener {
    View mContent;
    View mFragmentRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.widget_scrollview_qa_wrong_swipe_whole_page);
        setContentView(R.layout.widget_scrollview_qa_wrong_swipe_whole_page_after);

        mContent = findViewById(R.id.content);
        mFragmentRoot = findViewById(R.id.fragmentRoot);

        findViewById(R.id.detail).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail:
                openDetailFragment();
                break;
        }
    }

    private void openDetailFragment() {
        showFragment(new WrongSwipeWhoPageFragment());
    }
}

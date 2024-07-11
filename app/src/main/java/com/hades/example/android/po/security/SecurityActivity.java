package com.hades.example.android.po.security;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.tools.FragmentUtils;

public class SecurityActivity extends AppCompatActivity {
    ViewsVisibilityHelper visibilityHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        findViewById(R.id.pageAppChooser).setOnClickListener(v -> pageAppChooser());
    }

    private void pageAppChooser() {
        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new ShowAppChooserFragment(), ShowAppChooserFragment.class.getSimpleName());
    }

    @Override
    public void onBackPressed() {
        if (visibilityHelper.isShowFragmentRoot()) {
            visibilityHelper.showBtns();
            FragmentUtils.remove(this, R.id.fragmentRoot);
        } else {
            super.onBackPressed();
        }
    }
}

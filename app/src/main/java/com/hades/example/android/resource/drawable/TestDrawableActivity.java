package com.hades.example.android.resource.drawable;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;

public class TestDrawableActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_drawable_activity_layout);

        findViewById(R.id.pageDrawableFolder).setOnClickListener(v -> pageDrawableFolder());

    }

    private void pageDrawableFolder() {
        showFragment(new TestDrawableFolderFragment());
    }
}
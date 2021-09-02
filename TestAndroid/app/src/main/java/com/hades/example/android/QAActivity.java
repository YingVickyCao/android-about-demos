package com.hades.example.android;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hades.example.android.lib.base.NoNeedPermissionActivity;
import com.hades.example.android.tools.TestDensityUtil;


public class QAActivity extends NoNeedPermissionActivity {
    private static final String TAG = "QAActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);

        initViews();

        findViewById(R.id.temp).setOnClickListener(v -> temp());
    }

    @Override
    protected boolean isNeedCheckPermission() {
        return false;
    }

    @Override
    protected boolean isShowDetail() {
        return true;
    }

    protected void showCurrentTest() {
        temp();
    }

    private void temp() {
        new TestDensityUtil().temp(this);
    }
}
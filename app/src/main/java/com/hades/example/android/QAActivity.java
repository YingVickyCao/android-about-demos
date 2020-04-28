package com.hades.example.android;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.yingvickycao.autils.base.NoNeedPermissionActivity;
import com.hades.example.android.tools.DensityUtil;

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
        DensityUtil densityUtil = new DensityUtil(this);
        densityUtil.temp();
    }
}
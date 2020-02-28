package com.hades.example.android;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.github.yingvickycao.autils.base.NoNeedPermissionActivity;
import com.hades.example.android.other_ui._actionbar.TestActionBarActivity;

public class QAActivity extends NoNeedPermissionActivity {
    private static final String TAG = QAActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);

        initViews();

        findViewById(R.id.temp).setOnClickListener(v -> temp());

        // Physical size: 1600x2560
        Log.e(TAG, "onCreate: screen type=" + getResources().getString(R.string.screen));
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
        showActivity(TestActionBarActivity.class);
    }
}
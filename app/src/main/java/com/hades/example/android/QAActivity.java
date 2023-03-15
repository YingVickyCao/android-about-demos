package com.hades.example.android;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hades.example.android.base.NoNeedPermissionActivity;
import com.hades.example.android.base.PermissionActivity;
import com.hades.example.android.media.camera.TestCameraActivity;
import com.hades.example.android.tools.permission.PermissionToolsTest;


public class QAActivity extends PermissionActivity {
    private static final String TAG = "QAActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        mRoot = findViewById(R.id.root);

        initViews();

        findViewById(R.id.temp).setOnClickListener(v -> temp());
    }

    @Override
    protected boolean isNeedCheckPermission() {
        return true;
    }

    @Override
    protected boolean isShowDetail() {
        return true;
    }

    protected void showCurrentTest() {
//        temp();
    }

    private void temp() {
//        new TestDensityUtil().temp(this);
//        new MyLog().test();
//        startActivity(new Intent(this, TestCameraActivity.class));
        PermissionToolsTest toolsTest = new PermissionToolsTest();
//        toolsTest.test(this);
        toolsTest.test2(this);

//        checkPermission("Request permission for Record", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);
    }
}
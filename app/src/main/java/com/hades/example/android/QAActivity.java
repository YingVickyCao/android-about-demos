package com.hades.example.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.app_component.service.unbounservice.StartServiceTest1Activity;


public class QAActivity extends AppCompatActivity {
    private static final String TAG = "QAActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        findViewById(R.id.temp).setOnClickListener(v -> temp());
    }

    private void temp() {
//        new TestDensityUtil().temp(this);
//        new MyLog().test();
        startActivity(new Intent(this, StartServiceTest1Activity.class));
//        PermissionToolsTest toolsTest = new PermissionToolsTest();
//        toolsTest.test(this);
//        toolsTest.test2(this);

//        checkPermission("Request permission for Record", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);
    }
}
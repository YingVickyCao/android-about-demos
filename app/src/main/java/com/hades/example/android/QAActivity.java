package com.hades.example.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.app_component._fragment.back.TestBackActivity;
import com.hades.example.android.app_component.content_provider.system.media.GalleryActivity;
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
        startActivity(new Intent(this, TestBackActivity.class));
    }
}
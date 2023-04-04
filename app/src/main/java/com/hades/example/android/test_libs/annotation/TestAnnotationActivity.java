package com.hades.example.android.test_libs.annotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hades.example.android.R;

public class TestAnnotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_annotation);
    }
}
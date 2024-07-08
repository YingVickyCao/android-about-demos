package com.hades.example.android.a;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_f);

        findViewById(R.id.back).setOnClickListener(v -> back());
    }

    private void back() {
        onBackPressed();
    }
}
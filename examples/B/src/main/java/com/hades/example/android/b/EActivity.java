package com.hades.example.android.b;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class EActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_e);

        findViewById(R.id.back).setOnClickListener(v -> back());
    }

    private void back() {
        onBackPressed();
    }
}
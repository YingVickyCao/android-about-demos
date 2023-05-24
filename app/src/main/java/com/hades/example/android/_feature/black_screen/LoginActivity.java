package com.hades.example.android._feature.black_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class LoginActivity extends Activity {

    private View progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_login);

        progress = findViewById(R.id.progress);
        progress.postDelayed(new Runnable() {
            @Override
            public void run() {
                // mock heavy work
                startActivity(new Intent(LoginActivity.this, MockMainActivity.class));
            }
        }, 10000);

    }
}
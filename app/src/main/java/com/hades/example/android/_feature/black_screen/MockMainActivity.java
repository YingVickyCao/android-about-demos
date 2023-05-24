package com.hades.example.android._feature.black_screen;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class MockMainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_red_page);
    }
}
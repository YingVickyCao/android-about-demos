package com.hades.example.android.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * System bar（Status bar 和 NavigationBar）支持theme
 */
public class TestSystemBarSupportThemeActivity extends AppCompatActivity {
    private static final String TAG = "TestDisplayEdgeToEdgeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppTheme_SystemBar_Support_Theme_Dark);
        setTheme(R.style.AppTheme_SystemBar_Support_Theme_Light);
        setContentView(R.layout.activity_system_bar_support_theme);
    }
}
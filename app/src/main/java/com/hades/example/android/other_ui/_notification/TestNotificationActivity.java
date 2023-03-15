package com.hades.example.android.other_ui._notification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

public class TestNotificationActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_ui_notification_activity);

//        new FragmentUtils().addFragment(this, new TestNotificationFragment(), TestNotificationFragment.TAG);
    }
}
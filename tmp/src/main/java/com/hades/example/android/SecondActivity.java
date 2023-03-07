package com.hades.example.android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.collapse).setOnClickListener(v -> clickCollapse());
        findViewById(R.id.close).setOnClickListener(v -> clickClose());
    }

    private void clickCollapse() {
        finish();
        EventBus.getDefault().post(new CollapseVideoEvent());
    }

    private void clickClose() {
        finish();
        EventBus.getDefault().post(new CloseVideoEvent());
    }
}
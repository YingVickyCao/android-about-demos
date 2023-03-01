package com.hades.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    View mMediaBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_one_video).setOnClickListener(v -> openOneVideo());
        findViewById(R.id.expand).setOnClickListener(v -> clickExpand());
        findViewById(R.id.close).setOnClickListener(v -> clickClose());

        mMediaBar = findViewById(R.id.media_bar);
    }

    private void openOneVideo() {
        startActivity(new Intent(this, SecondActivity.class));
    }

    private void clickExpand() {
        startActivity(new Intent(this, SecondActivity.class));
    }

    private void clickClose() {
        mMediaBar.setVisibility(View.GONE);
    }
}
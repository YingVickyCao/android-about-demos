package com.example.receive_app_links;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayDeeplinkActivity extends AppCompatActivity {
    private static final String TAG = DisplayDeeplinkActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_deeplink);

        Intent intent = getIntent();
        Toast.makeText(this, "Display this deep link", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: action:" + intent.getAction());
        Log.d(TAG, "onCreate: category:" + intent.getCategories());
        Log.d(TAG, "onCreate: data:" + intent.getData());
    }
}
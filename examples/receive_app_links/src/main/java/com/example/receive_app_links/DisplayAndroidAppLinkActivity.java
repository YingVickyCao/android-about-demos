package com.example.receive_app_links;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayAndroidAppLinkActivity extends AppCompatActivity {
    private static final String TAG = "DisplayAndroidAppLinkAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_android_app_link);

        Intent intent = getIntent();
        Toast.makeText(this, "Display this deep link", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: intent:" + intent.toString());
        Log.d(TAG, "onCreate: action:" + intent.getAction());
        Log.d(TAG, "onCreate: category:" + intent.getCategories());
        Log.d(TAG, "onCreate: schema:" + intent.getScheme());
        Log.d(TAG, "onCreate: data:" + intent.getData());
    }
}
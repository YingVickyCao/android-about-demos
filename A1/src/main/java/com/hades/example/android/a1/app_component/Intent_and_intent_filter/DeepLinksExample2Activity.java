package com.hades.example.android.a1.app_component.Intent_and_intent_filter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.a1.R;

//  web_open_app3.html
public class DeepLinksExample2Activity extends AppCompatActivity {
    private static final String TAG = "DeepLinksActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_link);
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        Log.d(TAG, "onCreate: action:" + appLinkAction + ",uri:" + appLinkData.toString());
    }
}
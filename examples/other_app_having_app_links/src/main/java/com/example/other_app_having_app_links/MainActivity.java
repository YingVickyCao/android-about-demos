package com.example.other_app_having_app_links;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * other_app_having_app_links 作为第三方app，点击它的link，测试
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl("file:///android_asset/links_in_other_app.html");
    }
}
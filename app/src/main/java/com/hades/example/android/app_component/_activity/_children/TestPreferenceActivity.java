package com.hades.example.android.app_component._activity._children;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.app_component._fragment.Prefs1Fragment;
import com.hades.example.android.app_component._fragment.Prefs2Fragment;

// https://developer.android.com/develop/ui/views/components/settings#java
public class TestPreferenceActivity extends AppCompatActivity {
    View setting_btns;
    View settings_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.preference_setting);
        setting_btns = findViewById(R.id.setting_btns);
        settings_container = findViewById(R.id.settings_container);

        findViewById(R.id.app_option_setting).setOnClickListener(v -> app_option_setting());
        findViewById(R.id.ui_option_setting).setOnClickListener(v -> ui_option_setting());
        findViewById(R.id.use_intent).setOnClickListener(v -> use_intent());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showBtns();
            }
        });
    }

    private void app_option_setting() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new Prefs1Fragment())
                .commit();
        hideBtns();
    }

    private void showBtns() {
        setting_btns.setVisibility(View.VISIBLE);
        settings_container.setVisibility(View.GONE);
    }

    private void hideBtns() {
        setting_btns.setVisibility(View.GONE);
        settings_container.setVisibility(View.VISIBLE);
    }

    private void ui_option_setting() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new Prefs2Fragment())
                .commit();
        hideBtns();
    }

    private void use_intent() {
        String url = "https://developer.android.google.cn/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}


package com.hades.example.android.custom_theme;

import android.app.TaskStackBuilder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static boolean isLightTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_main);
        setThemeMode();

        findViewById(R.id.changeTheme).setOnClickListener(view -> changeTheme());
    }

    private void changeTheme() {
        isLightTheme = !isLightTheme;
        finish();
        TaskStackBuilder.create(this).addNextIntent(getIntent()).startActivities();
    }

    private void setTheme() {
        if (isLightTheme) {
            setTheme(R.style.LightAppTheme);
        } else {
            setTheme(R.style.DarkAppTheme);
        }
    }

    private void setThemeMode() {
        if (isLightTheme) {
            ((TextView) findViewById(R.id.themeMode)).setText("Light Theme");
        } else {
            ((TextView) findViewById(R.id.themeMode)).setText("Dark Theme");
        }
    }
}
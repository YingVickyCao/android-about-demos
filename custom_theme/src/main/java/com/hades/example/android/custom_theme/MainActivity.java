package com.hades.example.android.custom_theme;

import android.app.TaskStackBuilder;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {
    static boolean isLightTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_main);
        setThemeMode();

        findViewById(R.id.changeTheme).setOnClickListener(view -> changeTheme());
        findViewById(R.id.changeBackground).setOnClickListener(view -> changeBackground());
        findViewById(R.id.addButton).setOnClickListener(view -> addButton());
    }

    private void changeTheme() {
        isLightTheme = !isLightTheme;
        finish();
        TaskStackBuilder.create(this).addNextIntent(getIntent()).startActivities();
    }

    private void changeBackground() {
        findViewById(R.id.themeBtn).setBackgroundResource(R.color.btn_light);
    }

    private void addButton() {
        AppCompatButton btn = new CustomAppCompatButton(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(lp.leftMargin, lp.topMargin + 20, lp.rightMargin, lp.bottomMargin);
        btn.setLayoutParams(lp);
        btn.setText("New button");
        LinearLayout layout = findViewById(R.id.topBtns);
        layout.addView(btn);
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
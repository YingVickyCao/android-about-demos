package com.example.kotlin.test.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kotlin.test.R;

public class NewMenuActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.kotlin.test.ui.REPLY";
    private String menuText = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_menu);

        findViewById(R.id.generate).setOnClickListener(v -> generate());
        findViewById(R.id.save).setOnClickListener(v -> save());
    }

    private void generate() {
        menuText = "Menu " + System.currentTimeMillis();
    }

    private void save() {
        Intent replayIntent = new Intent();
        if (menuText.isEmpty()) {
            setResult(RESULT_CANCELED, replayIntent);
        } else {
            replayIntent.putExtra(EXTRA_REPLY, menuText);
            setResult(RESULT_OK, replayIntent);
        }
        finish();
    }
}

package com.hades.example.android.b;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CActivity extends AppCompatActivity {
    int num1 = 0;
    int num2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_c);

        TextView received = findViewById(R.id.received);
        Intent intent = getIntent();
        if (null != intent) {
            num1 = intent.getIntExtra("NUM1", 0);
            num2 = intent.getIntExtra("NUM2", 0);
            if (num1 != 0) {
                received.setText(String.valueOf(num1));
            }
            if (num2 != 0) {
                received.setText(String.valueOf(num2));
            }
        }

        findViewById(R.id.back).setOnClickListener(v -> back());
    }

    private void back() {
        if (num1 != 0) {
            buildResult("NUM1", num1, 1);
            return;
        }

        if (num2 != 0) {
            buildResult("NUM2", num2, 2);
        }
    }

    private void buildResult(String label, int receivedNum, int plusNum) {
        Intent intent2 = new Intent();
        intent2.setAction("com.hades.example.android.a");
        intent2.addCategory(Intent.CATEGORY_DEFAULT);
        intent2.putExtra(label, receivedNum + plusNum);
        setResult(2000, intent2);
        finish();
    }
}
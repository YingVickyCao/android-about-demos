package com.hades.example.android.a;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

public class AActivity extends AppCompatActivity {
    private static final String TAG = AActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manifest_security_a_layout);
        findViewById(R.id.jumpB).setOnClickListener(v -> jumpB());
        findViewById(R.id.jumpC4ImplicitIntent).setOnClickListener(v -> jumpC4ImplicitIntent());
        findViewById(R.id.jumpC4ExplicitIntent).setOnClickListener(v -> jumpC4ExplicitIntent());
        findViewById(R.id.jumpE).setOnClickListener(v -> jumpE());
        findViewById(R.id.jumpF).setOnClickListener(v -> jumpF());
    }

    private void jumpB() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.hades.example.android.b", "com.hades.example.android.b.BActivity");
        intent.setComponent(componentName);
        intent.putExtra("NUM", 100);
        startActivity(intent);
    }

    private void jumpC4ExplicitIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.hades.example.android.b", "com.hades.example.android.b.CActivity");
        intent.putExtra("NUM1", 100);
//        startActivity(intent);
        startActivityForResult(intent, 1000);
    }

    /*
    In B App, C :
    android:exported="true", and no <intent-filter>
     ERROR: android.content.ActivityNotFoundException: No Activity found to handle Intent { act=com.hades.example.android.b.c cat=[android.intent.category.DEFAULT] (has extras) }
     */
    private void jumpC4ImplicitIntent() {
        Intent intent = new Intent();
        intent.setAction("com.hades.example.android.b.c");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("NUM2", 200);
//        startActivity(intent);
        startActivityForResult(intent, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1000 == requestCode) {
            if (data != null) {
                int num1 = data.getIntExtra("NUM1", 0);
                Log.e(TAG, "onActivityResult:num1=" + num1);
                Toast.makeText(this, String.valueOf(num1), Toast.LENGTH_SHORT).show();
            }
        } else if (2000 == requestCode) {
            if (data != null) {
                int num2 = data.getIntExtra("NUM2", 0);
                Log.d(TAG, "onActivityResult:num2=" + num2);
                Toast.makeText(this, String.valueOf(num2), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void jumpE() {
        // https://developer.android.google.cn/about/versions/14/behavior-changes-14?hl=en#safer-intents
        // launch this activity using an implicit intent, an exception would be thrown
        // Error:java.lang.SecurityException: Permission Denial: starting Intent { act=com.hades.example.android.b.e cmp=com.hades.example.android.b/.EActivity } from ProcessRecord{cb265d 25303:com.hades.example.android.a/u0a586} (pid=25303, uid=10586) not exported from uid 10585
//        Intent intent = new Intent("com.hades.example.android.b.e");
//        startActivity(intent);
        // To launch the non-exported activity, your app should use an explicit intent instead:
        Intent intent = new Intent("com.hades.example.android.b.e");
        intent.setPackage("com.hades.example.android.b");
//        intent.setPackage(getPackageName());
        startActivity(intent);
    }

    private void jumpF() {
        // https://developer.android.google.cn/about/versions/14/behavior-changes-14?hl=en#safer-intents
        // launch this activity using an implicit intent, an exception would be thrown
        // Error:android.content.ActivityNotFoundException: No Activity found to handle Intent { act=com.hades.example.android.a.f }
//        Intent intent = new Intent("com.hades.example.android.a.f");
//        startActivity(intent);
        // To launch the non-exported activity, your app should use an explicit intent instead:
        Intent intent = new Intent("com.hades.example.android.a.f");
        intent.setPackage("com.hades.example.android.a"); // ok
        intent.setPackage(getPackageName());
        startActivity(intent);
    }
}

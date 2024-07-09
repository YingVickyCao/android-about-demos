package com.hades.example.android.a;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.OnSimplePermissionCallback;
import com.hades.utility.permission.PermissionsTool;

// <permission>
public class TestCustomPermissionActivity extends AppCompatActivity {
    private static final String TAG = TestCustomPermissionActivity.class.getSimpleName();
    PermissionsTool permissionsTool;
    private String[] customPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manifest_cusotom_permission_layout);

        findViewById(R.id.jump).setOnClickListener(v -> clickJump());
    }

    // Define a custom app permission  https://developer.android.com/guide/topics/permissions/defining
    private void clickJump() {
        if (null == permissionsTool) {
            permissionsTool = new PermissionsTool(this);
        }
        if (null == customPermission) {
            customPermission = new String[]{"com.hades.example.android.b.CUSTOM_PERMISSION_TEST"};
        }

        permissionsTool.request(customPermission, new OnPermissionResultCallback() {
            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request custom permission", Snackbar.LENGTH_SHORT).setAction("Allow", v -> callback.ok()).setAction("Cancel", v -> callback.cancel()).show();
            }

            @Override
            public void onPermissionGranted() {
                Snackbar.make(findViewById(R.id.root), "custom permission granted", Snackbar.LENGTH_SHORT).show();
                jump();
            }

            @Override
            public void onPermissionDenied() {
                Snackbar.make(findViewById(R.id.root), "custom permission denied", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionError(String message) {
                Snackbar.make(findViewById(R.id.root), "custom permission error", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void jump() {
        ComponentName componentName = new ComponentName("com.hades.example.android.b", "com.hades.example.android.b.DActivity");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.putExtra("NUM", 100);
        startActivity(intent);
    }
}
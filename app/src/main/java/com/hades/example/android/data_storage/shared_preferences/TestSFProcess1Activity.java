package com.hades.example.android.data_storage.shared_preferences;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.PermissionsTool;

public class TestSFProcess1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_storage_sf_in_another_process);
    }

    private void requestPermission() {
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , new OnPermissionResultCallback() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(TestSFProcess1Activity.this, "SD card permission granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionError(String message) {

                    }

                    @Override
                    public void showInContextUI(OnContextUIListener callback) {
                        Snackbar.make(findViewById(R.id.root), "Request SD card permission", Snackbar.LENGTH_INDEFINITE)
                                .setAction(getString(R.string.ok), view -> callback.ok())
                                .setAction(getString(R.string.cancel), view -> callback.cancel())
                                .show();
                    }
                });
    }
}

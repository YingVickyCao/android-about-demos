package com.hades.example.android.data_storage.shared_preferences;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.tools.permission.IRationaleOnClickListener;
import com.hades.example.android.tools.permission.IRequestPermissionsCallback;
import com.hades.example.android.tools.permission.PermissionTools;

public class TestSFProcess1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_storage_sf_in_another_process);
    }

    private void requestPermission() {
        PermissionTools permissionTools = new PermissionTools(this);
        permissionTools.request(new IRequestPermissionsCallback() {
            @Override
            public void showRationaleContextUI(IRationaleOnClickListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request SD card permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.clickOK())
                        .setAction(getString(R.string.cancel), view -> callback.clickCancel())
                        .show();
            }

            @Override
            public void granted() {
                Toast.makeText(TestSFProcess1Activity.this, "SD card permission granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}

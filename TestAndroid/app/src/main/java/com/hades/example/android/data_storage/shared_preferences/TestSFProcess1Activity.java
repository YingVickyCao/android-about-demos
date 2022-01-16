package com.hades.example.android.data_storage.shared_preferences;

import android.Manifest;

import com.hades.example.android.R;
import com.hades.example.android.lib.base.PermissionActivity;

public class TestSFProcess1Activity extends PermissionActivity {
    @Override
    protected void requestPermission() {
        super.requestPermission();

        setContentView(R.layout.data_storage_sf_in_another_process);

        checkPermission("Request SD card permission", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}

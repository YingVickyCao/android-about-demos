package com.hades.example.android.data_storage;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.data_storage.database.TestSQLiteActivity;
import com.hades.example.android.data_storage.io.TestIOFragment;
import com.hades.example.android.data_storage.io.TestZipFragment;
import com.hades.example.android.data_storage.shared_preferences.TestSharedPreferencesFragment;
import com.hades.example.android.lib.base.PermissionActivity;

public class DataStorageActivity extends PermissionActivity {
    private static final String TAG = DataStorageActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        setContentView(R.layout.data_storage_layout);

        initViews(R.id.root);

        findViewById(R.id.pageSharedPreferences).setOnClickListener(v -> pageSharedPreferences());
        findViewById(R.id.pageDatabase).setOnClickListener(v -> pageDatabase());
        findViewById(R.id.pageIO).setOnClickListener(v -> pageIO());
        findViewById(R.id.pageIO_Zip).setOnClickListener(v -> pageIO_Zip());
    }

    @Override
    protected void requestPermission() {
        super.requestPermission();
        checkPermission("Request SD card permission", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void showCurrentTest() {
        pageIO_Zip();
    }

    private void pageSharedPreferences() {
        showFragment(new TestSharedPreferencesFragment());
    }

    private void pageDatabase() {
//        showFragment(new TestDBFragment());
        showActivity(TestSQLiteActivity.class);
    }

    private void pageIO() {
        showFragment(new TestIOFragment());
    }

    private void pageIO_Zip() {
        showFragment(new TestZipFragment());
    }
}

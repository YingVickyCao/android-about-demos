package com.hades.example.android.data_storage;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.data_storage.database.TestSQLiteActivity;
import com.hades.example.android.data_storage.io.TestIOFragment;
import com.hades.example.android.data_storage.io.TestZipFragment;
import com.hades.example.android.data_storage.shared_preferences.TestSharedPreferencesFragment;
import com.hades.example.android.tools.permission.IRationaleOnClickListener;
import com.hades.example.android.tools.permission.IRequestPermissionsCallback;
import com.hades.example.android.tools.permission.PermissionTools;

public class DataStorageActivity extends BaseActivity {
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
        requestPermission();
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
                Toast.makeText(DataStorageActivity.this, "SD card permission granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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

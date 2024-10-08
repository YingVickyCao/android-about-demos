package com.hades.example.android.resource.bitmap;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.resource.bitmap.three_level_cache.ImageGridActivity;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.PermissionsTool;

/**
 * https://www.cnblogs.com/andriod-html5/archive/2012/04/30/2539419.html
 */
public class BitmapActivity extends BaseActivity {
    private static final String TAG = BitmapActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_bitmap_activity_layout);
        Log.d(TAG, "onCreate: ");
        initViews(R.id.root);
        findViewById(R.id.page_Drawable_and_ScreenDensity).setOnClickListener(v -> page_Drawable_and_ScreenDensity());
        findViewById(R.id.page_create_bitmap).setOnClickListener(v -> page_create_bitmap());
        findViewById(R.id.page_BitmapViewer).setOnClickListener(v -> page_BitmapViewer());
        findViewById(R.id.page_LoadBitmapPo).setOnClickListener(v -> page_LoadBitmapPo());
        findViewById(R.id.page_MemoryCacheBitmap).setOnClickListener(v -> page_MemoryCacheBitmap());

        requestPermission();
    }

    private void requestPermission() {
        // FIXED_ERROR:java.io.FileNotFoundException: /sdcard/bg004.JPG: open failed: EACCES (Permission denied)
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new OnPermissionResultCallback() {

            @Override
            public void onPermissionGranted() {
                Toast.makeText(BitmapActivity.this, "SD Card permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(BitmapActivity.this, "SD Card permission denied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionError(String message) {

            }

            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request SD Card permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }
        });
    }

    @Override
    protected void showCurrentTest() {
        page_Drawable_and_ScreenDensity();
    }

    private void page_Drawable_and_ScreenDensity() {
        showFragment(new TestBitmapMemoryAndScreenDensityFragment());
    }

    private void page_create_bitmap() {
        showFragment(new TestBitmapFragment());
    }

    private void page_BitmapViewer() {
        showFragment(new TestBitmapViewerFragment());
    }

    private void page_LoadBitmapPo() {
        showFragment(new TestDecodeSampledBitmapFragment());
    }

    private void page_MemoryCacheBitmap() {
        showActivity(ImageGridActivity.class);
    }
}
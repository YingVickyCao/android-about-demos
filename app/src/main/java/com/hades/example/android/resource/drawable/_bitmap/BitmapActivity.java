package com.hades.example.android.resource.drawable._bitmap;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.PermissionActivity;
import com.hades.example.android.resource.drawable._bitmap.three_level_cache.ImageGridActivity;

/**
 * https://www.cnblogs.com/andriod-html5/archive/2012/04/30/2539419.html
 */
public class BitmapActivity extends PermissionActivity {
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
    }

    @Override
    protected void requestPermission() {
        Log.d(TAG, "requestPermission: ");
        checkPermission("Request permission for operate storage", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void showCurrentTest() {
        page_LoadBitmapPo();
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
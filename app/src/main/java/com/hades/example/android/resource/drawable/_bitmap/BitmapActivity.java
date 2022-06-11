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
        findViewById(R.id.pageBitmap).setOnClickListener(v -> pageBitmap());
        findViewById(R.id.pageBitmapViewer).setOnClickListener(v -> pageBitmapViewer());
        findViewById(R.id.pageLoadBitmapPo).setOnClickListener(v -> pageLoadBitmapPo());
        findViewById(R.id.pageMemoryCacheBitmap).setOnClickListener(v -> pageBitmapThreeLevelCache());
    }

    @Override
    protected void requestPermission() {
        Log.d(TAG, "requestPermission: ");
        checkPermission("Request permission for operate storage", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void showCurrentTest() {
        pageLoadBitmapPo();
    }

    private void pageBitmap() {
        showFragment(new TestBitmapFragment());
    }

    private void pageBitmapViewer() {
        showFragment(new TestBitmapViewerFragment());
    }

    private void pageLoadBitmapPo() {
        showFragment(new TestDecodeSampledBitmapFragment());
    }

    private void pageBitmapThreeLevelCache() {
        showActivity(ImageGridActivity.class);
    }
}
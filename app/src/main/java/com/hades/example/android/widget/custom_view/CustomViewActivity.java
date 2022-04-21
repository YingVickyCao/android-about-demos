package com.hades.example.android.widget.custom_view;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.lib.base.PermissionActivity;
import com.hades.example.android.resource.drawable._bitmap.TestBitmapFragment;
import com.hades.example.android.resource.drawable._bitmap.TestBitmapViewerFragment;
import com.hades.example.android.resource.drawable._bitmap.TestDecodeSampledBitmapFragment;
import com.hades.example.android.resource.drawable._bitmap.three_level_cache.ImageGridActivity;
import com.hades.example.android.widget.custom_view.cascadelayout.CascadeLayoutActivity;
import com.hades.example.android.widget.custom_view.shadow.TestShadowViewFragment;

/**
 * https://www.cnblogs.com/andriod-html5/archive/2012/04/30/2539419.html
 */
public class CustomViewActivity extends PermissionActivity {
    private static final String TAG = CustomViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Light);
        setContentView(R.layout.widget_custom_view_activity_layout);
        Log.d(TAG, "onCreate: ");
        initViews();
        findViewById(R.id.pageCustomVew).setOnClickListener(v -> pageCustomVew());
        findViewById(R.id.page_CustomVew4CascadeLayout).setOnClickListener(v -> pageCustomView4CascadeLayout());
        findViewById(R.id.page_CustomVew_ShadowView).setOnClickListener(v -> page_CustomVew_ShadowView());
    }

    @Override
    protected void requestPermission() {
        Log.d(TAG, "requestPermission: ");
        checkPermission("Request permission for operate storage", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void showCurrentTest() {
        pageCustomVew();
    }

    private void pageCustomVew() {
        showFragment(new TestCustomViewFragment());
    }

    private void pageCustomView4CascadeLayout() {
        showActivity(CascadeLayoutActivity.class);
    }

    private void page_CustomVew_ShadowView() {
        showFragment(new TestShadowViewFragment());
    }
}
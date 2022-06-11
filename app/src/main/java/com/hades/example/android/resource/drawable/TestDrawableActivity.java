package com.hades.example.android.resource.drawable;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.resource.drawable._bitmap.BitmapActivity;
import com.hades.example.android.resource.drawable._level_list.TestLevelListDrawableFragment;
import com.hades.example.android.resource.drawable.clip.TestClipDrawableFragment;
import com.hades.example.android.resource.drawable.layer.TestLayerDrawableFragment;
import com.hades.example.android.resource.drawable.shape.TestShapeDrawableFragment;
import com.hades.example.android.resource.drawable.state.TestStateDrawableFragment;
import com.hades.example.android.resource.drawable.vector.TestVectorDrawableFragment;

public class TestDrawableActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_drawable_activity_layout);
        initViews(R.id.root);
        findViewById(R.id.pageDrawableFolder).setOnClickListener(v -> pageDrawableFolder());
        findViewById(R.id.pageStateDrawable).setOnClickListener(v -> pageStateDrawable());
        findViewById(R.id.pageVectorDrawable).setOnClickListener(v -> pageVectorDrawable());
        findViewById(R.id.pageBitmap).setOnClickListener(v -> pageBitmap());
        findViewById(R.id.pageShapeDrawable).setOnClickListener(v -> pageShapeDrawable());
        findViewById(R.id.pageLevelListDrawable).setOnClickListener(v -> pageLevelListDrawable());
        findViewById(R.id.pageLayerListDrawable).setOnClickListener(v -> pageLayerListDrawable());
        findViewById(R.id.pageClipDrawable).setOnClickListener(v -> pageClipDrawable());
    }

    private void pageDrawableFolder() {
        showFragment(new TestDrawableFolderFragment());
    }

    private void pageStateDrawable() {
        showFragment(new TestStateDrawableFragment());
    }

    private void pageVectorDrawable() {
        showFragment(new TestVectorDrawableFragment());
    }
    
    private void pageBitmap() {
        showActivity(BitmapActivity.class);
    }

    private void pageShapeDrawable() {
        showFragment(new TestShapeDrawableFragment());
    }

    private void pageLevelListDrawable() {
        showFragment(new TestLevelListDrawableFragment());
    }

    private void pageLayerListDrawable() {
        showFragment(new TestLayerDrawableFragment());
    }

    private void pageClipDrawable() {
        showFragment(new TestClipDrawableFragment());
    }
}
package com.hades.example.android.resource.drawable.bitmap;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.resource.drawable.bitmap.change_icon_render_color.TestColorFilterFragment;
import com.hades.example.android.resource.drawable.bitmap.change_icon_render_color.TestTileModeFragment;
import com.hades.example.android.resource.drawable.bitmap.change_icon_render_color.TestTintFragment;

/**
 * https://www.cnblogs.com/andriod-html5/archive/2012/04/30/2539419.html
 */
public class TestBitmapDrawableActivity extends BaseActivity {
    private static final String TAG = TestBitmapDrawableActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_bitmap_drawable_activity_layout);
        Log.d(TAG, "onCreate: ");
        initViews(R.id.root);
        findViewById(R.id.page_BitmapDrawable).setOnClickListener(v -> page_BitmapDrawable());
        findViewById(R.id.page_ColorFilter).setOnClickListener(v -> page_ColorFilter());
        findViewById(R.id.page_Tint).setOnClickListener(v -> page_Tint());
        findViewById(R.id.page_TileMode).setOnClickListener(v -> page_TileMode());
    }

    @Override
    protected void showCurrentTest() {
        page_Tint();
    }

    private void page_BitmapDrawable() {
        showFragment(new TestBitmapDrawableFragment());
    }

    private void page_ColorFilter() {
        showFragment(new TestColorFilterFragment());
    }

    private void page_Tint() {
        showFragment(new TestTintFragment());
    }

    private void page_TileMode() {
        showFragment(new TestTileModeFragment());
    }
}
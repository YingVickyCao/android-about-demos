package com.hades.example.android.resource.drawable.bitmap;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.resource.drawable.bitmap.change_icon_render_color.TestColorFilterFragment;
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
        findViewById(R.id.pageColorFilter).setOnClickListener(v -> pageColorFilter());
        findViewById(R.id.pageTint).setOnClickListener(v -> pageTint());
    }

    @Override
    protected void showCurrentTest() {
        pageTint();
    }

    private void page_BitmapDrawable() {
        showFragment(new TestBitmapDrawableFragment());
    }

    private void pageColorFilter() {
        showFragment(new TestColorFilterFragment());
    }

    private void pageTint() {
        showFragment(new TestTintFragment());
    }
}
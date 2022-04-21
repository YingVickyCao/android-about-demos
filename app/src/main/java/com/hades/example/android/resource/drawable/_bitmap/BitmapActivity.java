package com.hades.example.android.resource.drawable._bitmap;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.lib.base.PermissionActivity;
import com.hades.example.android.resource._array.TestStringIntegerArrayFragment;
import com.hades.example.android.resource._color_state_list.TestColorFragment;
import com.hades.example.android.resource._color_state_list.TestColorStateListFragment;
import com.hades.example.android.resource._style_theme.ThemeChoosePageAActivity;
import com.hades.example.android.resource.adapter_screen.ScreenSizeActivity;
import com.hades.example.android.resource.adapter_screen.TestConfigurationActivity;
import com.hades.example.android.resource.anim.TestFrameAnimationFragment;
import com.hades.example.android.resource.anim.TestTweenAnimationFragment;
import com.hades.example.android.resource.animator.TestPropertyAnimationFragment;
import com.hades.example.android.resource.dimension.TestDimensionFragment;
import com.hades.example.android.resource.drawable.TestDrawableFolderFragment;
import com.hades.example.android.resource.drawable._bitmap.three_level_cache.ImageGridActivity;
import com.hades.example.android.resource.drawable._level_list.TestLevelListDrawableFragment;
import com.hades.example.android.resource.drawable.clip.TestClipDrawableFragment;
import com.hades.example.android.resource.drawable.layer.TestLayerDrawableFragment;
import com.hades.example.android.resource.drawable.shape.TestShapeDrawableFragment;
import com.hades.example.android.resource.drawable.state.TestStateDrawableFragment;
import com.hades.example.android.resource.drawable.vector.TestVectorDrawableFragment;
import com.hades.example.android.resource.font.TestFontFragment;
import com.hades.example.android.resource.i18n.InternationalizationFragment;
import com.hades.example.android.resource.material.TestMaterialFragment;
import com.hades.example.android.resource.xml.TestXMLFragment;

/**
 * https://www.cnblogs.com/andriod-html5/archive/2012/04/30/2539419.html
 */
public class BitmapActivity extends PermissionActivity {
    private static final String TAG = BitmapActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Light);
        setContentView(R.layout.res_bitmap_layout);
        initViews();
        findViewById(R.id.pageBitmap).setOnClickListener(v -> pageBitmap());
        findViewById(R.id.pageBitmapViewer).setOnClickListener(v -> pageBitmapViewer());
        findViewById(R.id.pageLoadBitmapPo).setOnClickListener(v -> pageLoadBitmapPo());
        findViewById(R.id.pageMemoryCacheBitmap).setOnClickListener(v -> pageBitmapThreeLevelCache());
    }

    @Override
    protected void requestPermission() {
        checkPermission("Request permission for operate storage", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void showCurrentTest() {
        pageBitmapViewer();
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
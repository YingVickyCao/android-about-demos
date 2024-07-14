package com.hades.example.android.resource;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.data_storage.DataStorageActivity;
import com.hades.example.android.data_storage.shared_preferences.TestSharedPreferencesFragment;
import com.hades.example.android.resource._array.TestStringIntegerArrayFragment;
import com.hades.example.android.resource._color_state_list.TestColorFragment;
import com.hades.example.android.resource._color_state_list.TestColorStateListFragment;
import com.hades.example.android.resource._style_theme.ThemeChoosePageAActivity;
import com.hades.example.android.resource.adapter_screen.ScreenSizeActivity;
import com.hades.example.android.resource.adapter_screen.TestConfigurationActivity;
import com.hades.example.android.resource.anim.TestFrameAnimationFragment;
import com.hades.example.android.resource.anim.TestTweenAnimationFragment;
import com.hades.example.android.resource.animator.TestObjectAnimationFragment;
import com.hades.example.android.resource.animator.TestValueAnimationFragment;
import com.hades.example.android.resource.dimension.TestDimensionFragment;
import com.hades.example.android.resource.font.TestFontFragment;
import com.hades.example.android.resource.i18n.InternationalizationFragment;
import com.hades.example.android.resource.material.TestMaterialFragment;
import com.hades.example.android.resource.xml.TestXMLFragment;
import com.hades.example.android.tools.FragmentUtils;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.PermissionsTool;

/**
 * https://www.cnblogs.com/andriod-html5/archive/2012/04/30/2539419.html
 */
public class ResourceActivity extends AppCompatActivity {
    private static final String TAG = ResourceActivity.class.getSimpleName();
    ViewsVisibilityHelper visibilityHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppTheme_Light);
        setContentView(R.layout.activity_resources);
        Log.d(TAG, "onCreate: ");

        findViewById(R.id.pageMaterial).setOnClickListener(v -> pageMaterial());
        findViewById(R.id.pageDimension).setOnClickListener(v -> pageDimension());
        findViewById(R.id.pageThemeChoose).setOnClickListener(v -> pageThemeChoose());
        findViewById(R.id.pageInternationalization).setOnClickListener(v -> pageInternationalization());
        findViewById(R.id.pageStringIntegerArray).setOnClickListener(v -> pageStringIntegerArray());
        findViewById(R.id.pageFont).setOnClickListener(v -> pageFont());
        findViewById(R.id.pageXML).setOnClickListener(v -> pageXML());
        findViewById(R.id.pageScreenOrientation).setOnClickListener(v -> pageScreenOrientation());
        findViewById(R.id.pageScreenSize).setOnClickListener(v -> pageScreenSize());
        findViewById(R.id.pageColor).setOnClickListener(v -> pageColor());
        findViewById(R.id.pageColorStateListResource).setOnClickListener(v -> pageColorStateListResource());

        findViewById(R.id.pageTweenAnimation).setOnClickListener(v -> pageTweenAnimation());
        findViewById(R.id.pageFrameAnimation).setOnClickListener(v -> pageFrameAnimation());
        findViewById(R.id.page_ObjectAnimation).setOnClickListener(v -> page_ObjectAnimation());
        findViewById(R.id.page_ValueAnimation).setOnClickListener(v -> page_ValueAnimation());

        requestPermission();
        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (visibilityHelper.isBtnsHiden()) {
                    FragmentUtils.remove(ResourceActivity.this, R.id.fragmentRoot);
                    visibilityHelper.showBtns();
                } else {
                    finish();
                }
            }
        });
    }


    private void requestPermission() {
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new OnPermissionResultCallback() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(ResourceActivity.this, "SD card permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(ResourceActivity.this, "SD card permission denied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionError(String message) {

            }

            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request SD card permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }
        });
    }

    private void pageColor() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestColorFragment(), TestColorFragment.class.getSimpleName());
    }

    private void pageColorStateListResource() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestColorStateListFragment(), TestColorStateListFragment.class.getSimpleName());
    }

    private void pageMaterial() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestMaterialFragment(), TestMaterialFragment.class.getSimpleName());
    }

    private void pageDimension() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestDimensionFragment(), TestDimensionFragment.class.getSimpleName());
    }

    private void pageThemeChoose() {
        startActivity(new Intent(this, ThemeChoosePageAActivity.class));
    }

    private void pageInternationalization() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new InternationalizationFragment(), InternationalizationFragment.class.getSimpleName());
    }

    private void pageStringIntegerArray() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestStringIntegerArrayFragment(), TestStringIntegerArrayFragment.class.getSimpleName());
    }

    private void pageFont() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestFontFragment(), TestFontFragment.class.getSimpleName());
    }

    private void pageXML() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestXMLFragment(), TestXMLFragment.class.getSimpleName());
    }

    private void pageScreenOrientation() {
        startActivity(new Intent(this, TestConfigurationActivity.class));
    }

    private void pageScreenSize() {
        startActivity(new Intent(this, ScreenSizeActivity.class));
    }

    private void pageTweenAnimation() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestTweenAnimationFragment(), TestTweenAnimationFragment.class.getSimpleName());
    }

    private void pageFrameAnimation() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestFrameAnimationFragment(), TestFrameAnimationFragment.class.getSimpleName());
    }

    private void page_ObjectAnimation() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestObjectAnimationFragment(), TestObjectAnimationFragment.class.getSimpleName());
    }

    private void page_ValueAnimation() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestValueAnimationFragment(), TestValueAnimationFragment.class.getSimpleName());
    }
}
package com.hades.example.android.resource;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
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
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnResultCallback;
import com.hades.utility.permission.PermissionsTool;

/**
 * https://www.cnblogs.com/andriod-html5/archive/2012/04/30/2539419.html
 */
public class ResourceActivity extends BaseActivity {
    private static final String TAG = ResourceActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppTheme_Light);
        setContentView(R.layout.activity_resources);
        Log.d(TAG, "onCreate: ");
        initViews(R.id.root);

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
    }


    private void requestPermission() {
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new OnResultCallback() {
            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request SD card permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }

            @Override
            public void granted() {
                Toast.makeText(ResourceActivity.this, "SD card permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void denied() {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void showCurrentTest() {
        page_ObjectAnimation();
    }

    private void pageColor() {
        showFragment(new TestColorFragment());
    }

    private void pageColorStateListResource() {
        showFragment(new TestColorStateListFragment());
    }

    private void pageMaterial() {
        showFragment(new TestMaterialFragment());
    }

    private void pageDimension() {
        showFragment(new TestDimensionFragment());
    }

    private void pageThemeChoose() {
        showActivity(ThemeChoosePageAActivity.class);
    }

    private void pageInternationalization() {
        showFragment(new InternationalizationFragment());
    }

    private void pageStringIntegerArray() {
        showFragment(new TestStringIntegerArrayFragment());
    }

    private void pageFont() {
        showFragment(new TestFontFragment());
    }

    private void pageXML() {
        showFragment(new TestXMLFragment());
    }

    private void pageScreenOrientation() {
        showActivity(TestConfigurationActivity.class);
    }

    private void pageScreenSize() {
        showActivity(ScreenSizeActivity.class);
    }

    private void pageTweenAnimation() {
        showFragment(new TestTweenAnimationFragment());
    }

    private void pageFrameAnimation() {
        showFragment(new TestFrameAnimationFragment());
    }

    private void page_ObjectAnimation() {
        showFragment(new TestObjectAnimationFragment());
    }

    private void page_ValueAnimation() {
        showFragment(new TestValueAnimationFragment());
    }
}
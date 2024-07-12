package com.hades.example.android.po.security;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.tools.FragmentUtils;

public class SecurityActivity extends AppCompatActivity {
    private static final String TAG = "SecurityActivity";
    ViewsVisibilityHelper visibilityHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        findViewById(R.id.pageAppChooser).setOnClickListener(v -> pageAppChooser());
        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }
        //  set enabled = false, handleOnBackPressed not invoked and directly back to previous activity.
        //  set enabled = true, handleOnBackPressed invoked
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (visibilityHelper.isBtnsHiden()) {
                    Log.d(TAG, "onCreate : handleOnBackPressed - hide fragment ");
                    visibilityHelper.showBtns();
                    FragmentUtils.remove(SecurityActivity.this, R.id.fragmentRoot);
                } else {
                    Log.d(TAG, "onCreate : handleOnBackPressed - finsh ");
                    finish();
                }
            }
        });
    }

    private void pageAppChooser() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new ShowAppChooserFragment(), ShowAppChooserFragment.class.getSimpleName());
    }
}

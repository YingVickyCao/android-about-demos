package com.hades.example.android.app_component._activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class ManageSystemUIActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dimStatusBar_and_or_NavigationIcons();
//        showSystemUI();
//        hideStatusBar();
        hideContentBehindStatusBar();
//        hideNavigationBar();

        setContentView(R.layout.activity_manage_system_ui);
    }

    /**
     * 调用 flag SYSTEM_UI_FLAG_LOW_PROFILE 来调暗状态栏和导航栏
     * 一旦用户触碰了状态栏和导航栏区域， 这个flag就会被清除，从而系统栏就会显示出来。
     * 只要flag被清除了，app就需要重设。
     */
    private void dimStatusBar_and_or_NavigationIcons() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    // Default
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;  // Show system UI(status bar + Navigation bar)
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {// < Android 4.1
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {// >=Android 4.1
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;  // Hide system UI(status bar)
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 一旦用户触碰了状态栏，这个flag就会被清除
     */
    private void hideContentBehindStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
    }


    /**
     * 触碰屏幕上的任意地方都会造成navigation bar重新显示并保持可见。flags会被清除。
     */
    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;  // Hide system UI(Navigation bar)
        decorView.setSystemUiVisibility(uiOptions);
    }

}

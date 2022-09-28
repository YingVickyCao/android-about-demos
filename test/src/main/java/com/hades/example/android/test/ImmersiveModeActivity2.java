package com.hades.example.android.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * https://developer.android.google.cn/training/system-ui/immersive
 * decorView.setSystemUiVisibility is depressed, use windowInsetsControllerCompat or windowInsetsController
 */
public class ImmersiveModeActivity2 extends AppCompatActivity {
    private static final String TAG = ImmersiveModeActivity2.class.getSimpleName();
    boolean isFullscreen = false;

    private View viewRoot;
    private TextView mMode;

    private Button enterFullScreen;
    private Button exitFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
//        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // Lay out your app in full screen
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_VideoPage);
        setContentView(R.layout.activity_immersive_mode_2);

        viewRoot = findViewById(R.id.viewRoot);
        mMode = findViewById(R.id.mode);

        findViewById(R.id.hideStatusBar).setOnClickListener(v -> hideStatusBar());
        findViewById(R.id.showStatusBar).setOnClickListener(v -> showStatusBar());
        findViewById(R.id.hideNavigationBar).setOnClickListener(v -> hideNavigationBar());
        findViewById(R.id.showNavigationBar).setOnClickListener(v -> showNavigationBar());

        enterFullScreen = findViewById(R.id.enterFullScreen);
        exitFullScreen = findViewById(R.id.exitFullScreen);

        exitFullScreen.setVisibility(View.GONE);
        enterFullScreen.setOnClickListener(v -> fullScreenOn());
        exitFullScreen.setOnClickListener(v -> fullScreenOff());

        // Step 2: Change the color of the system bars
        // way 2
        WindowInsetsControllerCompat windowInsetsControllerCompat = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (null != windowInsetsControllerCompat) {
            windowInsetsControllerCompat.setAppearanceLightStatusBars(true);
            windowInsetsControllerCompat.setAppearanceLightNavigationBars(true);
        }

        // System bars insets
        ViewCompat.setOnApplyWindowInsetsListener(viewRoot, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.leftMargin = insets.left;
            mlp.bottomMargin = insets.bottom;
            mlp.rightMargin = insets.right;
            v.setLayoutParams(mlp);

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            return WindowInsetsCompat.CONSUMED;
        });
        monitorSystemUI(); // 设置这个 system bar 的颜色就会改编
    }

    @Override
    protected void onResume() {
        super.onResume();
//        fullscreen_in_video_page();
    }

    private void hideStatusBar() {
        WindowInsetsControllerCompat windowInsetsControllerCompat = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (null != windowInsetsControllerCompat) {
            windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.statusBars());
        }
//        WindowInsetsCompat insetsCompat = WindowInsetsCompat.CONSUMED;
        // 获取状态栏和导航栏的高度.即使状态栏和导航栏处于隐藏状态，也不影响获取. 此处是WindowInsetsCompat.Type.statusBars()
//        insetsCompat.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.statusBars());

//        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // Lay out your app in full screen
    }

    private void showStatusBar() {
        WindowInsetsControllerCompat windowInsetsControllerCompat = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (null != windowInsetsControllerCompat) {
            windowInsetsControllerCompat.show(WindowInsetsCompat.Type.statusBars());
        }
    }

    private void hideNavigationBar() {
        WindowInsetsControllerCompat windowInsetsControllerCompat = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (null != windowInsetsControllerCompat) {
            windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.navigationBars());
        }
    }

    private void showNavigationBar() {
        WindowInsetsControllerCompat windowInsetsControllerCompat = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (null != windowInsetsControllerCompat) {
            windowInsetsControllerCompat.show(WindowInsetsCompat.Type.navigationBars());
        }
    }

    private void hideSystemUI() {
        WindowInsetsControllerCompat windowInsetsControllerCompat = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (null != windowInsetsControllerCompat) {
            windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.systemBars());
            windowInsetsControllerCompat.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE);
        }
    }

    private void showSystemUI() {
        WindowInsetsControllerCompat windowInsetsControllerCompat = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (null != windowInsetsControllerCompat) {
            windowInsetsControllerCompat.show(WindowInsetsCompat.Type.systemBars());
        }
    }


    private void fullScreenOn() {
        isFullscreen = true;
        hideSystemUI();
        Log.d(TAG, "hideSystemBar: ");
        enterFullScreen.setVisibility(View.GONE);
        exitFullScreen.setVisibility(View.VISIBLE);
        mMode.setText("Full screen");
    }

    private void fullScreenOff() {
        isFullscreen = false;
        Log.d(TAG, "showSysthideSystemUIemBar: ");
        showSystemUI();
        enterFullScreen.setVisibility(View.VISIBLE);
        exitFullScreen.setVisibility(View.GONE);
        mMode.setText("Portrait");
    }

    /**
     * 响应系统界面可见性变化
     */
    private void monitorSystemUI() {
//        View decorView = getWindow().getDecorView();
//        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
//            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                Log.d(TAG, "monitorSystemUI: system bar are visible");
//                // system bar are visible
//                if (isFullscreen) {
//                    hideSystemUI();
//                }
//            }
//        });

        ViewCompat.setOnApplyWindowInsetsListener(viewRoot, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                Log.d(TAG, "onApplyWindowInsets: ");
                if (insets.isVisible(WindowInsetsCompat.Type.systemBars())) {
                    if (isFullscreen) {
                        showSystemUI();
                    }
                }
//                return insets;
                return insets; // // Lay out your app in full screen
            }
        });
    }
}
package com.hades.example.android.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * // setSystemUiVisibility
 * // 仅仅在onCreate设置，用户按主屏幕按钮时，系统栏会重新出现。若在用户进入和推出 Activity 时继续保持系统更改，在onResume或 onWindowFocusChanged 中 设置界面标记
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    boolean fullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorSystemUI();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.btn).setOnClickListener(v -> {
//            if (fullscreen) {
//                showSystemBar();
//            } else {
//                hideSystemBar();
//            }
//        });
        findViewById(R.id.dim).setOnClickListener(v -> {
            dim_StatusBar_NavigationBar();
        });
        findViewById(R.id.show_StatusBar_NavigationBar).setOnClickListener(v -> {
            showStatusBar_NavigationBar();
        });
        findViewById(R.id.showStatusBar).setOnClickListener(v -> {

        });
        findViewById(R.id.hideStatusBar).setOnClickListener(v -> {
            hideStatusBar();
        });
        findViewById(R.id.hideStatusBar_2).setOnClickListener(v -> {
            hideStatusBar_2();
        });
        findViewById(R.id.hideNavigationBar).setOnClickListener(v -> {
            hideNavigationBar();
        });
        findViewById(R.id.hideNavigationBar_2).setOnClickListener(v -> {
            hideNavigationBar_2();
        });
        findViewById(R.id.fullscreen_1).setOnClickListener(v -> {
            fullscreen_1();
        });

        findViewById(R.id.fullscreen_2).setOnClickListener(v -> {
            fullscreen_2();
        });

        findViewById(R.id.fullscreen_3).setOnClickListener(v -> {
            fullscreen_3();
        });
        findViewById(R.id.fullscreen_in_video_page).setOnClickListener(v -> {
            fullscreen_in_video_page();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void dim_StatusBar_NavigationBar() {
        // 调暗status bar (状态栏) 和导航栏
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        /**
         * 测试结果：导航栏并没有变暗
         */
    }

    private void showStatusBar_NavigationBar() {
        // 调暗status bar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN     // 隐藏状态栏
                ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * Not working
     */
    private void hideStatusBar_2() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN     // 隐藏状态栏
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN        // 让内容显示在状态栏后面
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;                // 并保持界面稳定
        decorView.setSystemUiVisibility(uiOptions);
    }


    private void hideSystemBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        fullscreen = true;
    }

    /**
     * 轻触屏幕任何位置，导航栏重新出现并保持可见， 导航栏显示在布局的下方，因此布局随着导航栏的隐藏和显示调整大小
     */
    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN     // 隐藏导航栏
                ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 轻触屏幕任何位置，导航栏重新出现并保持可见， 导航栏盖在布局的上面，因此布局不会随着导航栏的隐藏和显示调整大小
     */
    private void hideNavigationBar_2() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN     // 隐藏导航栏
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION        // 让内容显示在导航栏后面
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;                // 并保持界面稳定
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showSystemBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        fullscreen = false;
    }

    /**
     * 全屏-向后倾斜模式
     * 当希望调出系统栏时，用户点击屏幕上当任何位置即可
     */
    private void fullscreen_1() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION     // 隐藏导航栏
                ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 全屏-普通沉浸模式
     * 当希望调出系统栏时，用户可从隐藏状态栏的任一边滑动
     */
    private void fullscreen_2() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION     // 隐藏导航栏
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 全屏-粘性沉浸模式
     * 当希望调出系统栏时，用户可从隐藏状态栏的任一边滑动, 系统栏会显示出来，但它们是透明的，盖在上面
     */
    private void fullscreen_3() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION     // 隐藏导航栏
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * Video page 采用全屏-粘性沉浸模式，并结合其他系统标志，防止布局随着系统栏的隐藏和显示调整大小
     */
    private void fullscreen_in_video_page() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION     // 隐藏导航栏
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 响应系统界面可见性变化
     */
    private void monitorSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                Log.d(TAG, "monitorSystemUI: system bar are visible");
                // system bar are visible
                if (fullscreen) {
                    hideNavigationBar();
                    hideStatusBar();
                }
            } else {
                // system bar are not visible
                Log.d(TAG, "monitorSystemUI: system bar not visible");
            }
        });
    }
}
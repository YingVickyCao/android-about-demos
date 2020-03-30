package com.hades.example.android.feature.multi_window;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.yingvickycao.autils.base.BaseActivity;
import com.hades.example.android.R;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.content.res.Configuration.ORIENTATION_UNDEFINED;

public class MultiWindowRootActivity extends BaseActivity {
    private static final String TAG = "MultiWindowRootActivity";

    private View pageRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.multi_window_root_activity);
        pageRoot = findViewById(R.id.pageRoot);

        findViewById(R.id.openA).setOnClickListener(v -> openA());
    }

    private void openA() {
//        showActivity(AActivity.class);
        Intent intent = new Intent(this, AActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        Log.d(TAG, "onMultiWindowModeChanged: isInMultiWindowMode=" + isInMultiWindowMode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (isInMultiWindowMode()) {
                pageRoot.setBackgroundColor(getColor(android.R.color.holo_blue_dark));
            } else {
                pageRoot.setBackgroundColor(getColor(android.R.color.holo_blue_light));
            }
        }
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
        Log.d(TAG, "onMultiWindowModeChanged: isInMultiWindowMode=" + isInMultiWindowMode + ",orientation=" + printOrientation(newConfig.orientation));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ,orientation=" + printOrientation(newConfig.orientation));
    }

    private String printOrientation(int orientation) {
        switch (orientation) {
            case ORIENTATION_UNDEFINED:
                return "UNDEFINED";

            case ORIENTATION_PORTRAIT:
                return "PORTRAIT";

            case ORIENTATION_LANDSCAPE:
                return "LANDSCAPE";
        }
        return "InValid";
    }
}
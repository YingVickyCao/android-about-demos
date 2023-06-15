package com.hades.example.android._feature.menu_manager.menu_page;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;
import com.hades.example.android._feature._web_based_contents.r8.J2v8Fragment;
import com.hades.example.android._feature._web_based_contents.webview.TestWebViewFragment;
import com.hades.example.android.tools.FragmentUtils;

import java.util.HashMap;
import java.util.Map;

public class MenuHandler extends Handler {
    private static final String TAG = "MenuHandler";
    final private Map<String, ISimpleCallback> mActions = new HashMap<>();
    private MenuActivity activity;

    public MenuHandler() {
        super(Looper.getMainLooper());
        registerAction();
    }

    public void setActivity(MenuActivity activity) {
        this.activity = activity;
    }

    private void registerAction() {
        mActions.put("r8", () -> {
            Fragment fragment = new J2v8Fragment();
            show(fragment);
        });
        mActions.put("webview", () -> {
            Fragment fragment = new TestWebViewFragment();
            show(fragment);
        });
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        String code = msg.getData().getString("code");
        if (null != code) {
            ISimpleCallback simpleCallback = mActions.get(code);
            if (null != simpleCallback) {
                simpleCallback.action();
            } else {
                Log.i(TAG, "handleMessage:no action for code " + code);
            }
        }
    }

    private void show(Fragment fragment) {
        if (null == activity) {
            return;
        }
        FragmentUtils.replaceFragment(activity, R.id.content, fragment, fragment.getClass().getSimpleName());
    }
}

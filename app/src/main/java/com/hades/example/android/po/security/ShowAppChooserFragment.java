package com.hades.example.android.po.security;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;

import java.util.List;

/**
 * 模拟分享功能：点击按钮，弹出可选列表
 * <a href="https://blog.csdn.net/oudetu/article/details/78443826">https://blog.csdn.net/oudetu/article/details/78443826</a>
 */
public class ShowAppChooserFragment extends BaseFragment {
    private static final String TAG = "ShowAppChooserFragment";
    OnBackPressedCallback onBackPressedCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_chooser_layout, container, false);
        view.findViewById(R.id.click).setOnClickListener(v -> click());
        return view;
    }

    private void click() {
        send();
    }

    private void send() {
        if (null == getActivity()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Fav music");
        List<ResolveInfo> activities = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        if (activities.size() > 1) {
            String title = "Share";
            Intent chooser = Intent.createChooser(intent, title);
            startActivity(chooser);
        } else if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
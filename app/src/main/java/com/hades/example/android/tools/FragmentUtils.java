package com.hades.example.android.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hades.example.android.other_ui._notification.TestNotificationFragment;

public class FragmentUtils {
    public FragmentUtils() {
    }

    public void addFragment(AppCompatActivity activity, Fragment fragment, String tag) {
        activity.getSupportFragmentManager().beginTransaction().add(fragment, tag).commit();
    }
}

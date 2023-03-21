package com.hades.example.android.tools;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class FragmentUtils {
    public FragmentUtils() {
    }

    public static void addFragment(FragmentActivity activity, Fragment fragment, String tag) {
        activity.getSupportFragmentManager().beginTransaction().add(fragment, tag).commit();
    }

    public static void replaceFragment(FragmentActivity activity, @IdRes int containerViewId, @NonNull Fragment fragment, @Nullable String tag) {
        activity.getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, tag).commit();
    }

    public static void replaceFragment(FragmentActivity activity, String backStackName, @IdRes int containerViewId, @NonNull Fragment fragment, @Nullable String tag) {
        activity.getSupportFragmentManager().beginTransaction().addToBackStack(backStackName).replace(containerViewId, fragment, tag).commit();
    }

    public static void removedFromParent(Fragment fragment) {
        fragment.getParentFragmentManager().beginTransaction().remove(fragment).commit();
    }
}

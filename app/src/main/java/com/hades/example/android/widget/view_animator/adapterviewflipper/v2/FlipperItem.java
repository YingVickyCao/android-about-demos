package com.hades.example.android.widget.view_animator.adapterviewflipper.v2;

import androidx.annotation.DrawableRes;

public class FlipperItem {
    @DrawableRes
    int resId;

    String name;

    public FlipperItem(int resId, String name) {
        this.resId = resId;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public String getName() {
        return name;
    }
}

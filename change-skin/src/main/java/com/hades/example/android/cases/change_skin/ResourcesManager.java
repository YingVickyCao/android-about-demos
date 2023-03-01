package com.hades.example.android.cases.change_skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.sql.Driver;

public class ResourcesManager {
    private Resources mResources;
    private String mPackageName;

    public ResourcesManager(Resources resources, String packageName) {
        mResources = resources;
        mPackageName = packageName;
    }

    public Drawable getDrawableByResourceName(String name) {
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, "drawable", mPackageName));
        } catch (Exception ex) {
            return null;
        }
    }

    public ColorStateList getColorByResName(String name) {
        try {
            return mResources.getColorStateList(mResources.getIdentifier(name, "color", mPackageName));
        } catch (Exception ex) {
            return null;
        }
    }
}

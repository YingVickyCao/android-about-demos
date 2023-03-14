package com.hades.example.android.tools.permission;

import android.app.Activity;

public interface IRequestPermissionCallback {
    Activity getContext();

    String getPermissionRationale();

    String[] getPermissions();

    void showRationaleContextUI();

    void allow();

    void notAllow();

    void skip();
}

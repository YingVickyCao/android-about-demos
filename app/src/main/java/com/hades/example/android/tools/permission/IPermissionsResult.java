package com.hades.example.android.tools.permission;

import android.util.Log;

public interface IPermissionsResult {
    void allow();

    default void notAllow() {

    }

    default void error(Exception ex, String... permissions) {
        Log.d(PermissionTools.TAG, permissions.toString() + ",error:" + ex);
    }
}

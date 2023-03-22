package com.hades.example.android.tools.permission;

import android.util.Log;

public interface IPermissionsResult {
    void granted();

    default void denied() {

    }

    default void error(Exception ex, String... permissions) {
        Log.d(PermissionTools.TAG, permissions + ",error:" + ex);
    }
}

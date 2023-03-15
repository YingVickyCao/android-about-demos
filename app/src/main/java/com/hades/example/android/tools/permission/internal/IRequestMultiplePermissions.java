package com.hades.example.android.tools.permission.internal;

import java.util.Map;

public interface IRequestMultiplePermissions {
    void onResult(Map<String, Boolean> permissionsResult);
}
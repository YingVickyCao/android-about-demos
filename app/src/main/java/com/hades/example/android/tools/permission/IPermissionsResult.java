package com.hades.example.android.tools.permission;

import java.util.List;
import java.util.Map;

public interface IPermissionsResult {
    void onResult(Map<String, Boolean> permissionsResult);
}

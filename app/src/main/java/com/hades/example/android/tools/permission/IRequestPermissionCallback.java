package com.hades.example.android.tools.permission;


import java.util.List;

public interface IRequestPermissionCallback extends IRequestPermissionsResult {

    void showRationaleContextUI(List<String> rationalePermissions, IRequestPermissionRationaleResult callback);
}

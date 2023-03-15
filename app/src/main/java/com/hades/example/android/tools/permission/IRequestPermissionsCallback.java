package com.hades.example.android.tools.permission;


import java.util.List;

public interface IRequestPermissionsCallback extends IPermissionsResult {

    void showRationaleContextUI(List<String> rationalePermissions, IRationaleOnClickListener callback);
}

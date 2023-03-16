package com.hades.example.android.tools.permission;


import java.util.List;

public interface IRequestPermissionsCallback extends IPermissionsResult {
    default boolean isAlwaysShowRationaleContextUI() {
        return false;
    }

    void showRationaleContextUI(List<String> rationalePermissions, IRationaleOnClickListener callback);

    default void cancel() {

    }
}

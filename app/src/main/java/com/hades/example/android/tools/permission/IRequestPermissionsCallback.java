package com.hades.example.android.tools.permission;


public interface IRequestPermissionsCallback extends IPermissionsResult {
    default void showRationaleContextUI(IRationaleOnClickListener callback) {

    }

    default void cancel() {

    }
}

package com.hades.example.android.tools.permission;

public interface IPermissionTools {
    void request(IRequestPermissionsCallback callback, final String... permissions);

    public boolean isGranted(final String... permissions);

    boolean shouldShowRequestPermissionRationale(final String... permissions);

    void requestPermission(IPermissionsResult callback, final String... permissions);
}

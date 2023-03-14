package com.hades.example.android.tools.permission;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;

public class PermissionTools {
    public static final String TAG = "PermissionTools";
    IRequestPermissionCallback mCallback;
    private String[] permissions;

    private AppCompatActivity mActivity;
    private PermissionsFragment mPermissionsFragment;

    public PermissionTools(AppCompatActivity activity) {
        mActivity = activity;
        mPermissionsFragment = getRxPermissionsFragment(activity);
    }

    private PermissionsFragment getRxPermissionsFragment(AppCompatActivity activity) {
        PermissionsFragment rxPermissionsFragment = findPermissionsFragment(activity);
        boolean isNewInstance = (rxPermissionsFragment == null);
        if (isNewInstance) {
            rxPermissionsFragment = new PermissionsFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(rxPermissionsFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return rxPermissionsFragment;
    }

    private PermissionsFragment findPermissionsFragment(AppCompatActivity activity) {
        return (PermissionsFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    private static final int REQUEST_CODE_4_REQUEST_PERMISSIONS = 2000;

    public void checkPermission(IRequestPermissionCallback callback) {
        mCallback = callback;
        if (isGranted(callback.getPermissions())) {
            mCallback.allow();
            return;
        }

        if (shouldShowRequestPermissionRationale(callback.getPermissions())) {
//            Snackbar.make(, permissionRationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(com.hades.example.android.lib.R.string.ok, view -> requestPermission(permissions))
//                    .show();
            showRationaleContextUI();
        } else {
            requestPermission(callback.getPermissions());
        }
    }

    protected boolean isGranted(final String... permissions) {
        if (permissions == null) {
            throw new RuntimeException("permissions is null");
        }

        for (String permission : permissions) {
            if (!isGranted(permission)) {
                return false;
            }
        }
        return true;
    }

    private boolean isGranted(final String permission) {
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mCallback.getContext(), permission));
    }

    private boolean shouldShowRequestPermissionRationale(final String... permissions) {
        for (String p : permissions) {
            if (!isGranted(p) && !ActivityCompat.shouldShowRequestPermissionRationale(mCallback.getContext(), p)) {
                return false;
            }
        }
        return true;
    }

    void showRationaleContextUI() {
        Snackbar.make(, mCallback.getPermissionRationale(), Snackbar.LENGTH_INDEFINITE
                ).setAction(com.hades.example.android.lib.R.string.ok, view -> requestPermission(permissions))
                .show();
    }

    public void showSnackBarAsRationale(View rootView) {
        Snackbar.make(rootView, mCallback.getPermissionRationale(), Snackbar.LENGTH_INDEFINITE)
                .setAction(mActivity.getString(R.string.ok), view -> requestPermission(permissions))
                .setAction(mActivity.getString(R.string.cancel), view -> notAllow())
                .show();
    }

    public void showDialogAsRationale(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Request permission")
                .setMessage(message)
                .setPositiveButton(mActivity.getString(R.string.ok), (dialog, which) -> requestPermission(permissions))
                .setNegativeButton(mActivity.getString(R.string.cancel), (dialog, which) -> notAllow())
                .create()
                .show();
    }

    private void allow() {
        mCallback.allow();
    }

    private void notAllow() {
        mCallback.notAllow();
    }

    protected void requestPermission(final String... permissions) {
        mPermissionsFragment.requestPermissions(createPermissionResult()).launch(permissions);
    }

    protected void requestPermission(final String permission) {
        mPermissionsFragment.requestPermission(createPermissionResult()).launch(permission);
    }

    private IPermissionResult createPermissionResult() {
        return new IPermissionResult() {
            @Override
            public void granted() {
                allow();
            }

            @Override
            public void denied() {
                notAllow();
            }
        };
    }

}
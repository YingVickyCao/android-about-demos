package com.hades.example.android.tools.permission;

import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PermissionTools {
    public static final String TAG = "PermissionTools";
    IRequestPermissionCallback mCallback;

    private AppCompatActivity mActivity;
    private PermissionsFragment mPermissionsFragment;

    public PermissionTools(AppCompatActivity activity) {
        mActivity = activity;
        mPermissionsFragment = getRxPermissionsFragment(activity);
    }

    private PermissionsFragment getRxPermissionsFragment(AppCompatActivity activity) {
        PermissionsFragment permissionsFragment = findPermissionsFragment(activity);
        boolean isNewInstance = (permissionsFragment == null);
        if (isNewInstance) {
            permissionsFragment = new PermissionsFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().add(permissionsFragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return permissionsFragment;
    }

    private PermissionsFragment findPermissionsFragment(AppCompatActivity activity) {
        return (PermissionsFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    public void request(IRequestPermissionCallback callback, final String... permissions) {
        mCallback = callback;

        List<Permission> list = new ArrayList<>(permissions.length);
        String[] unrequestedPermissions = new String[permissions.length];
        int indexOfUnrequestedPermissions = 0;
        List<String> rationalePermissions = new ArrayList<>(permissions.length);

        for (String permission : permissions) {
            mPermissionsFragment.log("Requesting permission " + permission);
            if (isGranted(permission)) {
                list.add(new Permission(permission, true, false));
            } else if (isRevoked(permission)) {
                list.add(new Permission(permission, false, false));
            } else if (shouldShowRequestPermissionRationale()) {
                rationalePermissions.add(permission);
                unrequestedPermissions[indexOfUnrequestedPermissions] = permission;
                indexOfUnrequestedPermissions++;
            } else {
                unrequestedPermissions[indexOfUnrequestedPermissions] = permission;
                indexOfUnrequestedPermissions++;
//                rationalePermissions.add(permission);
            }
        }

        if (isShowRationale(rationalePermissions)) {
            mCallback.showRationaleContextUI(rationalePermissions, new IRequestPermissionRationaleResult() {
                @Override
                public void allow() {
                    requestPermissions(unrequestedPermissions, list, mCallback);
                }

                @Override
                public void notAllow() {
                    mCallback.denied();
                }

                @Override
                public void skip() {
                    mCallback.denied();
                }
            });
        } else {
            if (!isShouldRequestPermissions(unrequestedPermissions)) {
                requestPermissions(unrequestedPermissions, list, mCallback);
            } else {
                check(list, mCallback);
            }
        }
    }

    private boolean isShowRationale(List<String> rationalePermissions) {
        return null != rationalePermissions && !rationalePermissions.isEmpty();
//        return true;
    }

    private boolean isShouldRequestPermissions(String[] unrequestedPermissions) {
        return null != unrequestedPermissions && unrequestedPermissions.length > 0;
    }

    private void requestPermissions(String[] unrequestedPermissions, List<Permission> list, IRequestPermissionsResult callback) {
        if (isShouldRequestPermissions(unrequestedPermissions)) {
            mPermissionsFragment.setCallback(new IPermissionsResult() {
                @Override
                public void onResult(Map<String, Boolean> permissionsResult) {
                    permissionsResult.forEach((permission, isGranted) -> {
                        Log.d(TAG, "accept: " + permission + "," + isGranted);
                        mPermissionsFragment.log(permission + "--" + isGranted);
                        list.add(new Permission(permission, isGranted));
                    });
                    for (Permission v : list) {
                        if (!v.granted) {
                            callback.denied();
                            return;
                        }
                    }
                    callback.granted();
                }
            });
            mPermissionsFragment.getResultLauncher().launch(unrequestedPermissions);
        }
    }

    private void check(List<Permission> list, IRequestPermissionsResult callback) {
        for (Permission v : list) {
            if (!v.granted) {
                callback.denied();
                return;
            }
        }
        callback.granted();
    }

    public boolean isGranted(final String... permissions) {
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
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mActivity, permission);
    }

    private boolean isRevoked(String permission) {
        return mActivity.getPackageManager().isPermissionRevokedByPolicy(permission, mActivity.getPackageName());
    }

    public boolean shouldShowRequestPermissionRationale(final String... permissions) {
        for (String p : permissions) {
            if (shouldShowPermissionRequestPermissionRationale(p)) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldShowPermissionRequestPermissionRationale(final String permission) {
        return !isGranted() && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
    }

    public void requestPermission(IRequestPermissionsResult callback, final String... permissions) {
        List<Permission> results = new ArrayList<>();
        requestPermissions(permissions, results, callback);
    }
}
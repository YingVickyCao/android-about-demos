package com.hades.example.android.tools.permission;

import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.hades.example.android.tools.permission.internal.IRequestMultiplePermissions;
import com.hades.example.android.tools.permission.internal.Permission;
import com.hades.example.android.tools.permission.internal.PermissionsFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PermissionTools implements IPermissionTools {
    public static final String TAG = "PermissionTools";

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

    @Override
    public void request(IRequestPermissionsCallback callback, final String... permissions) {
        try {
            List<Permission> list = new ArrayList<>(permissions.length);
            String[] unrequestedPermissions = new String[permissions.length];
            int indexOfUnrequestedPermissions = 0;
            List<String> rationalePermissions = new ArrayList<>(permissions.length);

            for (String permission : permissions) {
                mPermissionsFragment.log("Requesting permission " + permission);
                if (isGranted(permission)) {
                    list.add(new Permission(permission, true));
                } else if (isRevoked(permission)) {
                    list.add(new Permission(permission, false));
                } else if (shouldShowRequestPermissionRationale()) {
                    rationalePermissions.add(permission);
                    unrequestedPermissions[indexOfUnrequestedPermissions] = permission;
                    indexOfUnrequestedPermissions++;
                } else {
                    unrequestedPermissions[indexOfUnrequestedPermissions] = permission;
                    indexOfUnrequestedPermissions++;
                }
            }

            if (isShowRationale(rationalePermissions) || callback.isAlwaysShowRationaleContextUI()) {
                callback.showRationaleContextUI(rationalePermissions, new IRationaleOnClickListener() {
                    @Override
                    public void clickOK() {
                        requestPermissions(unrequestedPermissions, list, callback);
                    }

                    @Override
                    public void clickCancel() {
                        callback.cancel();
                    }

                    @Override
                    public void clickSkip() {
                        callback.cancel();
                    }
                });
            } else {
                if (isShouldRequestPermissions(unrequestedPermissions)) {
                    requestPermissions(unrequestedPermissions, list, callback);
                } else {
                    check(list, callback);
                }
            }
        } catch (Exception ex) {
            callback.error(ex, permissions);
        }
    }

    private boolean isShowRationale(List<String> rationalePermissions) {
        return null != rationalePermissions && !rationalePermissions.isEmpty();
    }

    private boolean isShouldRequestPermissions(String[] unrequestedPermissions) {
        return null != unrequestedPermissions && unrequestedPermissions.length > 0;
    }

    private void requestPermissions(String[] unrequestedPermissions, List<Permission> list, IPermissionsResult callback) {
        if (isShouldRequestPermissions(unrequestedPermissions)) {
            mPermissionsFragment.setCallback(new IRequestMultiplePermissions() {
                @Override
                public void onResult(Map<String, Boolean> permissionsResult) {
                    permissionsResult.forEach((permission, isGranted) -> {
                        Log.d(TAG, "accept: " + permission + "," + isGranted);
                        mPermissionsFragment.log(permission + "--" + isGranted);
                        list.add(new Permission(permission, isGranted));
                    });
                    for (Permission v : list) {
                        if (!v.granted) {
                            callback.notAllow();
                            return;
                        }
                    }
                    callback.allow();
                }
            });
            mPermissionsFragment.getResultLauncher().launch(unrequestedPermissions);
        }
    }

    private void check(List<Permission> list, IPermissionsResult callback) {
        for (Permission v : list) {
            if (!v.granted) {
                callback.notAllow();
                return;
            }
        }
        callback.allow();
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

    public void requestPermission(IPermissionsResult callback, final String... permissions) {
        try {
            List<Permission> results = new ArrayList<>();
            requestPermissions(permissions, results, callback);
        } catch (Exception ex) {
            callback.error(ex, permissions);
        }
    }
}
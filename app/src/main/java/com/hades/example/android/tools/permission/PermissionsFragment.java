package com.hades.example.android.tools.permission;

import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.util.Map;
import java.util.function.BiConsumer;

public class PermissionsFragment extends Fragment {
    private static final String TAG = "PermissionsFragment";
    private static final int PERMISSIONS_REQUEST_CODE = 42;
    private boolean mLogging;

    public void setLogging(boolean logging) {
        mLogging = logging;
    }

    //    ActivityResultLauncher<String[]> requestPermissions(@NonNull String[] permissions) {
    ActivityResultLauncher<String[]> requestPermissions(final IPermissionResult permissionsResultCallback) {
        return registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> permissionsResult) {
                permissionsResult.forEach(new BiConsumer<String, Boolean>() {
                    @Override
                    public void accept(String permission, Boolean isGranted) {
                        Log.d(TAG, "accept: " + permission + "," + isGranted);
                        log(permission + "--" + isGranted);
                        if (!isGranted) {
                            permissionsResultCallback.denied();
                            return;
                        }
                    }
                });
                permissionsResultCallback.granted();
            }
        });
    }

    //    ActivityResultLauncher<String> requestPermissions(@NonNull String permission) {
    ActivityResultLauncher<String> requestPermission(final IPermissionResult permissionsResultCallback) {
        return registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean granted) {
                if (granted) {
                    permissionsResultCallback.granted();
                } else {
                    permissionsResultCallback.denied();
                }
            }
        });
    }

    void log(String message) {
        if (mLogging) {
            Log.d(PermissionTools.TAG, message);
        }
    }
}

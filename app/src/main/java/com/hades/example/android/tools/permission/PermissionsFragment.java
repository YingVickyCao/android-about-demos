package com.hades.example.android.tools.permission;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Map;

public class PermissionsFragment extends Fragment {
    private static final String TAG = "PermissionsFragment";
    private boolean mLogging;

    ActivityResultLauncher<String[]> mResultLauncher;
    IPermissionsResult mCallback;

    public void setLogging(boolean logging) {
        mLogging = logging;
    }

    public void setCallback(IPermissionsResult callback) {
        this.mCallback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mResultLauncher = requestPermissions();
        super.onCreate(savedInstanceState);
    }

    //    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }

    public ActivityResultLauncher<String[]> getResultLauncher() {
        return mResultLauncher;
    }

    /*
Error: java.lang.IllegalStateException: Fragment PermissionsFragment{b17aadc} (0b3ab832-6d7d-431b-8b4d-e51d4ffec16d tag=PermissionTools) is attempting to registerForActivityResult after being created.
Fragments must call registerForActivityResult() before they are created (i.e. initialization, onAttach(), or onCreate()).

Fix :https://blog.csdn.net/jingzz1/article/details/108142784

 */
//    ActivityResultLauncher<String[]> requestPermissions(final IPermissionsResult callback) {
//        return registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
//            @Override
//            public void onActivityResult(Map<String, Boolean> permissionsResult) {
//                callback.onResult(permissionsResult);
//            }
//        });
//    }

    private ActivityResultLauncher<String[]> requestPermissions() {
        return registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> permissionsResult) {
                mCallback.onResult(permissionsResult);
            }
        });
    }

    //    ActivityResultLauncher<String> requestPermissions(@NonNull String permission) {
    ActivityResultLauncher<String> requestPermission(final IPermissionResult callback) {
        return registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean granted) {
                callback.onResult(granted);
//                if (granted) {
//                    permissionsResultCallback.granted();
//                } else {
//                    permissionsResultCallback.denied();
//                }
            }
        });
    }

    void log(String message) {
        if (mLogging) {
            Log.d(PermissionTools.TAG, message);
        }
    }
}

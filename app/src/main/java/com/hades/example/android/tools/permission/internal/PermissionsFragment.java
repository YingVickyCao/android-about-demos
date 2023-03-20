package com.hades.example.android.tools.permission.internal;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.tools.permission.IPermissionsResult;
import com.hades.example.android.tools.permission.PermissionTools;

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
                if (isAllGranted(permissionsResult)) {
                    mCallback.allow();
                } else {
                    mCallback.notAllow();
                }
            }
        });
    }

    private boolean isAllGranted(Map<String, Boolean> permissionsResult) {
        if (null == permissionsResult || permissionsResult.isEmpty()) {
            return false;
        }

        for (Boolean value : permissionsResult.values()) {
            if (Boolean.FALSE.equals(value)) {
                return false;
            }
        }
        return true;
    }

    public void requestMultiplePermissions(String... unrequestedPermissions) {
        if (!isPermissionsNotEmpty(unrequestedPermissions)) {
            mCallback.error(new Exception("Not permission to request"), null);
            return;
        }
        mResultLauncher.launch(unrequestedPermissions);
    }

    private boolean isPermissionsNotEmpty(String[] unrequestedPermissions) {
        return null != unrequestedPermissions && unrequestedPermissions.length > 0;
    }

    public void log(String message) {
        if (mLogging) {
            Log.d(PermissionTools.TAG, message);
        }
    }
}

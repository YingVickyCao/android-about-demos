package com.hades.example.android.tools.permission;

import android.Manifest;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

import java.util.List;

public class PermissionToolsTest {
    public void test(AppCompatActivity activity) {
        PermissionTools permissionTools = new PermissionTools(activity);
        permissionTools.request(new IRequestPermissionsCallback() {
            @Override
            public void showRationaleContextUI(List<String> rationalePermissions, IRationaleOnClickListener rationaleOnClickListener) {
                if (null == rationalePermissions || rationalePermissions.isEmpty()) {
                    return;
                }
                Log.d(PermissionTools.TAG, "showRationaleContextUI: " + rationalePermissions);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Request permission")
                        .setMessage("Permission " + rationalePermissions)
                        .setPositiveButton(activity.getString(R.string.ok), (dialog, which) -> rationaleOnClickListener.allow())
                        .setNegativeButton(activity.getString(R.string.cancel), (dialog, which) -> rationaleOnClickListener.notAllow())
                        .setNeutralButton(activity.getString(R.string.skip), (dialog, which) -> rationaleOnClickListener.skip())
                        .create()
                        .show();
            }

            @Override
            public void granted() {
                Toast.makeText(activity, "Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void denied() {
                Toast.makeText(activity, "Denied", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void test2(AppCompatActivity activity) {
        PermissionTools permissionTools = new PermissionTools(activity);
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (permissionTools.isGranted(permissions)) {
            Toast.makeText(activity, "Granted", Toast.LENGTH_SHORT).show();
            return;
        }
        if (permissionTools.shouldShowRequestPermissionRationale(permissions)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Request permission")
                    .setMessage("Permission " + permissions.toString())
                    .setPositiveButton(activity.getString(R.string.ok), (dialog, which) -> requestPermission(activity, permissionTools, permissions))
                    .setNegativeButton(activity.getString(R.string.cancel), (dialog, which) -> Toast.makeText(activity, "Denied", Toast.LENGTH_SHORT).show())
                    .setNeutralButton(activity.getString(R.string.skip), (dialog, which) -> Toast.makeText(activity, "Denied", Toast.LENGTH_SHORT).show())
                    .create()
                    .show();
        } else {
            requestPermission(activity, permissionTools, permissions);
        }
    }

    void requestPermission(AppCompatActivity activity, PermissionTools permissionTools, final String... permissions) {
        permissionTools.requestPermission(new IPermissionsResult() {
            @Override
            public void granted() {
                Toast.makeText(activity, "Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void denied() {
                Toast.makeText(activity, "Denied", Toast.LENGTH_SHORT).show();
            }
        }, permissions);

    }
}

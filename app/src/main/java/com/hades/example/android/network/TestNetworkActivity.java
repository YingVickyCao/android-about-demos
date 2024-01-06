package com.hades.example.android.network;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.network.tcp_ip.multi_thread_client.MultiThreadClientActivity;
import com.hades.example.android.network.tcp_ip.one_thread_client.SimpleClientActivity;
import com.hades.example.android.network.url.http_url_connection.MultiThreadDownFragment;
import com.hades.example.android.network.url.url.TestURLFragment;
import com.hades.example.android.network.url.url_connection.TestURLConnectionFragment;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnResultCallback;
import com.hades.utility.permission.PermissionsTool;

public class TestNetworkActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.network_layout);

        findViewById(R.id.pageSimpleSocketClient).setOnClickListener(v -> pageSimpleSocketClient());
        findViewById(R.id.pageMultiThreadSocketClient).setOnClickListener(v -> pageMultiThreadSocketClient());
        findViewById(R.id.pageURL).setOnClickListener(v -> pageURL());
        findViewById(R.id.pageURLConnection).setOnClickListener(v -> pageURLConnection());
        findViewById(R.id.pageHttpURLConnection).setOnClickListener(v -> pageHttpURLConnection());
        requestPermission();
    }

    private void requestPermission() {
        // FIXED_ERROR:java.io.FileNotFoundException: /sdcard/bg004.JPG: open failed: EACCES (Permission denied)
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new OnResultCallback() {
            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request SD Card permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }

            @Override
            public void granted() {
                Toast.makeText(TestNetworkActivity.this, "SD Card permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void denied() {

            }

            @Override
            public void onError(String message) {

            }
        });
    }


    @Override
    protected void showCurrentTest() {
        pageHttpURLConnection();
    }

    @Override
    protected boolean isNeedCheckPermission() {
        return true;
    }

    private void pageSimpleSocketClient() {
        showActivity(SimpleClientActivity.class);
    }

    private void pageMultiThreadSocketClient() {
        showActivity(MultiThreadClientActivity.class);
    }

    private void pageURL() {
        showFragment(new TestURLFragment());
    }

    private void pageURLConnection() {
        showFragment(new TestURLConnectionFragment());
    }

    private void pageHttpURLConnection() {
        showFragment(new MultiThreadDownFragment());
    }
}
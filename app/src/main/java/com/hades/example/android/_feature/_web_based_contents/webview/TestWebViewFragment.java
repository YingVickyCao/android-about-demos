package com.hades.example.android._feature._web_based_contents.webview;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.PermissionsTool;

public class TestWebViewFragment extends BaseFragment {
    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_webview, container, false);
        mWebView = view.findViewById(R.id.webView);
//        requestPermission();
        return view;
    }


    private void requestPermission() {
        // FIXED_ERROR:java.io.FileNotFoundException: /sdcard/bg004.JPG: open failed: EACCES (Permission denied)
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new OnPermissionResultCallback() {

            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(mWebView.findViewById(R.id.root), "Request SD Card permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }

            @Override
            public void onPermissionGranted() {
                Toast.makeText(getActivity(), "SD Card permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(getActivity(), "SD Card permission denied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionError(String message) {

            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.getSettings().setJavaScriptEnabled(true);

        // asserts folder
//        mWebView.loadUrl("file:///android_asset/web/maven.html");

        // asserts folder
//        mWebView.loadUrl("file:///android_asset/web/full/index.html"); // not ok. Can Swipe pages up/down,but content is white
//        mWebView.loadUrl("file:///android_asset/web/full/index.html?page=1");// not ok. Can Swipe pages up/down,but content is white
//        mWebView.loadUrl("file:///android_asset/web/full/1.html");// not ok, page 1 is showed,but sth is lost, e.g.,bg or grid

        // /sdcard/maven.html
//        mWebView.loadUrl("file:///" + Environment.getExternalStorageDirectory().getPath() + "/maven.html");// ok
//        mWebView.loadUrl("file:///sdcard/maven.html"); // ok
//        mWebView.loadUrl("file:///sdcard/full/index.html"); // not ok. Can Swipe pages up/down,but content is white
//        mWebView.loadUrl("file:///sdcard/full/index.html?page=1"); // not ok. Can Swipe pages up/down,but content is white
        mWebView.loadUrl("file:///sdcard/full/1.html"); // not ok, page 1 is showed,but sth is lost, e.g.,bg or grid
    }
}
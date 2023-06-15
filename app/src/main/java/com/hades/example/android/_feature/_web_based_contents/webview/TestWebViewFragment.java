package com.hades.example.android._feature._web_based_contents.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;

public class TestWebViewFragment extends BaseFragment {
    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_webview, container, false);
        mWebView = view.findViewById(R.id.webView);
        return view;
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
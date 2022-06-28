package com.hades.example.android.test_libs.okhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestHeader {
    private static final String TAG = "TestHeader";

    public void run(IOkHttpExampleView listener) {
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                /**
                 * header(name, value): 同一个Key，只能有一个值
                 */
                .header("User-Agent", "OkHttp Headers.java")
                .header("User-Agent", "OkHttp Call.java")
                /**
                 *addHeader(name, value):同一个Key，可以有多个值
                 */
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        /*
         User-Agent: OkHttp Call.java
         Accept: application/json; q=0.5
         Accept: application/vnd.github.v3+json
         */
        Log.d(TAG, "run: request header:" + request.headers());
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        if (!response.isSuccessful()) {
            return;
        }

        /**
         * 获取所有header
         */
        Log.d(TAG, "All: " + response.headers());
        /**
         * header(name):返回一个string 值
         */
        // server: GitHub.com
        Log.d(TAG, "Server:" + response.header("Server"));
        // Vary:Accept, Accept-Encoding, Accept, X-Requested-With
        Log.d(TAG, "Vary:" + response.header("vary"));
        /**
         * headers(name):返回一个值列表
         */
        // Vary:[Accept, Accept-Encoding, Accept, X-Requested-With]
        Log.d(TAG, "Vary:" + response.headers("vary"));
    }
}
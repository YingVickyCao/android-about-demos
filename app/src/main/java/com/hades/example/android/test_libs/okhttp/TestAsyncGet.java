package com.hades.example.android.test_libs.okhttp;

import static com.hades.example.android.test_libs.okhttp.OkHttpExampleConstants.URL_1;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// 异步的 Get
public class TestAsyncGet {
    private static final String TAG = "TestAsyncGet";

    public void run(IOkHttpExampleView listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_1)
                .build();

        Log.d(TAG, "run: --> ");
        listener.showLoading();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "onFailure: --->");
                        Log.d(TAG, "onFailure: Async get file failed! url:"+URL_1+ ",ex:" + e.getMessage());
                        Log.d(TAG, "onFailure: <--");
                        listener.hideLoading();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d(TAG, "onResponse: --->");
                        if (!response.isSuccessful()) {
                            Log.d(TAG, "onResponse: OkHttp async get failed!url:"+URL_1);
                        }
                        Headers headers = response.headers();
                        for (int i = 0; i < headers.size(); i++) {
//                            System.out.println("headers:"+headers.name(i)+","+headers.value(i));
                        }
//                        System.out.println(response.body().string());
                        Log.d(TAG, "onResponse: <--");
                        listener.hideLoading();
                    }
                });
        Log.d(TAG, "run: <---");
    }
}

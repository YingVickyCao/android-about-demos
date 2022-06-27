package com.hades.example.android.test_libs.okhttp;

import static com.hades.example.android.test_libs.okhttp.OkHttpExampleConstants.URL_1;

import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 同步的 Get
 */
public class TestSyncGet {
    private static final String TAG = "TestSyncGet";
    public void run(IOkHttpExampleView listener) {
        // Download a file, print its headers, and print its response body as a string.

        Log.d(TAG, "run: "+Thread.currentThread().getName()); // Thread-4
        
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_1)
                .build();
        listener.showLoading();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.d(TAG, "run: OkHttp sync get failed!url:"+URL_1);
                listener.hideLoading();
                return;
            }
            Headers headers = response.headers();
            for (int i = 0; i < headers.size(); i++) {
                /*
                    headers:Server,nginx/1.10.3 (Ubuntu)
                    headers:Date,Sat, 25 Jun 2022 09:47:04 GMT
                    headers:Content-Type,text/plain
                    headers:Content-Length,1759
                    headers:Last-Modified,Tue, 27 May 2014 02:35:47 GMT
                    headers:Connection,keep-alive
                    headers:ETag,"5383fa03-6df"
                    headers:Accept-Ranges,bytes
                */
                Log.d(TAG, "run: headers:"+ headers.name(i) + "," + headers.value(i));
            }

            /**
             * string():整个response变成string。
             * 适合：小文件或小段文本，大小<1MB。否则使用流处理body，避免 OutOfMemoryErro。
             */
            Log.d(TAG, "run: body:"+ response.body().string());
            Log.d(TAG, "run: "+Thread.currentThread().getName());   // Thread-4
            listener.hideLoading();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            listener.hideLoading();
        }
    }
}

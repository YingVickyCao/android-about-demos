package com.hades.example.android._feature.apk_upgrade;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpNetManager implements INetManager {
    private static final String TAG = "OkHttpNetManager";
    private static OkHttpClient sOkHttpClient;
    // 因为在UI线程更新UI，所以，要和UI线程绑定，因此，传入MainLooper
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(2, TimeUnit.MINUTES);
        sOkHttpClient = builder.build();
        // http
        // Q ： https Android >=9 自签名，Okhttp握手的错误
        // A ： builder.sslSocketFactory()设置证书的操作
        // <base-config cleartextTrafficPermitted="true" />
    }

    @Override
    public void get(String url, INetCallback callback) {
        // Request.Builder -> Request -> Call -> execute / enqueue -> UI
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().build(); // Get 请求
        Call call = sOkHttpClient.newCall(request);
//            Response response = call.execute(); // 同步操作：在当前线程中，返回请求
        call.enqueue(new Callback() { // 异步操作：入队列，每一个请求建立一个线程，然后入队。
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // ERROR: java.net.ConnectException: Failed to connect to /59.110.162.30:80
                // Caused by: java.net.ConnectException: failed to connect to /59.110.162.30 (port 80) from /:: (port 0) after 15000ms: connect failed: ENETUNREACH (Network is unreachable)
                // Caused by: android.system.ErrnoException: connect failed: ENETUNREACH (Network is unreachable)
                Log.e(TAG, "onFailure: ", e);
                // 非UI线程
                // 希望是UI线程，这样能直接更新线程
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.fail();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String str = response.body().string();
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.success(str);
                        }
                    });
                } catch (Exception ex) {
                    Log.e(TAG, "onResponse: failed" + ex.getMessage());
                    callback.fail();
                }
            }
        });
    }

    @Override
    public void download(String url, File targetFile, INetDownloadCallBack callBack) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: ", e);
                callBack.fail();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    // SonarQube：脆弱性
                    if (!targetFile.exists()) {
                        targetFile.getParentFile().mkdirs();
                    }

                    try (InputStream is = response.body().byteStream();
                         OutputStream os = new FileOutputStream(targetFile);) {
                        long totalLength = response.body().contentLength();
                        long currentLength = 0;
                        int bufferLength = 0;
//                        byte[] buffer = new byte[8 * 1024]; // 这个apk太小了，一个循环就下载完了，看不出进度更新，因此，把buffer改小一点/
                        byte[] buffer = new byte[8];
                        int oldProgress = 0;
                        while (-1 != (bufferLength = is.read(buffer))) {
                            os.write(buffer, 0, bufferLength);
                            os.flush();
                            currentLength += bufferLength;
                            int progress = (int) (currentLength * 1.0f / totalLength * 100); // 1.0 f ?  一个小数 除以 一个大数会为0。加上1.0f,得到的值就不是0了。
                            if (progress > oldProgress) {// 防止同一个progress，被UI更新了多次
                                oldProgress = progress;
                                sHandler.post(() -> callBack.progress(progress));
                            }
                        }
                        // 若存在sdcard，不需要这三个权限申请
                        targetFile.setExecutable(true, false);
                        targetFile.setReadable(true, false);
                        targetFile.setWritable(true, false);
                        sHandler.post(() -> callBack.success(targetFile));
                    }
                } catch (Exception exception) {
                    callBack.fail();
                }
            }
        });
    }
}

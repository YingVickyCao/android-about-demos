package com.hades.example.android.test.apk_upgrade;

import java.io.File;

public interface INetManager {
    void get(String url, INetCallback callback, Object tag);

    void download(String url, File targetFile, INetDownloadCallBack callBack, Object tag);

    void cancel(Object tag);
}

package com.hades.example.android.test.apk_upgrade;

import java.io.File;

public interface INetDownloadCallBack {
    void success(File apkFile);

    void progress(int progress);

    void fail();
}

package com.hades.example.android.app_component.assist.content_provider.cr;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * https://www.cnblogs.com/zsychanpin/p/7242147.html
 */
final class Dict2ContentObserver extends ContentObserver {
    private static final String TAG = Dict2ContentObserver.class.getSimpleName();


    public Dict2ContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d(TAG, "onChange: " + uri + ", selfChange: " + selfChange);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.e(TAG, "onChange: " + selfChange);
    }
}
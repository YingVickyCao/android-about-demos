package com.hades.example.android.app_component.assist.content_provider.cr;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * https://www.cnblogs.com/zsychanpin/p/7242147.html
 */
final class DictContentObserver extends ContentObserver {
    private static final String TAG = DictContentObserver.class.getSimpleName();

    private DictUserActivity mSMSActivity;

    public DictContentObserver(DictUserActivity activity, Handler handler) {
        super(handler);
        mSMSActivity = activity;
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
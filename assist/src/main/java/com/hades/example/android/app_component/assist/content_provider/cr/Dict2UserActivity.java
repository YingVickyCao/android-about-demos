package com.hades.example.android.app_component.assist.content_provider.cr;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.app_component.assist.R;
import com.hades.example.android.app_component.ipc.common.content_provider.Dict2;


/**
 * 用来访问app中的 DictContentProvider
 */
public class Dict2UserActivity extends AppCompatActivity {
    private static final String TAG = "DictUserActivity";

    private ContentResolver contentResolver;
    private Dict2ContentObserver mDictContentObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_permission_activity);
        findViewById(R.id.insert).setOnClickListener(v -> insert());
        findViewById(R.id.delete).setOnClickListener(v -> delete());
        init();
    }

    public void init() {
        contentResolver = getContentResolver();
        mDictContentObserver = new Dict2ContentObserver(new Handler(Looper.getMainLooper()));
        contentResolver.registerContentObserver(Dict2.WORDS_URI, true, mDictContentObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mDictContentObserver) {
            contentResolver.unregisterContentObserver(mDictContentObserver);
            mDictContentObserver = null;
        }
    }

    public void insert() {
        ContentValues values = new ContentValues();
        long ts = System.currentTimeMillis();
        values.put(Dict2.DictColumns._ID, ts);
        Uri uri = contentResolver.insert(Dict2.WORDS_URI, values);
        Log.e(TAG, "doInsert: " + uri);
    }

    public void delete() {
        String where = Dict2.DictColumns._ID;
        int rowNum = contentResolver.delete(Dict2.WORDS_URI, where, null);
        Log.e(TAG, "doDelete: " + rowNum);
    }
}
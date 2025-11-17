package com.hades.example.android.app_component.assist.content_provider.cr;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.hades.example.android.app_component.assist.R;
import com.hades.example.android.app_component.content_provider.dict.common.Dict;
import com.hades.example.android.app_component.content_provider.dict.common.DictBasicActivity;


/**
 * 用来访问app中的 DictContentProvider
 */
public class DictUserActivity extends DictBasicActivity {
    private static final String TAG = "DictUserActivity";

    private ContentResolver contentResolver;
//    private DictContentObserver mDictContentObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView) findViewById(R.id.topic)).setText("Dict Content Resolver Operations");
    }

    @Override
    public void init() {
        contentResolver = getContentResolver();

        /**
         * FIX_ERROR:java.lang.SecurityException: Failed to find provider ** for user 0; expected to find a valid ContentProvider for this authority
         * https://blog.csdn.net/qq_41806128/article/details/115190130
         *
         * 解决：
         * 1 提供 content provider 的app 中，添加 android:authorities 并  android:exported
         *  <provider
         *             android:name=".app_component.content_provider.dict.DictContentProvider"
         *             android:authorities="com.hades.example.android.app_component.content_provider.dict.DictContentProvider"
         *             android:enabled="true"
         *             android:exported="true" />
         * 使用content provider 的assist 中, UR 必须 提供正确，它的authority 必须对应 android:authorities
         * 2 使用content provider 的assist 中,要声明 queries
         *
         *    <queries>
         *         <provider android:authorities="com.hades.example.android.app_component.content_provider.dict.DictContentProvider" />
         *     </queries>
         */
//        mDictContentObserver = new DictContentObserver(this, new Handler(Looper.getMainLooper()));
//        getContentResolver().registerContentObserver(Dict.getUri(), true, mDictContentObserver);
//        getContentResolver().notifyChange(Dict.getUri(), mDictContentObserver);
//
//        getContentResolver().registerContentObserver(Dict.getUriById(), true, mDictContentObserver);
//        getContentResolver().notifyChange(Dict.getUriById(), mDictContentObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (null != mDictContentObserver) {
//            getContentResolver().unregisterContentObserver(mDictContentObserver);
//            mDictContentObserver = null;
//        }
    }

    @Override
    public boolean doInsert(String word, String detail) {
        // 插入多条记录, 也适合参数多条记录
        ContentValues values = new ContentValues();
        values.put(Dict.Word.WORD, word);
        values.put(Dict.Word.DETAIL, detail);
        Uri uri = contentResolver.insert(Dict.WORDS_URI, values);
        Log.e(TAG, "doInsert: " + uri);
        return null != uri;
    }

    @Override
    public boolean doDelete(long id) {
        ContentValues values = new ContentValues();
        values.put(Dict.Word._ID, id);
        String where = Dict.Word._ID + " = ?";
        int rowNum = contentResolver.delete(Dict.WORDS_URI, where, new String[]{String.valueOf(id)});
        Log.e(TAG, "doDelete: " + rowNum);
        return rowNum > 0;
    }

    public boolean doDeleteById(long id) {
        // 删除单条记录
        int rowNum = contentResolver.delete(Dict.getUriById(id), null, null);
        Log.e(TAG, "doDelete: " + rowNum);
        return rowNum > 0;
    }


    @Override
    public boolean doUpdate(String word, String detail, long id) {
        ContentValues values = new ContentValues();
        values.put(Dict.Word.WORD, word);
        values.put(Dict.Word.DETAIL, detail);
        String where = Dict.Word._ID + " = ?";
        int rowNumer = contentResolver.update(Dict.WORDS_URI, values, where, new String[]{String.valueOf(id)});
        Log.e(TAG, "update: " + rowNumer);
        return rowNumer > 0;
    }

    public boolean doUpdateById(String word, String detail, long id) {
        // 更新单条记录
        ContentValues values = new ContentValues();
        values.put(Dict.Word.WORD, word);
        values.put(Dict.Word.DETAIL, detail);
        int rowNumer = contentResolver.update(Dict.getUriById(id), values, null, null);
        Log.e(TAG, "update: " + rowNumer);
        return rowNumer > 0;
    }

    @Override
    public Cursor doQuery(String keyword) {
        return contentResolver.query(Dict.WORDS_URI,
                null,
                "word like ? or detail like ?",
                new String[]{"%" + keyword + "%", "%" + keyword + "%"},
                null);
    }

    @Override
    public Cursor doQueryById(String id) {
        // 查询单条记录
        return contentResolver.query(Dict.getUriById(id), null, null, null, null);
    }
}
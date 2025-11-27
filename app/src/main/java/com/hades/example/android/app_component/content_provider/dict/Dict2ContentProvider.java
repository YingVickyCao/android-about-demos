package com.hades.example.android.app_component.content_provider.dict;

import static android.content.ContentResolver.NOTIFY_DELETE;
import static android.content.ContentResolver.NOTIFY_INSERT;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.hades.example.android.app_component.ipc.common.content_provider.Dict2;
import com.hades.utility.jvm.ThreadUtils;

import java.util.Arrays;

/**
 * notifyChange(Uri, ContentObserver, int) - 当 ContentResolver 更新数据后，可以通过注入到 ContentResolver 的ContentObserver 得到通知。
 * flags = 0 (或者 null observer): 最常见，ContentResolver 会查找所有注册的、匹配 uri 的 ContentObserver，然后调用它们的 onChange() 方法。
 */
public class Dict2ContentProvider extends ContentProvider {
    public static final String TAG = Dict2ContentProvider.class.getSimpleName();

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    SharedPreferences sharedPreferences;

    static {
        matcher.addURI(Dict2.AUTHORITY, Dict2.PATH_WORDS_URI, Dict2.CODE_WORDS_URI);
        Log.e(TAG, "matcher: " + matcher);
    }

    // 第一次调用该DictProvider时，系统先创建DictProvider对象，并回调该方法
    @Override
    public boolean onCreate() {
        sharedPreferences = getContext().getSharedPreferences(Dict2.FILE_NAME, 0);
        Log.d(TAG, "onCreate: ");
        return true;
    }

    //     返回指定Uri参数对应的数据的MIME类型
//     TODO: 11/07/2018 getType没有调用
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: uri=" + uri.toString());
        /**
         * MIME 类型 是一种标准化的标识符，用于描述数据的类型。例如：
         * text/plain (纯文本)
         * image/jpeg (JPEG 图片)
         * application/json (JSON 数据)
         * vnd.android.cursor.dir/ (代表多条记录的 Cursor)
         * vnd.android.cursor.item/ (代表单条记录的 Cursor)
         */
        switch (matcher.match(uri)) {
            // 如果操作的数据是多项记录
            case Dict2.CODE_WORDS_URI:
                return "text/plain";
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: uri=" + uri.toString() + ",values=" + values);

        Context context = getContext();
        // 手动进行权限检查
//        if (PackageManager.PERMISSION_DENIED == context.checkCallingOrSelfPermission(Dict2.writePermission)) {
//            throw new SecurityException("Not grant write permission");
//        }
        // 临时授权
        context.grantUriPermission("com.hades.example.android.app_component.assist", uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 对uri进行匹配
        switch (matcher.match(uri)) {
            // 如果Uri参数代表操作全部数据项
            case Dict2.CODE_WORDS_URI:
                if (values.keySet().isEmpty()) {
                    return null;
                }
                String key = values.keySet().toArray()[0].toString();
                long ts = System.currentTimeMillis();
                sharedPreferences.edit()
                        .putLong(key, ts)
                        .commit();
                // 通知数据已经改变
                getContext().getContentResolver().notifyChange(uri, null, NOTIFY_INSERT);
                return uri;

            // 如果Uri参数代表操作指定数据项
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        Log.d(TAG, "delete: " + ThreadUtils.getThreadInfo());
        Context context = getContext();
        context.grantUriPermission("com.hades.example.android.app_component.assist", uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // 对uri进行匹配
        switch (matcher.match(uri)) {
            // 如果Uri参数代表操作全部数据项
            case Dict2.CODE_WORDS_URI:
                String key = where;
                if (where.isEmpty()) {
                    return 0;
                }
                sharedPreferences.edit()
                        .remove(key)
                        .commit();
                // 通知数据已经改变
                getContext().getContentResolver().notifyChange(uri, null, NOTIFY_DELETE);
                return 1;

            // 如果Uri参数代表操作指定数据项
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        Log.d(TAG, "update: uri=" + uri.toString() + ",where=" + where + ",whereArgs=" + Arrays.toString(whereArgs));
        Log.d(TAG, "update: " + ThreadUtils.getThreadInfo());
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String where, String[] whereArgs, String sortOrder) {
        // query,thread =4331,Binder:21984_2
        Log.d(TAG, "query: uri=" + uri.toString() + ",where=" + where);
        Log.d(TAG, "query: " + ThreadUtils.getThreadInfo());
        return null;
    }
}
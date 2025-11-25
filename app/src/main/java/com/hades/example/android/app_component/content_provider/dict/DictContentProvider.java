package com.hades.example.android.app_component.content_provider.dict;

import static android.content.ContentResolver.NOTIFY_DELETE;
import static android.content.ContentResolver.NOTIFY_INSERT;
import static android.content.ContentResolver.NOTIFY_UPDATE;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.hades.example.android.app_component.content_provider.dict.common.Dict;
import com.hades.example.android.app_component.content_provider.dict.common.DictDbOps;
import com.hades.utility.jvm.ThreadUtils;

import java.util.Arrays;

/**
 * notifyChange(Uri, ContentObserver, int) - 当 ContentResolver 更新数据后，可以通过注入到 ContentResolver 的ContentObserver 得到通知。
 * flags = 0 (或者 null observer): 最常见，ContentResolver 会查找所有注册的、匹配 uri 的 ContentObserver，然后调用它们的 onChange() 方法。
 */
public class DictContentProvider extends ContentProvider {
    public static final String TAG = DictContentProvider.class.getSimpleName();

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    // TODO: 06/07/2018  dbOpenHelper not close
    private DictSQLiteOpenHelper dbOpenHelper;

    static {
        // 为 UriMatcher 注册两个Uri
        matcher.addURI(Dict.AUTHORITY, Dict.PATH_WORDS_URI, Dict.CODE_WORDS_URI);
        matcher.addURI(Dict.AUTHORITY, Dict.PATH_WORD_URI, Dict.CODE_WORD_URI);
        Log.e(TAG, "matcher: " + matcher);
    }

    // 第一次调用该DictProvider时，系统先创建DictProvider对象，并回调该方法
    @Override
    public boolean onCreate() {
        dbOpenHelper = new DictSQLiteOpenHelper(this.getContext(), DictDbOps.DB_NAME, 1);
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
            case Dict.CODE_WORDS_URI:
                return "vnd.android.cursor.dir/" + Dict.WORDS_URI;

            // 如果操作的数据是单项记录
            case Dict.CODE_WORD_URI:
                return "vnd.android.cursor.item/" + Dict.WORD_URI;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: uri=" + uri.toString() + ",values=" + values);

        /**
         * insert: uri=content://com.hades.example.android.app_component.cp.dict.DictContentProvider/words,values=detail=A  detail word=A
         * insert,thread =180,Binder:3178_2
         *
         * 疯狂连续插入数据,有时thread =180，有时thread =179
         insert,thread =180,Binder:3178_2
         insert,thread =179,Binder:3178_1
         */

        Log.d(TAG, "insert: " + ThreadUtils.getThreadInfo());

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri)) {
            // 如果Uri参数代表操作全部数据项
            case Dict.CODE_WORDS_URI:
                // 插入数据，返回插入记录的ID
                long rowId = db.insert(DictDbOps.TABLE_DICT_NAME, Dict.DictColumns._ID, values);
                // 如果插入成功返回uri
                if (rowId > 0) {
                    // 在已有的 Uri的后面追加ID
                    Uri wordUri = ContentUris.withAppendedId(uri, rowId);
                    // 通知数据已经改变
                    getContext().getContentResolver().notifyChange(Dict.WORDS_URI, null, NOTIFY_INSERT);
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        Log.d(TAG, "update: uri=" + uri.toString() + ",where=" + where + ",whereArgs=" + Arrays.toString(whereArgs));
        Log.d(TAG, "delete: " + ThreadUtils.getThreadInfo());

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // 记录所删除的记录数
        int num = 0;
        // 对uri进行匹配
        switch (matcher.match(uri)) {
            // 如果Uri参数代表操作全部数据项
            case Dict.CODE_WORDS_URI:
                num = db.delete(DictDbOps.TABLE_DICT_NAME, where, whereArgs);
                break;
            // 如果Uri参数代表操作指定数据项

            case Dict.CODE_WORD_URI:
                // 解析出所需要删除的记录ID
                long id = ContentUris.parseId(uri);
                String whereClause = Dict.DictColumns._ID + "=" + id;
                // 如果原来的where子句存在，拼接where子句
                if (where != null && !where.equals("")) {
                    whereClause = whereClause + " and " + where;
                }
                num = db.delete(DictDbOps.TABLE_DICT_NAME, whereClause, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        // 通知数据已经改变
        getContext().getContentResolver().notifyChange(uri, null, NOTIFY_DELETE);
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        Log.d(TAG, "update: uri=" + uri.toString() + ",where=" + where + ",whereArgs=" + Arrays.toString(whereArgs));
        Log.d(TAG, "update: " + ThreadUtils.getThreadInfo());

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        // 记录所修改的记录数
        int num = 0;
        switch (matcher.match(uri)) {
            // 如果Uri参数代表操作全部数据项
            case Dict.CODE_WORDS_URI:
                num = db.update(DictDbOps.TABLE_DICT_NAME, values, where, whereArgs);
                break;

            // 如果Uri参数代表操作指定数据项
            case Dict.CODE_WORD_URI:
                // 解析出想修改的记录ID
                long id = ContentUris.parseId(uri);
                String whereClause = Dict.DictColumns._ID + "=" + id;
                // 如果原来的where子句存在，拼接where子句
                if (where != null && !where.equals("")) {
                    whereClause = whereClause + " and " + where;
                }
                num = db.update(DictDbOps.TABLE_DICT_NAME, values, whereClause, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        // 通知数据已经改变
        getContext().getContentResolver().notifyChange(uri, null, NOTIFY_UPDATE);
        return num;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String where, String[] whereArgs, String sortOrder) {
        // query,thread =4331,Binder:21984_2
        Log.d(TAG, "query: uri=" + uri.toString() + ",where=" + where);
        Log.d(TAG, "query: " + ThreadUtils.getThreadInfo());

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri)) {
            // 如果Uri参数代表操作全部数据项
            case Dict.CODE_WORDS_URI:
                //  uri=content://com.hades.example.android.app_component.content_provider.dict.DictContentProvider/words,where=word like ? or detail like ?
                // 执行查询
                return db.query(DictDbOps.TABLE_DICT_NAME, projection, where, whereArgs, null, null, sortOrder);
            // 如果Uri参数代表操作指定数据项

            case Dict.CODE_WORD_URI:
                // query: uri=content://com.hades.example.android.app_component.content_provider.dict.DictContentProvider/word/10,where=null
                // 解析出想查询的记录ID
                long id = ContentUris.parseId(uri);
                String whereClause = Dict.DictColumns._ID + "=" + id;
                // 如果原来的where子句存在，拼接where子句
                if (where != null && !"".equals(where)) {
                    whereClause = whereClause + " and " + where;
                }
                return db.query(DictDbOps.TABLE_DICT_NAME, projection, whereClause, whereArgs, null, null, sortOrder);
            default:
                Log.e(TAG, "query: error: " + uri);
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }
}
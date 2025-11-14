package com.hades.example.android.app_component.content_provider.dict;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.hades.example.android.R;
import com.hades.example.android.app_component.content_provider.dict.common.Dict;
import com.hades.example.android.app_component.content_provider.dict.common.DictBasicActivity;
import com.hades.example.android.app_component.content_provider.dict.common.DictDbOps;

public class DictActivity extends DictBasicActivity {
    DictSQLiteOpenHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView) findViewById(R.id.topic)).setText("Dict Content Provider DB Operations");
    }

    @Override
    public void init() {
        dbHelper = new DictSQLiteOpenHelper(this, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (dbHelper != null) {
            dbHelper.close();
        }
    }


    @Override
    public boolean doInsert(String word, String detail) {
        try {
            dbHelper.getReadableDatabase().execSQL(DictDbOps.INSERT_TABLE_DICT, new String[]{null, word, detail});
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Cursor doQuery(String keyword) {
        /**
         * FIXED_ERROR:
         * 2019-03-15 11:22:16.590 19002-19002/com.hades.example.android E/AndroidRuntime: FATAL EXCEPTION: main
         *     Process: com.hades.example.android, PID: 19002
         *     android.database.sqlite.SQLiteException: no such table: dict (code 1): , while compiling: select * from dict where word like ? or detail like ?
         *     #################################################################
         *     Error Code : 1 (SQLITE_ERROR)
         *     Caused By : SQL(query) error or missing database.
         *     	(no such table: dict (code 1): , while compiling: select * from dict where word like ? or detail like ?)
         *     #################################################################
         */
//        //        return dbHelper.getReadableDatabase().rawQuery("select * from dict where word like ? or word like ?", new String[]{"%" + key + "%", "%" + key + "%"});
        return dbHelper.getReadableDatabase().rawQuery(DictDbOps.QUERY_TABLE_DICT, new String[]{"%" + keyword + "%", "%" + keyword + "%"});
    }

    @Override
    public boolean doDelete(long id) {
        try {
            String where = Dict.Word._ID + " = " + id;
            int rowNum = dbHelper.getReadableDatabase().delete(DictDbOps.TABLE_DICT_NAME, where, null);
            return rowNum > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean doUpdate(String word, String detail, long id) {
        try {
            ContentValues values = new ContentValues();
            values.put(Dict.Word.WORD, word);
            values.put(Dict.Word.DETAIL, detail);
            String where = Dict.Word._ID + " = ? ";
            int rowNum = dbHelper.getReadableDatabase().update(DictDbOps.TABLE_DICT_NAME, values, where, new String[]{String.valueOf(id)});
            return rowNum > 0;
        } catch (Exception ex) {
            return false;
        }
    }
}
package com.hades.example.android.app_component.content_provider.dict

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/*
    // data/data/com.xxx.xxx/dict.db
 */
class DictSQLiteOpenHelper(context: Context?, name: String?, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    constructor(context: Context?, version: Int) : this(context, DictDbOps.DB_NAME, version)

    override fun onCreate(db: SQLiteDatabase) {
        // When first use DB, auto create table
        db.execSQL(DictDbOps.CREATE_TABLE_DICT_SQL)

        initTableValues(db, DictDbOps.TABLE_DICT_NAME, buildTable1Data())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade: oldVersion=" + oldVersion + ",newVersion=" + newVersion)
    }

    private fun initTableValues(db: SQLiteDatabase, tableName: String?, list: MutableList<DictRowBean?>) {
        /**
         * FIXED_ERROR:java.lang.IllegalStateException: getDatabase called recursively
         *
         * https://blog.csdn.net/adayabetter/article/details/44516217
         */
//        SQLiteDatabase database = getWritableDatabase();
        for (i in list.indices) {
            db.insert(tableName, null, convertBean2ContentValues(list.get(i)!!))
        }
    }

    private fun convertBean2ContentValues(info: DictRowBean): ContentValues {
        val value = ContentValues()
        value.put(Dict.Word._ID, info.get_id())
        value.put(Dict.Word.WORD, info.getWord())
        value.put(Dict.Word.DETAIL, info.getDetail())
        return value
    }

    fun buildTable1Data(): MutableList<DictRowBean?> {
        val list: MutableList<DictRowBean?> = ArrayList<DictRowBean?>()
        list.add(DictRowBean(1, "City", 1))
        list.add(DictRowBean(2, "China", 11))
        list.add(DictRowBean(3, "D", 2))
        list.add(DictRowBean(4, "ABC", 2))
        list.add(DictRowBean(5, "hello", 204))
        list.add(DictRowBean(6, "Book", 9))
        list.add(DictRowBean(7, "OP", 15))
        list.add(DictRowBean(8, "Agile", 1911))
        return list
    }

    companion object Companion {
        private val TAG: String = DictSQLiteOpenHelper::class.java.getSimpleName()
    }
}
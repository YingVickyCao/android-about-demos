package com.hades.example.android.app_component.content_provider.dict.common

object DictDbOps {
    const val DB_NAME = "dict.db"
    const val TABLE_DICT_NAME: String = "dict"
    const val INSERT_TABLE_DICT: String = ("insert into " + TABLE_DICT_NAME
            + "(" + Dict.DictColumns._ID
            + "," + Dict.DictColumns.WORD
            + "," + Dict.DictColumns.DETAIL
            + ") values(?,?,?)")
    const val DELETE_TABLE_DICT: String = ("insert into " + TABLE_DICT_NAME
            + "(" + Dict.DictColumns._ID
            + "," + Dict.DictColumns.WORD
            + "," + Dict.DictColumns.DETAIL
            + ") values(?,?,?)")

    const val QUERY_TABLE_DICT_SELECTION = Dict.DictColumns.WORD + " like ? or " + Dict.DictColumns.DETAIL + " like ?"
    const val QUERY_TABLE_DICT: String = "select * from " + TABLE_DICT_NAME + " where " + QUERY_TABLE_DICT_SELECTION
    const val QUERY_TABLE_DICT_BY_ID: String = "select * from " + TABLE_DICT_NAME + " where " + Dict.DictColumns._ID + " = ?"
    const val CREATE_TABLE_DICT_SQL: String = "create table dict(${Dict.DictColumns._ID} integer primary " + "key autoincrement , ${Dict.DictColumns.WORD} ,${Dict.DictColumns.DETAIL})"
}
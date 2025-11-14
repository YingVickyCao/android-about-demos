package com.hades.example.android.app_component.content_provider.dict

object DictDbOps {
    const val DB_NAME = "dict.db"
    const val TABLE_DICT_NAME: String = "dict"
    const val INSERT_TABLE_DICT: String = ("insert into " + TABLE_DICT_NAME
            + "(" + Dict.Word._ID
            + "," + Dict.Word.WORD
            + "," + Dict.Word.DETAIL
            + ") values(?,?,?)")

    const val QUERY_TABLE_DICT_SELECTION = Dict.Word.WORD + " like ? or " + Dict.Word.DETAIL + " like ?"
    const val QUERY_TABLE_DICT: String = "select * from " + TABLE_DICT_NAME + " where " + QUERY_TABLE_DICT_SELECTION
    const val CREATE_TABLE_DICT_SQL: String = "create table dict(${Dict.Word._ID} integer primary " + "key autoincrement , ${Dict.Word.WORD} ,${Dict.Word.DETAIL})"
}
package com.example.kotlin.test.db;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import androidx.room.Relation;

import java.util.List;

@DatabaseView("SELECT menu2.menu_title AS menuTitle, page_data.title " +
        "FROM menu2 " +
        "JOIN page_data ON menu2.code = page_data.code")
public class MenuPageDataDetails {
    @ColumnInfo(name = "menu_title")
    String menuTitle;

    // parentColumn: code in Menu
    // entityColumn - code in PageData
    @Relation(parentColumn = "code", entityColumn = "code")
    public List<PageData> pageData;
}

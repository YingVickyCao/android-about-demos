package com.example.kotlin.test.db;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import androidx.room.Relation;

import java.util.List;

@DatabaseView("SELECT menu.menu_title AS menuTitle, page_data.title " +
        "FROM menu " +
        "JOIN page_data ON menu.code = page_data.code")
public class MenuPageDataDetails {
    @ColumnInfo(name = "menu_title")
    String menuTitle;

    // parentColumn: code in Menu
    // entityColumn - code in PageData
    @Relation(parentColumn = "code", entityColumn = "code")
    public List<PageData> pageData;
}

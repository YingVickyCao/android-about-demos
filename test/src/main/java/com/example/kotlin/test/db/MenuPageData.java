package com.example.kotlin.test.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MenuPageData {
    @Embedded
    public Menu menu;

    // parentColumn: code in Menu
    // entityColumn - code in PageData
    @Relation(parentColumn = "code", entityColumn = "code")
    public List<PageData> pageData;
}

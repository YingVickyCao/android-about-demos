package com.example.kotlin.test.db;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// Entity - data entity
// primaryKeys - set multiple primary keys
// indices - set index
@Entity(tableName = "menu2", primaryKeys = {"code"}, indices = {@Index(value = {"menu_title"}, unique = true)})
public class Menu {
    //    @PrimaryKey
    @NonNull
    public int code;

    @ColumnInfo(name = "menu_title")
    public String menuTitle;

    @ColumnInfo(name = "menu_type")
    public String menuType;

    //  don't want to persist
    @Ignore
    public Bitmap picture;

    // v1 : no country field
    // added in v2
    public String country;


    public Menu(int code, String menuTitle, String menuType) {
        this.code = code;
        this.menuTitle = menuTitle;
        this.menuType = menuType;
    }
}

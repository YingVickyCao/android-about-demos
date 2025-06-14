package com.example.kotlin.test.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Entity - data entity
@Entity(tableName = "menu")
public class Menu {
    @PrimaryKey
    @NonNull
    public int code;

    @ColumnInfo(name = "menu_title")
    public String menuTitle;

    @ColumnInfo(name = "menu_type")
    public String menuType;


    public Menu(int code, String menuTitle, String menuType) {
        this.code = code;
        this.menuTitle = menuTitle;
        this.menuType = menuType;
    }
}

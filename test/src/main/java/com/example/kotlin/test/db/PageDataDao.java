package com.example.kotlin.test.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PageDataDao {
    @Query("SELECT * FROM page_data")
    LiveData<List<PageData>> getAll();

    @Insert
    void insert(PageData... data);

    // TODO: 2025/6/14 not working
//    @Query("SELECT page_data.title " +
//            "FROM page_data " +
//            "INNER JOIN menu ON menu.code = page_data.code " +
//            "WHERE menu.code = :menuCode")
    @Query("SELECT title FROM page_data WHERE code = :menuCode")
    LiveData<List<String>> getTitle(int menuCode);
    
    @Query("SELECT title FROM page_data JOIN menu ON  page_data.code = menu.code WHERE menu.code = :menuCode ")
    List<String> getTitle2(int menuCode);
}

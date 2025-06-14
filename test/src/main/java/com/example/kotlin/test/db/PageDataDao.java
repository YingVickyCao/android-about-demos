package com.example.kotlin.test.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PageDataDao {
    @Query("select * from page_data")
    LiveData<List<PageData>> getAll();

    @Insert
    void insert(PageData... data);
}

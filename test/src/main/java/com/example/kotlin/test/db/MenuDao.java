package com.example.kotlin.test.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MenuDao {
    @Query("SELECT * from menu ORDER BY menu_title ASC")
    List<Menu> getAll();

    // MutableLiveData
    @Query("SELECT * from menu")
    LiveData<List<Menu>> getAll2();

    @Query("SELECT * FROM menu WHERE code IN (:menuCodes) ")
    List<Menu> loadAllByCodes(int[] menuCodes);

    // fuzzy query
    @Query("SELECT * FROM menu WHERE menu_title LIKE :menuTitle")
    Menu findByTitle(String menuTitle);

    @Query("SELECT * FROM menu WHERE code = :menuCode")
    Menu findByCode(String menuCode);

    @Insert
    void insert(Menu menu);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertAll(Menu... menus);

    @Delete
    void delete(Menu menu);

    @Query("DELETE from menu")
    void deleteAll();
}

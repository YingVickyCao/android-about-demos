package com.example.kotlin.test.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MenuDao {
    @Query("SELECT * from menu ORDER BY menu_title ASC")
    List<Menu> getAll();

    // MutableLiveData
    @Query("SELECT * from menu")
    LiveData<List<Menu>> getAll2();

    // can return a array or list
    @Query("SELECT * FROM menu WHERE code IN (:menuCodes) ")
    List<Menu> loadAllByCodes(int[] menuCodes);

    // fuzzy query
    @Query("SELECT * FROM menu WHERE menu_title LIKE :menuTitle")
    Menu findByTitle(String menuTitle);

    @Query("SELECT * FROM menu WHERE code = :menuCode")
    Menu findByCode(String menuCode);

    // Return a subset of a table's columns
    @Query("SELECT menu_title FROM menu WHERE code = :menuCode")
    String findMenuTitle(String menuCode);

    // Return a subset of a table's columns
    @Query("SELECT menu_title FROM menu WHERE code = :menuCode")
    SimpleMenu findMenuSimple(String menuCode);

    // @Insert - can return a new rowId for the inserted item
    @Insert
    void insert(Menu menu);

    // @Insert - can return array of the new rowId inserted
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertAll(Menu... menus);

    // update specific rows
    // @Update - can return the num of rows updated
    @Update
    public void updateMenus(Menu... menus); // use primary key to match the passed entity instances to rows in database.

    // delete specific rows
    // @Delete - can return the num of rows deleted
    @Delete
    void delete(Menu menu); // Use primary key to match the passed entity instances to rows in database.

    @Query("DELETE from menu")
    void deleteAll();
}

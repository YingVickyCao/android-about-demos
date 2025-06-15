package com.example.kotlin.test.db;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;

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

    //  return a paging source
    // TODO: 2025/6/15  PagingSource
//    @Query("SELECT * FROM page_data WHERE code = :code")
//    PagingSource<Integer, PageData> pagingSource(String code);

    // return a cursor
    // Only use this functionality if you already have code that expects a cursor and that you can't refactor easily.
    @Query("SELECT * FROM page_data WHERE code = :code")
    Cursor loadRawPageData(String code);

    //    @Query("SELECT * FROM menu JOIN page_data ON menu.code = page_data.code WHERE menu.code= :code")
    // todo: not expected
    @Query("SELECT * FROM menu JOIN page_data ON menu.code = page_data.code")
//    Map<Menu, List<PageData>> loadMenuAndPageData(int code);
    Map<Menu, List<PageData>> loadMenuAndPageData();


    // Transaction -多次查询，以确定整个操作以原子方式运行。
    @Transaction
    @Query("SELECT * FROM menu")
    List<MenuPageData> loadMenuPageData();

    // Transaction -多次查询，以确定整个操作以原子方式运行。
    @Transaction
    @Query("SELECT * FROM menu")
    Single<List<MenuPageData>> loadMenuPageData2(); // RxJava
}

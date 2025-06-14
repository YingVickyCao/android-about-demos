package com.example.kotlin.test.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.kotlin.test.db.AppDatabase;
import com.example.kotlin.test.db.PageData;
import com.example.kotlin.test.db.PageDataDao;

import java.util.List;

public class PageDataRepository {
    PageDataDao pageDataDao;

    public PageDataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        pageDataDao = db.pageDataDao();
    }

    public LiveData<List<PageData>> getAll() {
        return pageDataDao.getAll();
    }

    public LiveData<List<String>> getTitle(int menuCode) {
        return pageDataDao.getTitle(menuCode);
    }

    public List<String> getTitle2(int menuCode) {
        return pageDataDao.getTitle2(menuCode);
    }

    public void insert(PageData... data) {
        AppDatabase.databaseExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                pageDataDao.insert(data);
            }
        });
    }
}

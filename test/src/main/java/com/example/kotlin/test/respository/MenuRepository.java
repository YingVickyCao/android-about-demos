package com.example.kotlin.test.respository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.kotlin.test.db.AppDatabase;
import com.example.kotlin.test.db.Menu;
import com.example.kotlin.test.db.MenuDao;

import java.util.List;

public class MenuRepository {
    private MenuDao menuDao;

    private LiveData<List<Menu>> allMenus;

    // TODO: 2025/6/14 remove the application dependency
    // https://github.com/googlesamples ->  android-architecture-components repository
    public MenuRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        menuDao = db.menuDao();
        allMenus = menuDao.getAll2(); // Room execute all queries on a separate thread
    }

    public LiveData<List<Menu>> getAllMenus() {
        return allMenus;
    }

    public void insert(Menu menu) {
        // Use ExecutorService to insert into a background thread
        AppDatabase.databaseExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                menuDao.insert(menu);
            }
        });
    }
}

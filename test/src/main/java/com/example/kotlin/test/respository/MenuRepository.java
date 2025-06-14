package com.example.kotlin.test.respository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.kotlin.test.db.AppDatabase;
import com.example.kotlin.test.db.Menu;
import com.example.kotlin.test.db.MenuDao;
import com.example.kotlin.test.db.SimpleMenu;

import java.util.List;

public class MenuRepository {
    private MenuDao menuDao;

    private LiveData<List<Menu>> allMenus;

    private MutableLiveData<String> _menuTitle = new MutableLiveData<>();
    private LiveData<String> menuTitle = _menuTitle;

    private MutableLiveData<SimpleMenu> _simpleMenu = new MutableLiveData<>();
    private LiveData<SimpleMenu> simpleMenu = _simpleMenu;

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

    public LiveData<String> findMenuTitle(String menuCode) {
        AppDatabase.databaseExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                String title = menuDao.findMenuTitle(menuCode);
                _menuTitle.postValue(title);
            }
        });
        return menuTitle;
    }

    public LiveData<SimpleMenu> findMenuSimple(String menuCode) {
        AppDatabase.databaseExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                SimpleMenu title = menuDao.findMenuSimple(menuCode);
                _simpleMenu.postValue(title);
            }
        });
        return simpleMenu;
    }

    public void delete(Menu menu) {
        AppDatabase.databaseExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                menuDao.delete(menu);
            }
        });
    }

}

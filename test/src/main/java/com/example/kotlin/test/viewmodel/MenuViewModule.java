package com.example.kotlin.test.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.kotlin.test.db.Menu;
import com.example.kotlin.test.respository.MenuRepository;

import java.util.List;

// TODO: 2025/6/14 import androidx.lifecycle.AndroidViewModel;
// TODO: 2025/6/14  import androidx.lifecycle.ViewModel; 
public class MenuViewModule extends AndroidViewModel {
    private MenuRepository repository;
    private final LiveData<List<Menu>> menus;

    public MenuViewModule(@NonNull Application application) {
        super(application);

        repository = new MenuRepository(application);
        menus = repository.getAllMenus();
    }

    public LiveData<List<Menu>> getMenus() {
        return menus;
    }

    public void insert(Menu menu) {
        repository.insert(menu);
    }
}
